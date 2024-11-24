package com.example.pejantara

import androidx.compose.animation.core.animateDpAsState
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 30.dp)
    ) {
        TopAppBar(
            backgroundColor = Color(0xFF45624E),
            title = {
                Row {
                    Image(
                        painter = painterResource(id = R.drawable.profile_pic),
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(text = "Asep Syaifullah", color = Color.White)
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.img_coin),
                                contentDescription = "Jumlah Uang",
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("5.000", fontSize = 18.sp)
                        }
                    }
                }
            }
        )
        SearchBar()
        Spacer(modifier = Modifier.padding(10.dp))
        Carousel(modifier = Modifier.height(250.dp))
        Spacer(modifier = Modifier.padding(10.dp))
        Text(
            text = "Layanan",
            modifier = Modifier.padding(start = 16.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        ServiceButton()
        Spacer(modifier = Modifier.padding(10.dp))
        EducationContent(modifier = Modifier.weight(1f))
    }
}

@Composable
fun SearchBar() {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        label = {
            Text(text = "Cari...")
        },
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxWidth(),
        singleLine = true,
        textStyle = LocalTextStyle.current.copy(color = Color.Gray)
    )
}

@Composable
fun ServiceButton() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ServiceIcon(iconId = R.drawable.edukasi, label = "Edukasi")
        ServiceIcon(iconId = R.drawable.statistik, label = "Statistik")
        ServiceIcon(iconId = R.drawable.cs, label = "CS")
        ServiceIcon(iconId = R.drawable.location, label = "Location")
    }
}

@Composable
fun ServiceIcon(iconId: Int, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = iconId),
            contentDescription = label,
            modifier = Modifier
                .size(48.dp)
                .background(Color(0xFF45624E))
                .clip(CircleShape)
                .padding(8.dp)
                .clickable { /* Handle click event */ },
            colorFilter = ColorFilter.tint(Color.White)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = label, fontSize = 12.sp)
    }
}

@Composable
fun EducationContent(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        item {
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
                    Text("Pengenalan Jenis-Jenis Sampah")
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
        item {
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
                    Text("Bahaya Plastik Bagi Lingkungan dan Kesehatan")
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
        item {
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
                    Text("Cara Daur Ulang Sampah")
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun Carousel(modifier: Modifier = Modifier) {
    val images = listOf(
        R.drawable.carousel_1,
        R.drawable.carousel_2,
    )
    val pagerState = rememberPagerState(
        pageCount = { images.size }
    )
    LaunchedEffect(pagerState) {
        while (true) {
            delay(2000)
            val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
            pagerState.scrollToPage(nextPage)
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
                modifier = Modifier.wrapContentSize()
            ) { currentPage ->
                Card(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(26.dp),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Image(
                        painter = painterResource(id = images[currentPage]),
                        contentDescription = ""
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