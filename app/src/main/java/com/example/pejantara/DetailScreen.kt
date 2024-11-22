package com.example.pejantara

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pejantara.R

@Composable
fun detailScreen() {
    Scaffold(
        topBar = { detailTopBar() },
        bottomBar = { }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp)) // Jarak setelah topbar

            // Container untuk informasi sampah
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.8f), // Menggunakan 80% lebar layar
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Text(
                        text = "Nama Sampah",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        ),
                        modifier = Modifier.width(120.dp)
                    )
                    Text(
                        text = ": Botol plastik",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 1.dp)
                ) {
                    Text(
                        text = "Jenis Sampah",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        ),
                        modifier = Modifier.width(120.dp)
                    )
                    Text(
                        text = ": Sampah Anorganik",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp)) // Jarak sebelum gambar

            Image(
                painter = painterResource(id = R.drawable.education),
                contentDescription = "Botol Plastik",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(30.dp)) // Jarak sebelum gambar

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Botol plastik termasuk dalam kategori sampah anorganik karena terbuat dari bahan yang tidak berasal dari makhluk hidup dan sulit terurai oleh mikroorganisme. Plastik membutuhkan waktu ratusan hingga ribuan tahun untuk terdekomposisi secara alami. Oleh karena itu, penting untuk mengelola sampah botol plastik dengan bijak, seperti dengan mendaur ulang atau mengurangi penggunaannya.",
                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                            textAlign = TextAlign.Justify
                        ),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Text(
                        text = "Daur ulang botol plastik sangat penting karena bahan plastik bisa digunakan kembali untuk membuat produk baru, yang dapat mengurangi kebutuhan akan bahan baku baru dan mengurangi polusi plastik di lingkungan.",
                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                            textAlign = TextAlign.Justify
                        )
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun detailTopBar() {
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
                        text = "Keterangan",
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

//@Preview
//@ExperimentalMaterial3Api
//@Composable
//fun DetailScreenPreview(){
//    detailScreen()
//    }