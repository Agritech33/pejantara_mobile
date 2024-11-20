package com.example.pejantara

import android.media.Image
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    Column (
        modifier = Modifier
            .fillMaxSize()
    ) {
        ProfileSection()
        SearchBar()
        Spacer(modifier = Modifier.padding(10.dp))
        Text(text = "Layanan", modifier = Modifier
            .padding(start = 16.dp), fontSize = 20.sp, fontWeight = FontWeight.Bold)
        ServiceButton()
        Spacer(modifier = Modifier.padding(16.dp))
    }
}

@Composable
fun ProfileSection() {
    Row(modifier = Modifier
        .fillMaxWidth()
        .background(Color(0xFF45624E)),
        verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = R.drawable.profile_pic),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .padding(start = 20.dp, top = 30.dp)
                .size(48.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(8.dp))

        Column (
            modifier = Modifier
                .padding(top = 30.dp)
        ) {
            Text(text = "Asep Syaifullah", color = Color.White,
                fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = "5000", color = Color.White, fontSize = 14.sp)
        }
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
    Row (modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(iconId = R.drawable.edukasi, label = "Edukasi")
        Image(iconId = R.drawable.statistik, label = "Statistik")
        Image(iconId = R.drawable.cs, label = "CS")
        Image(iconId = R.drawable.location, label = "Location")
    }
}

@Composable
fun Image(iconId: Int, label: String) {
    Column (horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = iconId),
            contentDescription = label,
            modifier = Modifier
                .size(48.dp)
                .background(Color(0xFF45624E))
                .clip(CircleShape)
                .padding(8.dp)
                .clickable { /* Handle click event */   },
            colorFilter = ColorFilter.tint(Color.White)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = label, fontSize = 12.sp)
    }
}

@Composable
fun EducationPage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row {

        }
    }
}