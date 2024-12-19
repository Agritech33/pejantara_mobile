package com.agritech.pejantaraapp.ui.screen.register

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.agritech.pejantaraapp.data.model.ProfileState
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.security.SecureRandom
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val googleSignInClient: GoogleSignInClient
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private var verificationCode: String = generateRandomCode()

    fun onRegisterClick(
        email: String,
        password: String,
        confirmPassword: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        when {
            email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() -> {
                onFailure("Semua field harus diisi.")
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                onFailure("Format email tidak valid.")
            }
            password.length < 6 -> {
                onFailure("Password harus memiliki minimal 6 karakter.")
            }
            password != confirmPassword -> {
                onFailure("Password dan konfirmasi password tidak cocok.")
            }
            else -> {
                registerWithEmailPassword(email, password, onSuccess, onFailure)
            }
        }
    }

    private fun registerWithEmailPassword(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        _isLoading.value = true
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _isLoading.value = false
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.let {
                        createUserDocument(user)
                        sendEmailVerification(user, onSuccess, onFailure)
                    } ?: onFailure("User tidak ditemukan setelah registrasi.")
                } else {
                    onFailure(task.exception?.message ?: "Terjadi kesalahan.")
                }
            }
    }

    private fun sendEmailVerification(
        user: FirebaseUser,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        user.sendEmailVerification()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure(task.exception?.message ?: "Gagal mengirim email verifikasi.")
                }
            }
    }

    fun registerWithGoogle(
        idToken: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        _isLoading.value = true
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                _isLoading.value = false
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure(task.exception?.message ?: "Google Sign-In gagal.")
                }
            }
    }

    fun resendVerificationEmail(
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val user = auth.currentUser
        user?.let {
            user.sendEmailVerification()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onSuccess()
                    } else {
                        onFailure(task.exception?.message ?: "Gagal mengirim ulang email verifikasi.")
                    }
                }
        } ?: onFailure("User tidak ditemukan.")
    }

    fun verifyEmailCode(
        enteredCode: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        if (enteredCode == verificationCode) {
            onSuccess()
        } else {
            onFailure("Kode verifikasi salah. Silakan coba lagi.")
        }
    }

    private fun generateRandomCode(): String {
        val random = SecureRandom()
        return (random.nextInt(9000) + 1000).toString() // Generate 4-digit code
    }

    private fun createUserDocument(user: FirebaseUser) {
        val userId = user.uid
        val userProfile = ProfileState(
            name = "Default Name",
            email = user.email,
            phone = null,
            money = 0,
            photoUri = null,
            isLocationEnabled = false
        )
        firestore.collection("users").document(userId).set(userProfile)
            .addOnSuccessListener { Log.d("RegisterViewModel", "User document created") }
            .addOnFailureListener { e -> Log.e("RegisterViewModel", "Error creating user: ${e.message}") }
    }

}








