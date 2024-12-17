package com.agritech.pejantaraapp.ui.screen.edukasi

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agritech.pejantaraapp.data.Article
import com.agritech.pejantaraapp.data.repository.EdukasiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EdukasiViewModel(private val repository: EdukasiRepository) : ViewModel() {

    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    val articles: StateFlow<List<Article>> = _articles

    init {
        loadArticles()
    }

    private fun loadArticles() {
        viewModelScope.launch {
            val apiArticles = repository.fetchArticlesFromApi()
            if (apiArticles != null) {
                Log.d("EdukasiViewModel", "Loaded Articles from API: $apiArticles")
                _articles.value = apiArticles
            } else {
                val staticArticles = repository.fetchStaticArticles()
                Log.d("EdukasiViewModel", "Loaded Static Articles: $staticArticles")
                _articles.value = staticArticles
            }
        }
    }
}

