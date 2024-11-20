package com.example.pejantara

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun KeteranganScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 25.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            backgroundColor = Color(0xFF45624E),
            title = {
                androidx.compose.material.Text("Keterangan", color = Color.White)
            },
            navigationIcon = {
                androidx.compose.material.IconButton(onClick = { /* Handle back navigation */ }) {
                    androidx.compose.material.Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Image(
                    painter = painterResource(id = R.drawable.keterangan_1),
                    contentDescription = "Keterangan1",
                )
                Text(
                    "Yuk cari tahu cara mendaur ulang sampah jenis plastik!",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            items(listOf(
                R.drawable.keterangan_2,
                R.drawable.keterangan_3,
                R.drawable.keterangan_4
            )) { imageRes ->
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = "Keterangan",
                )
            }
        }
    }
}