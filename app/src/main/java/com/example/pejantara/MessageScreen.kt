package com.example.pejantara

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MassageScreen(){
    Scaffold (
        topBar = { massageTopBar() },
        bottomBar = { massageBottomBar() }
    ) {
            paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
        ){
            // Menambahkan konten chat
            ChatMessage(
                profileImage = R.drawable.education, // Pastikan gambar tersedia di resources
                senderName = "Admin",
                message = "Laporan anda telah kami terima, dan anda berhak mendapatkan reward berdasarkan laporan.",
                reward = "5,000"
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun massageTopBar() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),
        shadowElevation = 1.dp
    ) {
        TopAppBar(
            modifier = Modifier.height(45.dp),
            title = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(), // Tambahkan fillMaxHeight()
                    contentAlignment = Alignment.Center // Tambahkan contentAlignment
                ) {
                    // Arrow di kiri
                    IconButton(
                        onClick = { /* navigasi kembali */ },
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .size(40.dp) // Sesuaikan ukuran IconButton
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = "Kembali",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp) // Sesuaikan ukuran Icon
                        )
                    }

                    // Text di tengah absolute
                    Text(
                        text = "Call Center",
                        modifier = Modifier.align(Alignment.Center),
                        style = TextStyle(
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp // Kurangi sedikit ukuran font
                        ),
                        color = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFF45624E)
            )
        )
    }
}

@Composable
private fun massageBottomBar() {
    Surface(
        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),

        color = Color(0xFF45624E)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Tombol tambah
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Tambah",
                tint = Color.White,
                modifier = Modifier.size(30.dp)
            )

            // Tombol kamera
            Icon(
                imageVector = Icons.Default.CameraAlt,
                contentDescription = "Kamera",
                tint = Color.White,
                modifier = Modifier.size(30.dp)
            )

            // Search bar (yang abu-abu di tengah)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(35.dp)
                    .padding(horizontal = 8.dp)
                    .background(
                        color = Color.DarkGray.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(20.dp)
                    )
            )

            // Tombol mikrofon
            Icon(
                imageVector = Icons.Default.Mic,
                contentDescription = "Mikrofon",
                tint = Color.White,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Composable
fun ChatMessage(
    profileImage: Int,
    senderName: String,
    message: String,
    reward: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Kolom untuk gambar profil dan nama
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(end = 8.dp)
                .height(75.dp)
        ) {
            // Profile image dengan border biru
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = Color(0xFF2196F3),
                        shape = CircleShape
                    )
                    .padding(1.5.dp)
            ) {
                Image(
                    painter = painterResource(id = profileImage),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            Text(
                text = senderName,
                style = TextStyle(
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Normal
                ),
                modifier = Modifier.padding(top = 3.dp)
            )
        }

        // Bubble chat
        Box(
            modifier = Modifier
                .weight(1f)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(25.dp)
                )
                .border(
                    width = 0.7.dp,
                    color = Color(0xFF4CAF50),
                    shape = RoundedCornerShape(25.dp)
                )
        ) {
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                // Pesan utama
                Text(
                    text = message,
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = Color.Black
                    )
                )

                // Reward text dengan alignment ke kanan
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Reward yang didapatkan : ",
                        style = TextStyle(
                            fontSize = 10.sp,
                            color = Color.Gray
                        )
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.education),
                        contentDescription = "Coin",
                        modifier = Modifier.size(12.dp),
                        tint = Color(0xFFFFD700)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = reward,
                        style = TextStyle(
                            fontSize = 10.sp,
                            color = Color.Gray
                        )
                    )
                }
            }
        }
    }
}

//@Preview
//@ExperimentalMaterial3Api
//@Composable
//fun MassageScreenPreview(){
//    MassageScreen()
//}