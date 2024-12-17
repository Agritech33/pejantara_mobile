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
        if (apiService != null) {
            val response = apiService.getArticles()
            Log.d("EdukasiRepository", "API Response: $response")
            if (response.isSuccessful) {
                val apiArticles = response.body()
                Log.d("EdukasiRepository", "API Articles: $apiArticles")
                return apiArticles
            }
        }
        return null
    }
}

