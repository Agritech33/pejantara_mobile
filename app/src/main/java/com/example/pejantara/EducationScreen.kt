package com.example.pejantara

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Share
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun educationScreen() {
    Scaffold(
        topBar = {
            educationTopBar()  // Ubah nama fungsi menjadi lebih deskriptif
        }
    ) { paddingValues ->
        EducationContent(paddingValues)  // Pisahkan konten ke fungsi terpisah
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun educationTopBar() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp), // Hapus rounded corner
        shadowElevation = 0.dp // Hapus shadow
    ) {
        TopAppBar(
            title = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    // Icon share di kanan
                    IconButton(
                        onClick = { /* TODO */ },
                        modifier = Modifier.align(Alignment.CenterEnd)
                    ) {
                        Icon(
                            imageVector = Icons.Default.BookmarkBorder,
                            contentDescription = "Bookmark",
                            tint = Color.White
                        )
                    }

                    // Icon bookmark di kanan
                    IconButton(
                        onClick = { /* TODO */ },
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share",
                            tint = Color.White
                        )
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFF45624E)
            ),
            navigationIcon = {
                IconButton(onClick = { /* TODO */ }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }
        )
    }
}

@Composable
private fun EducationContent(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues)
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Pengenalan Jenis-jenis Sampah",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier
                .padding(vertical = 16.dp)
                .align(Alignment.CenterHorizontally)
        )

        Image(
            painter = painterResource(id = R.drawable.education),
            contentDescription = "Ilustrasi Sampah",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(30.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = Color.Black,
                        )
                        .padding(16.dp)
                ) {
                    Column {
                        Text(
                            text = "Pengenalan jenis-jenis sampah sangat penting dalam upaya pengelolaan sampah yang efektif dan ramah lingkungan. Secara umum, sampah dapat dibagi berdasarkan karakteristik fisik, bahan yang terkandung, dan proses penguraian sampah tersebut.",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(vertical = 16.dp),
                            textAlign = TextAlign.Justify
                        )

                        Text(
                            text = "Berikut adalah pengelompokan jenis-jenis sampah:",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 16.dp),
                            textAlign = TextAlign.Justify
                        )

                        // Sampah Organik
                        JenisSampahSection(
                            title = "1. Sampah Organik",
                            description = "Sampah organik adalah sampah yang berasal dari bahan-bahan alami dan dapat terurai dengan mudah oleh mikroorganisme.",
                            examples = listOf(
                                "Sisa makanan (sayur, buah, daging, nasi, dan lainnya)",
                                "Sampah kebun (daun, ranting, rumput)",
                                "Kertas yang terbuat dari bahan organik",
                                "Kotoran hewan"
                            ),
                            pengelolaan = "Pengelolaan: Sampah organik biasanya dapat diolah menjadi kompos yang bisa digunakan untuk pupuk tanaman."
                        )

                        // Sampah Anorganik
                        JenisSampahSection(
                            title = "2. Sampah Anorganik",
                            description = "Sampah anorganik adalah sampah yang terbuat dari bahan-bahan yang tidak dapat terurai oleh mikroorganisme atau membutuhkan waktu sangat lama untuk terurai.",
                            examples = listOf(
                                "Plastik (kantong plastik, botol plastik, bungkus makanan)",
                                "Logam (kaleng, aluminium foil)",
                                "Kaca (botol, kaca pecah)",
                                "Kertas (koran, majalah, kertas karton, meskipun kertas ini bisa didaur ulang)"
                            ),
                            pengelolaan = "Pengelolaan: Sampah anorganik dapat didaur ulang, terutama plastik, kertas, dan logam."
                        )

                        // Sampah B3
                        JenisSampahSection(
                            title = "3. Sampah B3 (Bahan Berbahaya dan Beracun)",
                            description = "Sampah B3 adalah sampah yang mengandung bahan kimia berbahaya atau beracun yang dapat membahayakan kesehatan manusia dan lingkungan.",
                            examples = listOf(
                                "Baterai bekas",
                                "Lampu neon atau lampu LED yang mengandung merkuri",
                                "Obat kadaluwarsa",
                                "Cat dan pelarut kimia",
                                "Pestisida dan bahan kimia lainnya"
                            ),
                            pengelolaan = "Pengelolaan: Sampah B3 harus diproses secara khusus dengan prosedur pengelolaan yang aman, seperti pendaurulangan yang sesuai atau penghancuran oleh lembaga yang berwenang."
                        )

                        // Sampah Elektronik
                        JenisSampahSection(
                            title = "4. Sampah Elektronik (E-Waste)",
                            description = "Sampah elektronik adalah sampah yang berasal dari perangkat elektronik dan peralatan listrik yang sudah tidak terpakai lagi.",
                            examples = listOf(
                                "Televisi, komputer, dan laptop",
                                "Ponsel dan charger",
                                "Mesin cuci dan peralatan rumah tangga lainnya",
                                "Baterai lithium dari perangkat elektronik"
                            ),
                            pengelolaan = "Pengelolaan: Sampah elektronik sebaiknya didaur ulang oleh perusahaan yang memiliki fasilitas khusus untuk menangani limbah elektronik."
                        )

                        // Sampah Residu
                        JenisSampahSection(
                            title = "5. Sampah Residu",
                            description = "Sampah residu adalah sampah yang tidak dapat didaur ulang atau dimanfaatkan lagi dan biasanya akan dibuang ke tempat pembuangan akhir (TPA).",
                            examples = listOf(
                                "Pembungkus makanan yang terbuat dari plastik multilayer",
                                "Sampah yang tercampur antara organik dan anorganik",
                                "Sampah yang sudah rusak dan tidak bisa didaur ulang"
                            ),
                            pengelolaan = "Pengelolaan: Sampah residu sebaiknya dikurangi dengan cara memilah sampah dan mengurangi penggunaan barang sekali pakai."
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun JenisSampahSection(
    title: String,
    description: String,
    examples: List<String>,
    pengelolaan: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(vertical = 8.dp),
            textAlign = TextAlign.Justify
        )
        Text(
            text = "Contoh sampah ${title.lowercase().split(".")[1].trim()}:",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        examples.forEach { example ->
            Row(
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            ) {
                Text("• ", style = MaterialTheme.typography.bodyMedium)
                Text(
                    text = example,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        Text(
            text = pengelolaan,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 8.dp),
            textAlign = TextAlign.Justify
        )
    }
}

//@Preview
//@Composable
//fun educationScreenPreview(){
//    educationScreen()
//}

