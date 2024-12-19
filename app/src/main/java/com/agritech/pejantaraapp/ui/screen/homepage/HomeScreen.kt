package com.agritech.pejantaraapp.ui.screen.homepage

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.agritech.pejantaraapp.R
import com.agritech.pejantaraapp.ui.navigation.Screen
import com.agritech.pejantaraapp.ui.screen.profile.ProfileViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val profileState by viewModel.profileState.collectAsState()
    val context = LocalContext.current
    var locationPermissionGranted by remember { mutableStateOf(false) }

    LaunchedEffect(profileState) {
        Log.d("HomeScreen", "Profile state: $profileState")
    }

    LaunchedEffect(Unit) {
        requestLocationPermission(context) { granted ->
            locationPermissionGranted = granted
            if (!granted) {
                Toast.makeText(context, "Izin lokasi diperlukan untuk fitur ini.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    if (profileState.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (profileState.name == null || profileState.email == null) {
        Text(
            text = "Gagal memuat data pengguna.",
            color = Color.Gray,
            style = MaterialTheme.typography.bodyLarge
        )
    } else {
        // Konten utama HomeScreen
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                // Header dengan informasi pengguna
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF45624E))
                        .clip(RoundedCornerShape(bottomStart = 25.dp, bottomEnd = 25.dp))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = if (profileState.photoUri != null) {
                                rememberAsyncImagePainter(profileState.photoUri)
                            } else {
                                painterResource(id = R.drawable.profile) // Placeholder image
                            },
                            contentDescription = "Foto Profil",
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(Color.Gray),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        // Nama dan Coin
                        Column {
                            Text(
                                text = "Selamat Datang, ${profileState.name ?: "Pengguna"}!",
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painter = painterResource(id = R.drawable.img_coin),
                                    contentDescription = "Icon Coin",
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${profileState.money ?: 0}",
                                    color = Color.White,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                // Carousel
                Carousel(modifier = Modifier.height(250.dp))
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                // Layanan
                Text(
                    text = "Layanan",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                ServiceButton(navController = navController)
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                // Edukasi
                Text(
                    text = "Edukasi",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                EducationContent()
            }
        }
    }
}


fun requestLocationPermission(context: Context, onResult: (Boolean) -> Unit) {
    val permission = Manifest.permission.ACCESS_FINE_LOCATION
    val isGranted = ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED

    if (isGranted) {
        onResult(true)
    } else {
        if (context is Activity) {
            ActivityCompat.requestPermissions(
                context,
                arrayOf(permission),
                1
            )
        }
        onResult(false)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Carousel(modifier: Modifier = Modifier) {
    val images = listOf(
        R.drawable.carousel_1,
        R.drawable.carousel_2,
    ).takeIf { it.isNotEmpty() } ?: listOf(R.drawable.ic_add_photo)
    val pagerState = rememberPagerState(
        pageCount = { images.size }
    )
    if (images.isNotEmpty()) {
        LaunchedEffect(pagerState) {
            if (pagerState.pageCount > 1) {
                while (true) {
                    delay(2000)
                    val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
                    pagerState.scrollToPage(nextPage)
                }
            }
        }

    }

    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = modifier.wrapContentSize()) {
            HorizontalPager(
                state = pagerState,
                modifier = modifier
            ) { currentPage ->
                Card(
                    modifier = Modifier
                        .padding(26.dp)
                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Image(
                        painter = painterResource(id = images[currentPage]),
                        contentDescription = "Image carousel"
                    )
                }
            }
            IconButton(
                onClick = {
                    val nextPage = pagerState.currentPage + 1
                    if (nextPage < images.size) {
                        scope.launch {
                            pagerState.scrollToPage(nextPage)
                        }
                    }
                },
                modifier = Modifier
                    .padding(30.dp)
                    .size(48.dp)
                    .align(Alignment.CenterEnd)
                    .clip(CircleShape)
                    .background(Color(0x52373737))
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowRight,
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize(),
                    tint = Color.LightGray
                )
            }
            IconButton(
                onClick = {
                    val prevPage = pagerState.currentPage - 1
                    if (prevPage >= 0) {
                        scope.launch {
                            pagerState.scrollToPage(prevPage)
                        }
                    }
                },
                modifier = Modifier
                    .padding(30.dp)
                    .size(48.dp)
                    .align(Alignment.CenterStart)
                    .clip(CircleShape)
                    .background(Color(0x52373737))
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowLeft,
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize(),
                    tint = Color.LightGray
                )
            }
        }

        PageIndicator(
            pageCount = images.size,
            currentPage = pagerState.currentPage,
            modifier = modifier
        )
    }
}

@Composable
fun PageIndicator(pageCount: Int, currentPage: Int, modifier: Modifier) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        repeat(pageCount) {
            IndicatorDots(isSelected = it == currentPage, modifier = Modifier)
        }
    }
}

@Composable
fun IndicatorDots(isSelected: Boolean, modifier: Modifier) {
    val size = animateDpAsState(targetValue = if (isSelected) 12.dp else 10.dp, label = "")
    Box(
        modifier = modifier
            .padding(2.dp)
            .size(size.value)
            .clip(CircleShape)
            .background(if (isSelected) Color(0xff373737) else Color(0xA8373737))
    )
}

@Composable
fun ServiceButton(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(CircleShape),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ServiceIcon(iconId = R.drawable.edukasi, label = "Edukasi") {
            navController.navigate(Screen.Edukasi.route)
        }
        ServiceIcon(iconId = R.drawable.statistik, label = "Statistik") {
            navController.navigate(Screen.History.route)
        }
        ServiceIcon(iconId = R.drawable.cs, label = "CS") {
            navController.navigate(Screen.Scan.route)
        }
        ServiceIcon(iconId = R.drawable.location, label = "Location") {
            navController.navigate(Screen.BankSampah.route)
        }
    }
}

@Composable
fun ServiceIcon(iconId: Int, label: String, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = iconId),
            contentDescription = label,
            modifier = Modifier
                .size(68.dp)
                .clip(CircleShape)
                .padding(10.dp)
                .background(Color(0xFF45624E))
                .clickable { onClick() },
            colorFilter = ColorFilter.tint(Color.White)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = label, fontSize = 12.sp)
    }
}

@Composable
fun EducationContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(max = 600.dp) // Batas maksimum
            .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .shadow(elevation = 4.dp, RectangleShape)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row {
                Image(
                    painter = painterResource(id = R.drawable.homescreen_1),
                    contentDescription = "Edukasi",
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Pengenalan Jenis-Jenis Sampah")
            }
        }
        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .shadow(elevation = 4.dp, RectangleShape)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row {
                Image(
                    painter = painterResource(id = R.drawable.homescreen_2),
                    contentDescription = "Edukasi",
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Bahaya Plastik Bagi Lingkungan dan Kesehatan")
            }
        }
        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .shadow(elevation = 4.dp, RectangleShape)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row {
                Image(
                    painter = painterResource(id = R.drawable.homescreen_3),
                    contentDescription = "Edukasi",
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Cara Daur Ulang Sampah")
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
fun SearchBar(searchQuery: String, onSearchQueryChanged: (String) -> Unit) {
//    OutlinedTextField(
//        value = searchQuery,
//        onValueChange = onSearchQueryChanged,
//        placeholder = { Text("Cari...", color = Color.Gray) },
//        singleLine = true,
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 16.dp, vertical = 8.dp)
//            .clip(RoundedCornerShape(20.dp)),
//        colors = OutlinedTextFieldDefaults.colors(
//            unfocusedBorderColor = Color.White,
//            focusedBorderColor = Color.White,
//            cursorColor = Color.White
//        ),
//        textStyle = LocalTextStyle.current.copy(color = Color.Black)
//    )
}

@Preview(showBackground = true, widthDp = 412, heightDp = 917)
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    HomeScreen(navController = navController)
}
