package com.agritech.pejantaraapp.data.repository

import android.util.Log
import com.agritech.pejantaraapp.data.Article
import com.agritech.pejantaraapp.data.EdukasiDataSource
import com.agritech.pejantaraapp.data.retrofit.ApiService

class EdukasiRepository(private val apiService: ApiService?) {
    fun fetchStaticArticles(): List<Article> {
        val staticArticles = EdukasiDataSource.articles
        Log.d("EdukasiRepository", "Static Articles: $staticArticles")
        return staticArticles
    }

    suspend fun fetchArticlesFromApi(): List<Article>? {
        Log.d("EdukasiRepository", "Fetching articles from API...")
        if (apiService != null) {
            try {
                val response = apiService.getArticles()
                Log.d("EdukasiRepository", "API Response: $response")
                if (response.isSuccessful) {
                    val apiArticles = response.body()
                    Log.d("EdukasiRepository", "API Articles: $apiArticles")
                    return apiArticles
                } else {
                    Log.e("EdukasiRepository", "Failed API Response: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("EdukasiRepository", "Error fetching articles: ${e.message}")
            }
        } else {
            Log.e("EdukasiRepository", "API Service is not initialized.")
        }
        return null
    }

}

