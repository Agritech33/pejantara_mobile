//package com.agritech.pejantaraapp.ui.components
//
//import android.Manifest
//import android.content.Context
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.net.Uri
//import android.widget.Toast
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.clickable
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.unit.sp
//import androidx.core.content.ContextCompat
//import com.google.android.gms.location.LocationServices
//
//@Composable
//fun EditLocation(
//    modifier: Modifier = Modifier
//) {
//    val context = LocalContext.current
//
//    val locationPermissionLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.RequestPermission(),
//        onResult = { isGranted ->
//            if (isGranted) {
//                navigateToCurrentLocation(context)
//            } else {
//                Toast.makeText(context, "Izin lokasi diperlukan untuk fitur ini.", Toast.LENGTH_SHORT).show()
//            }
//        }
//    )
//
//    Text(
//        text = "Edit",
//        color = Color.Black,
//        style = TextStyle(fontSize = 15.sp),
//        modifier = modifier.clickable {
//            if (ContextCompat.checkSelfPermission(
//                    context,
//                    Manifest.permission.ACCESS_FINE_LOCATION
//                ) == PackageManager.PERMISSION_GRANTED
//            ) {
//                try {
//                    navigateToCurrentLocation(context)
//                } catch (e: SecurityException) {
//                    Toast.makeText(context, "Izin lokasi tidak mencukupi.", Toast.LENGTH_SHORT).show()
//                }
//            } else {
//                locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
//            }
//        }
//    )
//}
//
//private fun navigateToCurrentLocation(context: Context) {
//    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
//
//    if (ContextCompat.checkSelfPermission(
//            context,
//            Manifest.permission.ACCESS_FINE_LOCATION
//        ) == PackageManager.PERMISSION_GRANTED
//    ) {
//        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
//            if (location != null) {
//                val latitude = location.latitude
//                val longitude = location.longitude
//
//                val gmmIntentUri =
//                    Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude(Current+Location)")
//                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
//                mapIntent.setPackage("com.google.android.apps.maps")
//                context.startActivity(mapIntent)
//            } else {
//                Toast.makeText(context, "Lokasi tidak ditemukan.", Toast.LENGTH_SHORT).show()
//            }
//        }.addOnFailureListener {
//            Toast.makeText(context, "Gagal mendapatkan lokasi. Pastikan GPS aktif.", Toast.LENGTH_SHORT).show()
//        }
//    } else {
//        Toast.makeText(context, "Izin lokasi belum diberikan.", Toast.LENGTH_SHORT).show()
//    }
//}
//
//
//
//
//
