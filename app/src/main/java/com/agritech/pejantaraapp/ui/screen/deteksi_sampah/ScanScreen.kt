package com.agritech.pejantaraapp.ui.screen.deteksi_sampah

import android.Manifest
import android.content.pm.PackageManager
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
import androidx.lifecycle.Lifecycle
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
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    var preview by remember { mutableStateOf<Preview?>(null) }
    var imageCapture by remember { mutableStateOf<ImageCapture?>(null) }
    var cameraSelector by remember { mutableStateOf(CameraSelector.DEFAULT_BACK_CAMERA) }
    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }

    // Setup PreviewView
    val previewView = remember { PreviewView(context) }

    // Lifecycle Aware Camera Setup
    LaunchedEffect(cameraSelector) {
        val cameraProvider = cameraProviderFuture.get()

        // Release previous bindings
        cameraProvider.unbindAll()

        try {
            // Reinitialize use cases
            val newPreview = Preview.Builder().build().apply {
                setSurfaceProvider(previewView.surfaceProvider)
            }
            val newImageCapture = ImageCapture.Builder().build()

            // Bind to lifecycle
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                newPreview,
                newImageCapture
            )
            preview = newPreview
            imageCapture = newImageCapture
            Log.d("ScanScreen", "Camera switched to: ${if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) "Back" else "Front"}")
        } catch (e: Exception) {
            Log.e("ScanScreen", "Error switching camera: ${e.message}", e)
        }
    }

    // Main UI
    Box(modifier = Modifier.fillMaxSize()) {
        // Camera Preview
        AndroidView(
            factory = { previewView },
            modifier = Modifier.fillMaxSize()
        )

        // Controls
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
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
                                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                                        lifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                                            val reducedFile = file.reduceFileImage()
                                            viewModel.uploadImage(reducedFile)
                                            navController.navigate("result/${Uri.encode(reducedFile.path)}")
                                        }
                                    }

                                    override fun onError(exception: ImageCaptureException) {
                                        Log.e("ScanScreen", "Photo capture failed", exception)
                                    }
                                }
                            )
                        }
                    }
                ) {
                    Text("Capture")
                }

                // Switch Camera Button
                Button(
                    onClick = {
                        cameraSelector = if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                            CameraSelector.DEFAULT_FRONT_CAMERA
                        } else {
                            CameraSelector.DEFAULT_BACK_CAMERA
                        }
                    }
                ) {
                    Text("Switch Camera")
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




