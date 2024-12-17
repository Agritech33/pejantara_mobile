package com.agritech.pejantaraapp.ui.screen.deteksi_sampah

import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.agritech.pejantaraapp.R
import com.agritech.pejantaraapp.ui.navigation.Screen
import com.agritech.pejantaraapp.util.Resource
import com.agritech.pejantaraapp.util.createCustomTempFile
import com.agritech.pejantaraapp.util.reduceFileImage
import com.agritech.pejantaraapp.util.uriToFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Composable
fun ScanScreen(
    navController: NavController,
    viewModel: ScanViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val uploadState by viewModel.uploadState.collectAsState()

    // CameraX-related states
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    var preview by remember { mutableStateOf<Preview?>(null) }
    var imageCapture by remember { mutableStateOf<ImageCapture?>(null) }
    var cameraSelector by remember { mutableStateOf(CameraSelector.DEFAULT_BACK_CAMERA) }
    val cameraExecutor: ExecutorService = remember { Executors.newSingleThreadExecutor() }

    // Handle Upload State
    when (uploadState) {
        is Resource.Loading -> {
            // Tampilkan indikator loading jika diperlukan
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is Resource.Success -> {
            Toast.makeText(context, "Deteksi Berhasil", Toast.LENGTH_SHORT).show()
            val imagePath = (uploadState as Resource.Success<Long>).data.toString()
            navController.navigate("result/${Uri.encode(imagePath)}") {
                popUpTo(Screen.Scan.route) { inclusive = false }
            }
            viewModel.resetUploadState()
        }
        is Resource.Error -> {
            Toast.makeText(context, (uploadState as Resource.Error).error, Toast.LENGTH_SHORT).show()
            viewModel.resetUploadState()
        }
    }

    // Gallery Launcher
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let { selectedUri ->
            val file = uriToFile(selectedUri, context) // Konversi URI ke File lokal
            val reducedFile = file.reduceFileImage()  // Optimalkan ukuran file
            viewModel.uploadImage(reducedFile)
        }
    }

    // Camera Permission Launcher
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            Toast.makeText(context, "Camera permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    // Request Camera Permission
    LaunchedEffect(Unit) {
        cameraPermissionLauncher.launch(android.Manifest.permission.CAMERA)
    }

    // PreviewView setup
    val previewView = remember { PreviewView(context) }

    // Initialize CameraX
    LaunchedEffect(cameraProviderFuture) {
        val cameraProvider = cameraProviderFuture.get()

        // Set up Preview Use Case
        val newPreview = Preview.Builder().build().also { preview ->
            preview.setSurfaceProvider(previewView.surfaceProvider)
        }

        // Set up ImageCapture Use Case
        val newImageCapture = ImageCapture.Builder().build()

        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                newPreview,
                newImageCapture
            )
            preview = newPreview
            imageCapture = newImageCapture
        } catch (e: Exception) {
            Log.e("ScanScreen", "Error binding camera: ${e.message}", e)
        }
    }

    // Main UI
    Box(modifier = Modifier.fillMaxSize()) {
        // Camera Preview
        AndroidView(
            factory = { previewView },
            modifier = Modifier.fillMaxWidth()
        )

        // Controls Overlay
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Add from Gallery
                IconButton(
                    onClick = { galleryLauncher.launch("image/*") },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add_image),
                        contentDescription = "Add Image",
                        modifier = Modifier.size(120.dp)
                    )
                }

                // Capture Button
                Button(
                    onClick = {
                        lifecycleOwner.lifecycleScope.launch {
                            val file = createCustomTempFile(context)
                            val outputOptions = ImageCapture.OutputFileOptions.Builder(file).build()

                            imageCapture?.takePicture(
                                outputOptions,
                                cameraExecutor,
                                object : ImageCapture.OnImageSavedCallback {
                                    override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                                        lifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                                            try {
                                                Log.d("ScanScreen", "Image saved to: ${file.path}")
                                                val reducedFile = file.reduceFileImage()
                                                val encodedImagePath = Uri.encode(reducedFile.path)
                                                viewModel.uploadImage(reducedFile)

                                                // Navigasi harus dilakukan di Main Thread
                                                navController.navigate("result/${Uri.encode(reducedFile.path)}")
                                            } catch (e: Exception) {
                                                Log.e("ScanScreen", "Error processing image", e)
                                            }
                                        }
                                    }

                                    override fun onError(exception: ImageCaptureException) {
                                        Log.e("ScanScreen", "Photo capture failed", exception)
                                    }
                                }
                            )
                        }
                    },
                    modifier = Modifier.size(80.dp),
                )
                    {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_capture),
                        contentDescription = "Capture"
                    )
                }
                // Switch Camera
                IconButton(
                    onClick = {
                        cameraSelector = if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                            CameraSelector.DEFAULT_FRONT_CAMERA
                        } else {
                            CameraSelector.DEFAULT_BACK_CAMERA
                        }
                    },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_switch_camera),
                        contentDescription = "Switch Camera",
                        modifier = Modifier.size(120.dp)
                    )
                }
            }
        }
    }
}


//@Preview(showBackground = true, widthDp = 412, heightDp = 917)
//@Composable
//fun PreviewScanScreen() {
//    ScanScreen(onPhotoCaptured = {})
//}




