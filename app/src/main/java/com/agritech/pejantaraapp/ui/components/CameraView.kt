//package com.agritech.pejantaraapp.ui.components
//
//import android.widget.Toast
//import androidx.camera.core.CameraSelector
//import androidx.camera.core.ImageCapture
//import androidx.camera.core.ImageCaptureException
//import androidx.camera.lifecycle.ProcessCameraProvider
//import androidx.camera.view.PreviewView
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Camera
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.viewinterop.AndroidView
//import androidx.core.content.ContextCompat
//import androidx.lifecycle.compose.LocalLifecycleOwner
//import com.agritech.pejantaraapp.util.createCustomTempFile
//import java.io.File
//import androidx.camera.core.Preview
//
//@Composable
//fun CameraView(onImageCaptured: (File) -> Unit) {
//    val context = LocalContext.current
//    val lifecycleOwner = LocalLifecycleOwner.current
//
//    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
//
//    val preview = remember { Preview.Builder().build() }
//    val imageCapture = remember { ImageCapture.Builder().build() }
//    val cameraSelector = remember { CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build() }
//
//    var cameraProvider: ProcessCameraProvider? by remember { mutableStateOf(null) }
//    val previewView = remember { PreviewView(context) }
//
//    LaunchedEffect(cameraProviderFuture) {
//        cameraProvider = cameraProviderFuture.get()
//
//        cameraProvider?.unbindAll()
//        cameraProvider?.bindToLifecycle(
//            lifecycleOwner,
//            cameraSelector,
//            preview,
//            imageCapture
//        )
//
//        preview.setSurfaceProvider(previewView.surfaceProvider)
//    }
//
//    Box(modifier = Modifier.fillMaxSize()) {
//        AndroidView(
//            factory = { previewView },
//            modifier = Modifier.fillMaxSize()
//        )
//
//        IconButton(
//            onClick = {
//                val photoFile = createCustomTempFile(context)
//                val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
//
//                imageCapture.takePicture(
//                    outputOptions,
//                    ContextCompat.getMainExecutor(context),
//                    object : ImageCapture.OnImageSavedCallback {
//                        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
//                            onImageCaptured(photoFile)
//                        }
//
//                        override fun onError(exception: ImageCaptureException) {
//                            Toast.makeText(context, "Error capturing image: ${exception.message}", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                )
//            },
//            modifier = Modifier
//                .align(Alignment.BottomCenter)
//                .padding(16.dp)
//        ) {
//            Icon(Icons.Filled.Camera, contentDescription = "Capture Image", tint = Color.White)
//        }
//    }
//}
//
