package com.agritech.pejantaraapp.ui.screen.lapor

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditLocation
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.agritech.pejantaraapp.R
import com.agritech.pejantaraapp.ui.components.DateSelector
import com.agritech.pejantaraapp.ui.screen.profile.ProfileViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import java.io.File
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaporScreen(
    navController: NavController,
    laporViewModel: LaporViewModel,
    profileViewModel: ProfileViewModel,
    fusedLocationClient: FusedLocationProviderClient,
    onSubmit: () -> Unit
) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var textBerat by remember { mutableStateOf("") }
    var textDeskripsi by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    val context = LocalContext.current
    val options = listOf("Laporan Sampah", "Laporan TPS Liar")

//    LaunchedEffect(profileState) {
//        Log.d("HomeScreen", "Profile State: $profileState")
//    }

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) Log.d("LaporScreen", "Image captured successfully.")
        }

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let { imageUri = it }
        }

    val activity = LocalContext.current as Activity
    fun getCurrentLocation() {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            try {
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        val geocoder = Geocoder(context, Locale.getDefault())
                        val addresses =
                            geocoder.getFromLocation(location.latitude, location.longitude, 1)
                        if (addresses?.isNotEmpty() == true) {
                            address = addresses[0].getAddressLine(0) ?: ""
                        }
                    } else {
                        Log.e("LaporScreen", "Lokasi tidak ditemukan")
                    }
                }
            } catch (e: SecurityException) {
                Log.e("LaporScreen", "Izin lokasi tidak valid: ${e.message}")
            }
        } else {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
        }
    }

    val imageFile = remember { File(context.externalCacheDir, "temp_image.jpg") }
    val imageUriForCamera = FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        imageFile
    )

    fun showImageSourceDialog() {
        AlertDialog.Builder(context)
            .setTitle("Pilih Sumber Gambar")
            .setItems(arrayOf("Kamera", "Galeri")) { _, which ->
                when (which) {
                    0 -> cameraLauncher.launch(imageUriForCamera)
                    1 -> galleryLauncher.launch("image/*")
                }
            }
            .show()
    }

    LaunchedEffect(Unit) {
        getCurrentLocation()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
            .heightIn(max = 600.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
                    .size(150.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xffeeeeee))
                    .clickable { showImageSourceDialog() },
                contentAlignment = Alignment.Center
            ) {
                if (imageUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(imageUri),
                        contentDescription = "Gambar Laporan",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add_photo),
                        contentDescription = "Tambah Gambar",
                        tint = Color(0xFF4B675C),
                        modifier = Modifier.size(64.dp)
                    )
                }
            }
            Text("Tambahkan Gambar",
                color = Color(0xFF4B675C),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text("Alamat") },
                trailingIcon = {
                    IconButton(onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q="))
                        context.startActivity(intent)
                    }) {
                        Icon(Icons.Default.EditLocation, contentDescription = "Edit Alamat")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Text("Tanggal Laporan", fontSize = 12.sp, fontWeight = FontWeight.Bold)

            DateSelector(
                context = context,
                onDateSelected = { date -> selectedDate = date }
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = selectedOption,
                    onValueChange = {},
                    label = { Text("Jenis Laporan") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor() //
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    options.forEach { option ->
                        DropdownMenuItem(
                            onClick = {
                                selectedOption = option
                                expanded = false
                            },
                            text = { Text(option) }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            OutlinedTextField(
                value = textBerat,
                onValueChange = { textBerat = it },
                label = { Text("Berat (Kg)") },
                placeholder = { Text("Masukkan jumlah berat") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                trailingIcon = { Text("Kg", color = Color.Gray) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            OutlinedTextField(
                value = textDeskripsi,
                onValueChange = { if (it.length <= 500) textDeskripsi = it },
                label = { Text("Deskripsi") },
                placeholder = { Text("Deskripsi tambahan (max 500 karakter)") },
                maxLines = 4,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "${textDeskripsi.length}/500",
                style = TextStyle(fontSize = 12.sp, color = Color.Gray),
                modifier = Modifier.wrapContentSize(Alignment.Center)
            )

            Spacer(modifier = Modifier.height(24.dp))

        }

        item {
            Button(
                onClick = {
                    val berat = textBerat.toIntOrNull()
                    if (berat == null || berat <= 0) {
                        Toast.makeText(context, "Berat tidak valid atau kosong.", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    if (selectedOption.isEmpty()) {
                        Toast.makeText(context, "Pilih jenis laporan terlebih dahulu.", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    if (selectedDate.isEmpty()) {
                        Toast.makeText(context, "Pilih tanggal laporan terlebih dahulu.", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    laporViewModel.tambahLaporan(
                        jenis = selectedOption,
                        deskripsi = textDeskripsi,
                        berat = berat,
                        tanggal = selectedDate,
                        onSuccess = {
                            Toast.makeText(context, "Laporan berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
                            navController.navigate("lapor_success")
                        },
                        onFailure = { error ->
                            Toast.makeText(context, "Gagal menambahkan laporan: $error", Toast.LENGTH_SHORT).show()
                        },
                        updateCoins = { coins -> profileViewModel.updateCoins(coins) }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedOption.isNotEmpty()) Color(0xff273526) else Color.Gray
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "Laporkan",
                    color = Color.White,
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}


//@Preview(showBackground = true, widthDp = 412, heightDp = 917)
//@Composable
//fun Lapor1Preview() {
//    LaporScreen (onClick = {})
//}