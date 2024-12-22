package com.agritech.pejantaraapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.agritech.pejantaraapp.data.database.Trash
import com.agritech.pejantaraapp.data.database.TrashDao
import com.agritech.pejantaraapp.data.retrofit.ApiService
import com.agritech.pejantaraapp.ui.screen.detail_result.ResultUiState
import com.agritech.pejantaraapp.util.Resource
import com.agritech.pejantaraapp.util.reduceFileImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class TrashRepository(
    private val trashDao: TrashDao,
    private val apiService: ApiService,
    private val tutorialRepository: TutorialRepository
) {

    fun predictTrash(image: File): Flow<Resource<Long>> = flow {
        try {
            emit(Resource.Loading)

            val imageFile = image.reduceFileImage()
            if (!imageFile.exists() || imageFile.length() == 0L) {
                emit(Resource.Error("Gambar tidak valid atau kosong."))
                return@flow
            }

            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "file",
                imageFile.name,
                requestImageFile
            )

            Log.d("TrashRepository", "Mengunggah gambar ke server...")
            val response = apiService.predictTrash(multipartBody)

            val predictedClass = response.prediction
            val trash = Trash(
                predictedClass = predictedClass,
                videoUrl = "",
                articleUrl = "",
                imageData = imageFile.readBytes()
            )
            val trashId = trashDao.insert(trash)

            cleanUpOldTrash()
            emit(Resource.Success(trashId))

            Log.i("TrashRepository", "Prediksi berhasil: $predictedClass")
        } catch (e: UnknownHostException) {
            Log.e("TrashRepository", "Tidak dapat terhubung ke server: ${e.localizedMessage}")
            emit(Resource.Error("Tidak dapat terhubung ke server. Periksa koneksi internet Anda."))
        } catch (e: SocketTimeoutException) {
            Log.e("Network", "Timeout Error: ${e.message}")
            emit(Resource.Error("Koneksi lambat atau server tidak merespons. Silakan coba lagi."))
        } catch (e: IOException) {
            Log.e("TrashRepository", "Kesalahan jaringan: ${e.localizedMessage}")
            emit(Resource.Error("Kesalahan jaringan. Coba lagi nanti."))
        } catch (e: Exception) {
            Log.e("TrashRepository", "Kesalahan tidak diketahui: ${e.localizedMessage}")
            emit(Resource.Error("Kesalahan tidak diketahui: ${e.localizedMessage}"))
        }
    }

    fun fetchResultWithVideos(image: File): Flow<Resource<ResultUiState>> = flow {
        try {
            emit(Resource.Loading)

            val imageFile = image.reduceFileImage()
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "file",
                imageFile.name,
                requestImageFile
            )

            val response = apiService.predictTrash(multipartBody)

            val predictedClass = response.prediction
            val videos = tutorialRepository.getVideosByTrashType(predictedClass)

            val resultUiState = ResultUiState(
                trashType = predictedClass,
                imageUrl = "",
                videos = videos
            )
            emit(Resource.Success(resultUiState))
        } catch (e: Exception) {
            Log.e("TrashRepository", "Kesalahan: ${e.localizedMessage}", e)
            emit(Resource.Error(e.localizedMessage ?: "Kesalahan tidak diketahui."))
        }
    }

    private suspend fun cleanUpOldTrash() {
        val trashList = trashDao.getAllSync()
        if (trashList.size > 30) {
            val trashToDelete = trashList.take(trashList.size - 30)
            trashDao.deleteTrashList(trashToDelete)
        }
    }

    fun getTrashHistory(): LiveData<List<Trash>> = trashDao.getAll()

    fun getTrash(id: Long): LiveData<Trash> = trashDao.getTrashById(id)
}

//class TrashRepository(
//    private val trashDao: TrashDao,
//    private val apiService: ApiService,
//    private val tutorialRepository: TutorialRepository
//) {
//
//    fun predictTrash(image: File): Flow<Resource<Long>> = flow {
//        try {
//            emit(Resource.Loading)
//
//            // Validasi file gambar
//            val imageFile = image.reduceFileImage()
//            if (!imageFile.exists() || imageFile.length() == 0L) {
//                emit(Resource.Error("Gambar tidak valid atau kosong."))
//                return@flow
//            }
//
//            // Siapkan request
//            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
//            val multipartBody = MultipartBody.Part.createFormData(
//                "file",
//                imageFile.name,
//                requestImageFile
//            )
//
//            // Prediksi di server
//            Log.d("TrashRepository", "Mengunggah gambar ke server...")
//            val response = apiService.predictTrash(multipartBody)
//
//            // Simpan hasil prediksi ke database
//            val trash = Trash(
//                predictedClass = response.predictedClass.data.nama,
//                videoUrl = response.predictedClass.data.video,
//                articleUrl = response.predictedClass.data.artikel,
//                imageData = imageFile.readBytes(),
//            )
//            val trashId = trashDao.insert(trash)
//
//            // Hapus data sampah lama
//            cleanUpOldTrash()
//            emit(Resource.Success(trashId))
//
//            Log.i("TrashRepository", "Prediksi berhasil: ${response.predictedClass.data.nama}")
//        } catch (e: UnknownHostException) {
//            Log.e("TrashRepository", "Tidak dapat terhubung ke server: ${e.localizedMessage}")
//            emit(Resource.Error("Tidak dapat terhubung ke server. Periksa koneksi internet Anda."))
//        } catch (e: SocketTimeoutException) {
//            Log.e("Network", "Timeout Error: ${e.message}")
//            emit(Resource.Error("Koneksi lambat atau server tidak merespons. Silakan coba lagi."))
//        } catch (e: IOException) {
//            Log.e("TrashRepository", "Kesalahan jaringan: ${e.localizedMessage}")
//            emit(Resource.Error("Kesalahan jaringan. Coba lagi nanti."))
//        } catch (e: Exception) {
//            Log.e("TrashRepository", "Kesalahan tidak diketahui: ${e.localizedMessage}")
//            emit(Resource.Error("Kesalahan tidak diketahui: ${e.localizedMessage}"))
//        }
//    }
//
//    fun fetchResultWithVideos(image: File): Flow<Resource<ResultUiState>> = flow {
//        try {
//            emit(Resource.Loading)
//
//            // Langkah 1: Prediksi Sampah
//            val imageFile = image.reduceFileImage()
//            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
//            val multipartBody = MultipartBody.Part.createFormData(
//                "file",
//                imageFile.name,
//                requestImageFile
//            )
//            val response = apiService.predictTrash(multipartBody)
//
//            val predictedClass = response.predictedClass.data
//            if (predictedClass == null) {
//                throw Exception("Data prediksi tidak valid.")
//            }
//
//            val videos = tutorialRepository.getVideosByTrashType(predictedClass.nama)
//
//            val resultUiState = ResultUiState(
//                trashType = predictedClass.nama,
//                imageUrl = predictedClass.video,
//                videos = videos
//            )
//            emit(Resource.Success(resultUiState))
//
//        } catch (e: Exception) {
//            Log.e("TrashRepository", "Kesalahan: ${e.localizedMessage}", e)
//            emit(Resource.Error(e.localizedMessage ?: "Kesalahan tidak diketahui."))
//        }
//    }
//
//
//    private suspend fun cleanUpOldTrash() {
//        val trashList = trashDao.getAllSync()
//        if (trashList.size > 30) {
//            val trashToDelete = trashList.take(trashList.size - 30)
//            trashDao.deleteTrashList(trashToDelete)
//        }
//    }
//
//    fun getTrashHistory(): LiveData<List<Trash>> = trashDao.getAll()
//
//    fun getTrash(id: Long): LiveData<Trash> = trashDao.getTrashById(id)
//}
