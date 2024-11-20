package com.example.pejantara

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ProfileScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            backgroundColor = Color(0xFF45624E),
            title = {
                Text("Profil", color = Color.White)
            },
            navigationIcon = {
                IconButton(onClick = { /* Handle back navigation */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        ProfilePicture()
        Spacer(modifier = Modifier.height(20.dp))
        JumlahUang()
        Spacer(modifier = Modifier.height(25.dp))
        Content()
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
fun ProfilePicture() {
    Box(
        contentAlignment = Alignment.BottomEnd,
        modifier = Modifier
            .size(120.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.profile_photo),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(Color(0xFF45624E))
                .clickable { /* Handle edit icon click */ }
                .padding(4.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_edit_camera),
                contentDescription = "Edit Icon",
                tint = Color.White
            )
        }
    }
}

@Composable
fun JumlahUang() {
    Box(
        modifier = Modifier
            .shadow(elevation = 4.dp, RectangleShape)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_coin),
                    contentDescription = "Jumlah Uang",
                )
                Text("5.000", fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text("Rp. 20.000,00", fontSize = 24.sp)
        }
    }
}

@Composable
fun Content() {
    var isNotificationSwitchOn by remember { mutableStateOf(false) }
    var isLocationSwitchOn by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Akun", fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
        item {
            Box(
                modifier = Modifier
                    .shadow(elevation = 4.dp, RectangleShape)
                    .padding(16.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Text("Nama", fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text("Asep Syaifullah", fontSize = 18.sp)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(id = R.drawable.ic_edit),
                        contentDescription = "Edit",
                        modifier = Modifier.clickable { /* Handle edit click */ }
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
        item {
            Box(
                modifier = Modifier
                    .shadow(elevation = 4.dp, RectangleShape)
                    .padding(16.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Text("Telepon", fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text("+62 812-3456-7890", fontSize = 18.sp)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(id = R.drawable.ic_edit),
                        contentDescription = "Edit",
                        modifier = Modifier.clickable { /* Handle edit click */ }
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
        item {
            Box(
                modifier = Modifier
                    .shadow(elevation = 4.dp, RectangleShape)
                    .padding(16.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Text("Email", fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text("asepsyaifullah04@gmail.com", fontSize = 18.sp)
                    }
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
        }
        item {
            Divider(color = Color.Black, thickness = 1.dp)
            Spacer(modifier = Modifier.height(15.dp))
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Notifikasi", fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
        item {
            Box(
                modifier = Modifier
                    .shadow(elevation = 4.dp, RectangleShape)
                    .padding(16.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Text("Notifikasi aktivitas", fontSize = 18.sp)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            "Telusuri yang Anda sukai, hal terkini, rekomendasi tutorial",
                            fontSize = 12.sp
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Switch(
                        checked = isNotificationSwitchOn,
                        onCheckedChange = { isNotificationSwitchOn = it },
                        colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFF45624E))
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
        item {
            Box(
                modifier = Modifier
                    .shadow(elevation = 4.dp, RectangleShape)
                    .padding(16.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Text("Hidupkan Lokasi", fontSize = 18.sp)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Switch(
                        checked = isLocationSwitchOn,
                        onCheckedChange = { isLocationSwitchOn = it },
                        colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFF45624E))
                    )
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
        }
        item {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Keamanan", fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
        item {
            Box(
                modifier = Modifier
                    .shadow(elevation = 4.dp, RectangleShape)
                    .padding(16.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Text("Ubah kata sandi", fontSize = 18.sp)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(id = R.drawable.ic_arrow_right),
                        contentDescription = "Ubah Sandi",
                        modifier = Modifier.clickable { /* Handle edit click */ }
                    )
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}