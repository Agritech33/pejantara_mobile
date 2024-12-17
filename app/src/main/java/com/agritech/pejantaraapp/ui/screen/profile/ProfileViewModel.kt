package com.agritech.pejantaraapp.ui.screen.profile

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agritech.pejantaraapp.data.model.ProfileState
import com.agritech.pejantaraapp.util.reduceFileImage
import com.agritech.pejantaraapp.util.uriToFile
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val firebaseStorage: FirebaseStorage
) : ViewModel() {

    private val _profileState = MutableStateFlow(ProfileState())
    val profileState: StateFlow<ProfileState> = _profileState.asStateFlow()

    init {
        loadUserProfile()
    }

    fun loadUserProfile() {
        val userId = auth.currentUser?.uid ?: return
        firestore.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                val name = document.getString("name") ?: "Nama tidak tersedia"
                val email = auth.currentUser?.email ?: "Email tidak tersedia"
                val phone = document.getString("phone") ?: "Telepon tidak tersedia"
                val money = document.getLong("money")?.toInt() ?: 0
                val photoUri = document.getString("photoUri")

                _profileState.update {
                    it.copy(
                        name = name,
                        email = email,
                        phone = phone,
                        money = money,
                        photoUri = photoUri
                    )
                }
            }
            .addOnFailureListener { e ->
                Log.e("ProfileViewModel", "Error memuat profil: ${e.message}")
            }
    }

    fun updateName(newName: String) {
        val userId = auth.currentUser?.uid ?: return
        firestore.collection("users").document(userId)
            .update("name", newName)
            .addOnSuccessListener {
                _profileState.update { it.copy(name = newName) }
            }
            .addOnFailureListener { e ->
                Log.e("ProfileViewModel", "Error memperbarui nama: ${e.message}")
            }
    }

    fun updatePhone(newPhone: String) {
        val userId = auth.currentUser?.uid ?: return
        firestore.collection("users").document(userId)
            .update("phone", newPhone)
            .addOnSuccessListener {
                _profileState.update { it.copy(phone = newPhone) }
            }
            .addOnFailureListener { e ->
                Log.e("ProfileViewModel", "Error memperbarui telepon: ${e.message}")
            }
    }

    fun updateNotificationPreference(isEnabled: Boolean) {
        val userId = auth.currentUser?.uid ?: return
        firestore.collection("users").document(userId)
            .update("isNotificationEnabled", isEnabled)
            .addOnSuccessListener {
                _profileState.update { it.copy(isNotificationEnabled = isEnabled) }
            }
            .addOnFailureListener { e ->
                Log.e("ProfileViewModel", "Error memperbarui notifikasi: ${e.message}")
            }
    }

    fun updateLocationPreference(isEnabled: Boolean) {
        val userId = auth.currentUser?.uid ?: return
        firestore.collection("users").document(userId)
            .update("isLocationEnabled", isEnabled)
            .addOnSuccessListener {
                _profileState.update { it.copy(isLocationEnabled = isEnabled) }
            }
            .addOnFailureListener { e ->
                Log.e("ProfileViewModel", "Error memperbarui lokasi: ${e.message}")
            }
    }

    fun uploadProfileImage(
        context: Context,
        uri: Uri,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val userId = auth.currentUser?.uid ?: return
        val storageRef = firebaseStorage.reference.child("profile_images/$userId.jpg")

        val file = uriToFile(uri, context).reduceFileImage()
        storageRef.putFile(Uri.fromFile(file))
            .addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                    firestore.collection("users").document(userId)
                        .update("photoUri", downloadUrl.toString())
                        .addOnSuccessListener {
                            _profileState.update { it.copy(photoUri = downloadUrl.toString()) }
                            onSuccess()
                        }
                        .addOnFailureListener { e ->
                            onFailure(e.message ?: "Gagal memperbarui URL gambar profil")
                        }
                }
            }
            .addOnFailureListener { e ->
                onFailure(e.message ?: "Gagal mengunggah gambar")
            }
    }

    fun updatePassword(
        oldPassword: String,
        newPassword: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val user = auth.currentUser
        val email = user?.email

        if (user != null && !email.isNullOrEmpty()) {
            val credential = EmailAuthProvider.getCredential(email, oldPassword)
            user.reauthenticate(credential)
                .addOnCompleteListener { reauthTask ->
                    if (reauthTask.isSuccessful) {
                        user.updatePassword(newPassword)
                            .addOnCompleteListener { updateTask ->
                                if (updateTask.isSuccessful) {
                                    onSuccess()
                                } else {
                                    onFailure(
                                        updateTask.exception?.message ?: "Gagal mengubah password"
                                    )
                                }
                            }
                    } else {
                        onFailure(reauthTask.exception?.message ?: "Password lama salah")
                    }
                }
        } else {
            onFailure("Pengguna tidak valid")
        }
    }

    fun updateCoins(coins: Int) {
        val userId = auth.currentUser?.uid ?: return
        val currentCoins = _profileState.value.money ?: 0
        val updatedCoins = currentCoins + coins

        // Update di Firestore
        firestore.collection("users").document(userId)
            .update("money", updatedCoins)
            .addOnSuccessListener {
                _profileState.update { it.copy(money = updatedCoins) } // Perbarui state lokal
            }
            .addOnFailureListener { e ->
                Log.e("ProfileViewModel", "Gagal memperbarui koin: ${e.message}")
            }
    }

}




