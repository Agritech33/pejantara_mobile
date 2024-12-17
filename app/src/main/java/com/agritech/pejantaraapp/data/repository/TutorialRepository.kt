package com.agritech.pejantaraapp.data.repository

import com.agritech.pejantaraapp.data.model.TutorialModel
import com.agritech.pejantaraapp.data.retrofit.ApiService
import com.agritech.pejantaraapp.data.retrofit.response.toVideoModel
import com.agritech.pejantaraapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TutorialRepository(private val apiService: ApiService) {

    fun getVideos(): Flow<Resource<List<TutorialModel>>> = flow {
        try {
            emit(Resource.Loading)

            val response = apiService.getVideos()
            val videoModels = response.item.data.map { it.toVideoModel() }

            emit(Resource.Success(videoModels))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
        }
    }

    suspend fun getVideosByTrashType(trashType: String): List<TutorialModel> {
        return try {
            apiService.getVideos()
                .item.data
                .map { it.toVideoModel() }
                .filter { it.title.contains(trashType, ignoreCase = true) }
        } catch (e: Exception) {
            emptyList()
        }
    }
}


