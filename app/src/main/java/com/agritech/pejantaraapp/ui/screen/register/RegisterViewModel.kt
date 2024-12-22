package com.agritech.pejantaraapp.ui.screen.register

import android.util.Log
import androidx.lifecycle.ViewModel
import com.agritech.pejantaraapp.data.model.ProfileState
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val googleSignInClient: GoogleSignInClient,
    private val emailService: EmailService
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun onRegisterClick(
        name: String,
        phone: String,
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
                registerWithEmailPassword(name, phone, email, password, onSuccess, onFailure)
            }
        }
    }

    private fun registerWithEmailPassword(
        name: String,
        phone: String,
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
                        val verificationCode = generateVerificationCode()
                        saveUserToFirestore(user, verificationCode) { success ->
                            if (success) {
                                sendVerificationEmail(email, verificationCode)
                                createUserDocument(user, name, phone)
                                onSuccess()
                            } else {
                                onFailure("Gagal menyimpan data pengguna.")
                            }
                        }
                    } ?: onFailure("User tidak ditemukan setelah registrasi.")
                } else {
                    onFailure(task.exception?.message ?: "Terjadi kesalahan.")
                }
            }
    }

    private fun generateVerificationCode(): String {
        return (1000..9999).random().toString()
    }

    private fun saveUserToFirestore(user: FirebaseUser, verificationCode: String, callback: (Boolean) -> Unit) {
        val userProfile = ProfileState(
            name = "Default Name",
            email = user.email,
            verificationCode = verificationCode,
            isVerified = false
        )

        firestore.collection("users").document(user.uid)
            .set(userProfile)
            .addOnSuccessListener { callback(true) }
            .addOnFailureListener { callback(false) }
    }

    private fun sendVerificationEmail(email: String, code: String) {
        emailService.sendEmail(
            recipient = email,
            subject = "Kode Verifikasi Akun Anda",
            body = "Kode verifikasi Anda adalah: $code. Masukkan kode ini di aplikasi untuk memverifikasi akun Anda."
        )
    }

    fun verifyEmailCode(
        userId: String,
        enteredCode: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        firestore.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                val storedCode = document.getString("verificationCode")
                if (storedCode == enteredCode) {
                    firestore.collection("users").document(userId)
                        .update("isVerified", true)
                        .addOnSuccessListener { onSuccess() }
                        .addOnFailureListener { e -> onFailure(e.message ?: "Gagal memperbarui status verifikasi.") }
                } else {
                    onFailure("Kode verifikasi salah.")
                }
            }
            .addOnFailureListener { onFailure(it.message ?: "Gagal memverifikasi kode.") }
    }

    private fun createUserDocument(user: FirebaseUser, name: String, phone: String) {
        val userId = user.uid
        val userProfile = ProfileState(
            name = name,
            email = user.email,
            phone = phone,
            money = 0,
            photoUri = null,
            isLocationEnabled = false
        )
        firestore.collection("users").document(userId).set(userProfile)
            .addOnSuccessListener { Log.d("RegisterViewModel", "User document created") }
            .addOnFailureListener { e -> Log.e("RegisterViewModel", "Error creating user: ${e.message}") }
    }

}










