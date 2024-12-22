package com.agritech.pejantaraapp.ui.screen.edukasi

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.agritech.pejantaraapp.ui.components.EdukasiItemCard

@Composable
fun EdukasiScreen(viewModel: EdukasiViewModel) {
    val articles by viewModel.articles.collectAsState()

    Log.d("EdukasiScreen", "Articles: $articles")

    Scaffold(
        content = { padding ->
            if (articles.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    items(articles) { article ->
                        Log.d("EdukasiScreen", "Rendering Article: $article")
                        EdukasiItemCard(article = article)
                    }
                }
            }
        }
    )
}

