package com.agritech.pejantaraapp.ui.screen.bank_sampah

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import com.agritech.pejantaraapp.R
import com.agritech.pejantaraapp.data.model.BankSampah
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.BitmapDescriptorFactory

@Composable
fun BankSampahScreen(
    viewModel: BankSampahViewModel = viewModel(),
    fusedLocationProviderClient: FusedLocationProviderClient,
    context: Context
) {
    val bankSampahList by viewModel.bankSampahList.observeAsState(emptyList())
    val (query, setQuery) = remember { mutableStateOf("") }
    val filteredList = bankSampahList.filter {
        it.name.contains(query, ignoreCase = true) ||
                it.address.contains(query, ignoreCase = true)
    }
    val userLocation = remember { mutableStateOf(LatLng(0.0, 0.0)) }

    LaunchedEffect(Unit) {
        getUserLocation(fusedLocationProviderClient, context) { location ->
            userLocation.value = location
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TextField(
            value = query,
            onValueChange = setQuery,
            placeholder = { Text("Cari lokasi Bank Sampah") },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Search Icon")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(Color.White, RoundedCornerShape(8.dp))
        )

        BankSampahMap(
            bankSampahList = bankSampahList,
            userLocation = userLocation.value
        )

        if (filteredList.isEmpty()) {
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tidak ada Bank Sampah yang ditemukan.",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(filteredList) { bankSampah ->
                    BankSampahItem(bankSampah = bankSampah) {
                        println("Clicked: ${bankSampah.name}")
                    }
                }
            }
        }
    }
}

fun isValidLatLng(lat: Double, lng: Double): Boolean {
    return lat in -90.0..90.0 && lng in -180.0..180.0
}

@Composable
fun BankSampahMap(bankSampahList: List<BankSampah>, userLocation: LatLng) {
    val defaultLocation = LatLng(-6.200000, 106.816666) // Jakarta sebagai fallback
    val currentLocation = if (isValidLatLng(userLocation.latitude, userLocation.longitude)) {
        userLocation
    } else {
        defaultLocation
    }

    GoogleMap(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(currentLocation, 15f)
        }
    ) {
        Marker(
            state = MarkerState(position = currentLocation),
            title = "Lokasi Anda",
            icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
        )
        bankSampahList.forEach { bankSampah ->
            if (isValidLatLng(bankSampah.latitude, bankSampah.longitude)) {
                Marker(
                    state = MarkerState(position = LatLng(bankSampah.latitude, bankSampah.longitude)),
                    title = bankSampah.name,
                    snippet = bankSampah.address
                )
            } else {
                Log.e("BankSampahMap", "Invalid coordinates for ${bankSampah.name}")
            }
        }
    }
}


fun getUserLocation(
    fusedLocationProviderClient: FusedLocationProviderClient,
    context: Context,
    onLocationFetched: (LatLng) -> Unit
) {
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            1
        )
    } else {
        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener { location ->
                location?.let {
                    onLocationFetched(LatLng(it.latitude, it.longitude))
                }
            }
    }

}

@Composable
fun BankSampahItem(bankSampah: BankSampah, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Image(
            painter = painterResource(id = R.drawable.lokasimaps),
            contentDescription = "Lokasi Maps",
            modifier = Modifier.size(60.dp)
        )
        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f)
        ) {
            Text(
                text = bankSampah.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = bankSampah.address,
                fontSize = 14.sp
            )
        }
    }
}

//    @Preview(widthDp = 412, heightDp = 997)
//    @Composable
//    private fun BankSampahPreview() {
//        BankSampahScreen(Modifier)
//    }
