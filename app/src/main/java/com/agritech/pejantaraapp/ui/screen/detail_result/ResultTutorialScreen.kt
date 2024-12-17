package com.agritech.pejantaraapp.ui.screen.detail_result

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.agritech.pejantaraapp.data.model.TutorialModel
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.agritech.pejantaraapp.util.Resource
import java.io.File

@Composable
fun ResultTutorialScreen(
    imagePath: String,
    onBack: () -> Unit,
    viewModel: ResultViewModel = hiltViewModel()
) {
    // Decode dan validasi path
    val decodedImagePath = Uri.decode(imagePath)
    val file = File(decodedImagePath)

    if (!file.exists()) {
        Log.e("ResultTutorialScreen", "File tidak ditemukan: $decodedImagePath")
        ErrorScreen("Gambar tidak valid atau tidak ditemukan") {
            onBack()
        }
        return
    }

    // Load hasil deteksi
    LaunchedEffect(decodedImagePath) {
        viewModel.loadResult(file)
    }

    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        is Resource.Loading -> LoadingScreen()
        is Resource.Success -> {
            val result = (uiState as Resource.Success<ResultUiState>).data
            ResultContent(
                trashType = result.trashType,
                imageFile = file,
                onBack = onBack
            )
        }
        is Resource.Error -> {
            val errorMessage = (uiState as Resource.Error).error
            ErrorScreen(message = errorMessage, onBack = onBack)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultContent(
    trashType: String,
    imageFile: File,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Kembali",
                            modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.CenterStart))
                    }
                },
                title = {
                    Text(
                        text = "Hasil Deteksi",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)),

                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color(0xFF4B675C)
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF5F5F5)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Tampilkan gambar lokal
            Image(
                painter = rememberAsyncImagePainter(model = imageFile),
                contentDescription = "Hasil Deteksi",
                modifier = Modifier
                    .size(250.dp)
                    .clip(MaterialTheme.shapes.medium)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Nama jenis sampah
            Text(
                text = "Jenis Sampah: $trashType",
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFF4CAF50)
            )
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(message: String, onBack: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = message,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onBack) {
                Text("Kembali")
            }
        }
    }
}



//@Composable
//fun ResultTutorialScreen(
//    imagePath: String,
//    onBack: () -> Unit,
//    viewModel: ResultViewModel = hiltViewModel()
//) {
//    val uiState by viewModel.uiState.collectAsState()
//
//    if (imagePath.isBlank() || !File(imagePath).exists()) {
//        ErrorScreen("Gambar tidak valid atau tidak ditemukan", onBack = onBack)
//        return
//    }
//
//    LaunchedEffect(imagePath) {
//        viewModel.loadResult(File(imagePath))
//    }
//
//    when (uiState) {
//        is Resource.Loading -> {
//            LoadingScreen()
//        }
//        is Resource.Success -> {
//            val result = (uiState as Resource.Success<ResultUiState>).data
//            ResultTutorialContent(
//                trashType = result.trashType,
//                videos = result.videos,
//                imageUrl = result.imageUrl,
//                onBack = onBack
//            )
//        }
//        is Resource.Error -> {
//            val errorMessage = (uiState as Resource.Error).error
//            ErrorScreen(message = errorMessage, onBack = onBack)
//        }
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ResultTutorialContent(
//    trashType: String,
//    videos: List<TutorialModel>,
//    imageUrl: String,
//    onBack: () -> Unit
//) {
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Keterangan") },
//                navigationIcon = {
//                    IconButton(onClick = onBack) {
//                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
//                    }
//                },
//                colors = TopAppBarDefaults.mediumTopAppBarColors(
//                    containerColor = Color(0xFF4CAF50),
//                    titleContentColor = Color.White
//                )
//            )
//        }
//    ) { innerPadding ->
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(innerPadding)
//                .background(Color(0xFFF5F5F5)),
//            contentPadding = PaddingValues(bottom = 16.dp)
//        ) {
//            Log.d("ResultTutorialScreen", "LazyColumn rendered with size constraints")
//            // Header (Gambar dan Informasi)
//            item {
//                Image(
//                    painter = rememberImagePainter(
//                        data = imageUrl,
//                        builder = {
//                            placeholder(R.drawable.ic_add_photo)
//                            error(R.drawable.icon_history)
//                        }
//                    ),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .aspectRatio(16 / 9f)
//                        .padding(16.dp)
//                        .clip(MaterialTheme.shapes.medium)
//                )
//
//                Text(
//                    text = "Jenis Sampah: $trashType",
//                    style = MaterialTheme.typography.titleLarge,
//                    color = Color(0xFF4CAF50),
//                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
//                )
//
//                Text(
//                    text = "Tutorial Terkait",
//                    style = MaterialTheme.typography.titleLarge,
//                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
//                )
//            }
//
//            // Konten List (Video Tutorial)
//            if (videos.isEmpty()) {
//                item {
//                    Column(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//                        Text(
//                            text = "Tidak ada tutorial terkait.",
//                            style = MaterialTheme.typography.bodyMedium,
//                            modifier = Modifier.padding(16.dp)
//                        )
//                        Spacer(modifier = Modifier.height(16.dp))
//                        Button(onClick = onBack) {
//                            Text("Kembali")
//                        }
//                    }
//                }
//            } else {
//                items(videos) { video ->
//                    Card(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(16.dp),
//                        elevation = CardDefaults.cardElevation(4.dp)
//                    ) {
//                        Row {
//                            Image(
//                                painter = rememberImagePainter(video.photo),
//                                contentDescription = null,
//                                modifier = Modifier
//                                    .size(100.dp)
//                                    .clip(MaterialTheme.shapes.medium)
//                            )
//                            Spacer(modifier = Modifier.width(8.dp))
//                            Text(
//                                text = video.title,
//                                style = MaterialTheme.typography.bodyLarge,
//                                modifier = Modifier.align(Alignment.CenterVertically)
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun LoadingScreen() {
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        CircularProgressIndicator()
//    }
//}
//
//@Composable
//fun ErrorScreen(message: String, onBack: (() -> Unit)? = null) {
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        Column(horizontalAlignment = Alignment.CenterHorizontally) {
//            Text(
//                text = message,
//                textAlign = TextAlign.Center
//            )
//            onBack?.let {
//                Spacer(modifier = Modifier.height(16.dp))
//                Button(onClick = it) {
//                    Text("Kembali")
//                }
//            }
//        }
//    }
//}





