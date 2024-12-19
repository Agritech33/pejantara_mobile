//package com.agritech.pejantaraapp.data.repository
//
//import android.content.Context
//import android.net.Uri
//import android.util.Log
//import com.agritech.pejantaraapp.data.model.ProfileState
//import com.agritech.pejantaraapp.util.reduceFileImage
//import com.agritech.pejantaraapp.util.uriToFile
//import com.google.firebase.auth.EmailAuthProvider
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.firestore.FirebaseFirestore
//import com.google.firebase.storage.FirebaseStorage
//import kotlinx.coroutines.tasks.await
//import javax.inject.Inject
//
//class UserRepository @Inject constructor(
//    private val auth: FirebaseAuth,
//    private val firestore: FirebaseFirestore,
//    private val firebaseStorage: FirebaseStorage
//) {
//
//    suspend fun getUserProfile(): ProfileState? {
//        val currentUser = auth.currentUser ?: return null
//
//        val documentSnapshot = firestore.collection("users").document(userId).get().await()
//        if (!documentSnapshot.exists()) {
//            Log.e("UserRepository", "Dokumen pengguna tidak ditemukan di Firestore")
//            return null
//        }
//        return documentSnapshot.toObject(ProfileState::class.java)
//        val snapshot = firestore.collection("users").document(currentUser.uid).get().await()
//        return if (snapshot.exists()) {
//            ProfileState(
//                name = snapshot.getString("name"),
//                phone = snapshot.getString("phone"),
//                photoUri = snapshot.getString("photoUri"),
//                money = snapshot.getLong("money")?.toInt(),
//                isLocationEnabled = snapshot.getBoolean("isLocationEnabled") ?: false,
//                isLoading = false
//            )
//        } else null
//    }
//
//    suspend fun updateName(newName: String) {
//        val userId = auth.currentUser?.uid ?: throw Exception("User not authenticated")
//        firestore.collection("users").document(userId).update("name", newName).await()
//    }
//
//    suspend fun updatePhone(newPhone: String) {
//        val userId = auth.currentUser?.uid ?: throw Exception("User not authenticated")
//        firestore.collection("users").document(userId).update("phone", newPhone).await()
//    }
//
//    suspend fun updateLocationPreference(isEnabled: Boolean) {
//        val userId = auth.currentUser?.uid ?: throw Exception("User not authenticated")
//        firestore.collection("users").document(userId).update("isLocationEnabled", isEnabled).await()
//    }
//
//    suspend fun uploadProfileImage(context: Context, uri: Uri): String {
//        val userId = auth.currentUser?.uid ?: throw Exception("User not authenticated")
//        val file = uriToFile(uri, context).reduceFileImage()
//
//        val storageRef = firebaseStorage.reference.child("profile_images/$userId.jpg")
//        storageRef.putFile(Uri.fromFile(file)).await()
//
//        return storageRef.downloadUrl.await().toString()
//    }
//
//    suspend fun updatePassword(oldPassword: String, newPassword: String) {
//        val currentUser = auth.currentUser ?: throw Exception("User not authenticated")
//        val credential = EmailAuthProvider.getCredential(currentUser.email!!, oldPassword)
//        currentUser.reauthenticate(credential).await()
//        currentUser.updatePassword(newPassword).await()
//    }
//
//    suspend fun updateCoins(coins: Int): Int {
//        val userId = auth.currentUser?.uid ?: throw Exception("User not authenticated")
//        val snapshot = firestore.collection("users").document(userId).get().await()
//
//        val currentCoins = snapshot.getLong("money")?.toInt() ?: 0
//        val updatedCoins = currentCoins + coins
//
//        firestore.collection("users").document(userId).update("money", updatedCoins).await()
//        return updatedCoins
//    }
//}
//
