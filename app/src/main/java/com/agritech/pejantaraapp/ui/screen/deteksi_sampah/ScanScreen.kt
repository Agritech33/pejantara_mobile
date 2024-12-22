package com.agritech.pejantaraapp.ui.screen.deteksi_sampah

import android.net.Uri
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.agritech.pejantaraapp.util.createCustomTempFile
import com.agritech.pejantaraapp.util.reduceFileImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    val previewView = remember { PreviewView(context) }

    LaunchedEffect(cameraSelector) {
        val cameraProvider = cameraProviderFuture.get()

        cameraProvider.unbindAll()

        try {
            val newPreview = Preview.Builder().build().apply {
                setSurfaceProvider(previewView.surfaceProvider)
            }
            val newImageCapture = ImageCapture.Builder().build()

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

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { previewView },
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
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




