package com.example.pejantara

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Button
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HistoryScreen() {
    var selectedTab by remember { mutableStateOf("laporan") }
    Scaffold(
        topBar = { HistoryTopBar() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
        )
        { // Menambah jarak dengan Spacer
            TabButtons(selectedTab) { newTab -> selectedTab = newTab }

            when (selectedTab) {
                "laporan" -> RiwayatLaporanContent()
                "deteksi" -> RiwayatDeteksiContent()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HistoryTopBar() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
        shadowElevation = 1.dp

    ) {
        TopAppBar(
            title = {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Arrow di kiri
                    IconButton(
                        onClick = { /* navigasi kembali */ },
                        modifier = Modifier.align(Alignment.CenterStart)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Kembali",
                            tint = Color.White
                        )
                    }
                    // Text di tengah absolute
                    Text(
                        text = "Riwayat",
                        modifier = Modifier.align(Alignment.Center),
                        style = TextStyle(
                            fontWeight = FontWeight.Medium,
                            fontSize = 18.sp),
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
private fun TabButtons(
    selectedTab: String,
    onTabSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 40.dp, top = 40.dp),

        horizontalArrangement = Arrangement.SpaceEvenly, // Ubah ke SpaceEvenly
    ) {

        Button(
            onClick = { onTabSelected("laporan") },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedTab == "laporan")
                    Color(0xFF273526)
                else
                    Color.White,
                contentColor = if (selectedTab == "laporan")
                    Color.White
                else
                    Color.Black
            ),
            modifier = Modifier
                .width(160.dp)  // Ganti weight dengan width tetap
                .height(35.dp),
            border = BorderStroke(
                    width = 1.dp,
                    color = if (selectedTab == "laporan") Color(0xFF273526) else Color.Black
                ),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text(
                "Riwayat Laporan",
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
            )
        }

        Button(
            onClick = { onTabSelected("deteksi") },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedTab == "deteksi")
                    Color(0xFF273526)
                else
                    Color.White,
                contentColor = if (selectedTab == "deteksi")
                    Color.White
                else
                    Color.Black
            ),
            modifier = Modifier
                .width(160.dp)  // Ganti weight dengan width tetap
                .height(35.dp),
            border = BorderStroke(
                    width = 1.dp,
                color = if (selectedTab == "laporan") Color(0xFF273526) else Color.Black
        ),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text(
                "Riwayat Deteksi",
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
            )
        }
    }
}

@Composable
private fun TabButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected)
                Color(0xFF273526)
            else
                MaterialTheme.colorScheme.surface,
            contentColor = if (isSelected)
                MaterialTheme.colorScheme.onPrimary
            else
                MaterialTheme.colorScheme.primary
        ),

        shape = RoundedCornerShape(24.dp)
    ) {
        Text(text = text)
    }
}

@Composable
fun RiwayatLaporanContent() {
    LazyColumn(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(5) { index ->
            HistoryItem(
                title = "Jenis Laporan: TPS Liar",
                description = "Ada TPS liar di jalan A",
                hasNotification = index == 0 || index == 3
            )
        }
    }
}

@Composable
fun RiwayatDeteksiContent() {
    LazyColumn(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(4) { index ->
            HistoryItem(
                title = if (index % 2 == 0) "Sampah Anorganik" else "Sampah Organik",
                description = "",
                showImage = true,
               imageRes = if (index % 2 == 0) R.drawable.education else R.drawable.education // Ganti dengan resource gambar yang sesuai
            )
        }
    }
}

@Composable
fun HistoryItem(
    title: String,
    description: String,
    hasNotification: Boolean = false,
    showImage: Boolean = false,
    @DrawableRes imageRes: Int? = null
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color(0xFFC0CFB2), // Mengubah warna background item menjadi hijau muda
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (showImage && imageRes != null) {
                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = null,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(4.dp))
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                }

                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black
                    )
                    if (description.isNotEmpty()) {
                        Text(
                            text = description,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box {

                    Button(
                        onClick = { /* TODO */ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.Black.copy(alpha = 0.7f)
                        ),
                        shape = RoundedCornerShape(20.dp),
                        border = BorderStroke(1.dp, Color.Black),

                        modifier = Modifier
                            .height(30.dp)
                            .width(84.dp)
                    ) {
                        Text(
                            text = "Detail",
                            modifier = Modifier,
                            style = TextStyle(
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp  // Ukuran font ditingkatkan dari 10sp ke 14sp
                            )
                        )
                    }

                    if (hasNotification) {
                        Box(
                            modifier = Modifier
                                .size(18.dp)
                                .background(Color.Red, CircleShape)
                                .align(Alignment.TopEnd)
                                .offset(x = 3.dp, y = (-3).dp)
                        )
                    }
                }
            }
        }
    }
}

//@Preview
//@ExperimentalMaterial3Api
//@Composable
//fun HistoryScreenDeteksiPreview() {
//    var selectedTab by remember { mutableStateOf("deteksi") }
////    var selectedTab by remember { mutableStateOf("laporan") }
//    Scaffold(
//        topBar = { HistoryTopBar() }
//    ) { paddingValues ->
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(paddingValues)
//        ) {
//            TabButtons(selectedTab) { newTab -> selectedTab = newTab }
//            RiwayatDeteksiContent()
////            RiwayatLaporanContent()
//        }
//    }
//}
