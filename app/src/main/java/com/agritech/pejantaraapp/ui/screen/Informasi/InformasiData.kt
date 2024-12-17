//package com.agritech.pejantaraapp.ui.screen.Informasi
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyRow
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Map
//import androidx.compose.material3.Card
//import androidx.compose.material3.FloatingActionButton
//import androidx.compose.material3.Icon
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//
//
//@Composable
//    fun InformasiDataScreen() {
//        Scaffold(
//        ) { innerPadding ->
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(innerPadding)
//                    .padding(bottom = 56.dp)
//            ) {
//                WasteStatisticsGraph()
//                MapSection()
//                RecommendationSection()
//            }
//        }
//    }
//
//    @Composable
//    fun WasteStatisticsGraph() {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(
//                text = "Data Sampah TPS/TPA Batam",
//                fontWeight = FontWeight.Bold,
//                fontSize = 20.sp,
//                modifier = Modifier.padding(bottom = 8.dp)
//            )
//            Image(
//                painter = painterResource(id = R.drawable.vector3),
//                contentDescription = "Waste Statistics Graph",
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(200.dp)
//            )
//        }
//    }
//
//    @Composable
//    fun MapSection() {
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(200.dp)
//                .padding(16.dp)
//        ) {
//            Text(
//                text = "Temukan Bank Sampah Terdekat Disini",
//                fontWeight = FontWeight.Bold,
//                fontSize = 20.sp,
//                modifier = Modifier
//                    .padding(bottom = 8.dp)
//                    .align(alignment = Alignment.Center)
//            )
//            Image(
//                painter = painterResource(id = R.drawable.maps),
//                contentDescription = "Map",
//                modifier = Modifier.fillMaxSize()
//            )
//            FloatingActionButton(
//                onClick = {  },
//                modifier = Modifier
//                    .align(Alignment.BottomEnd)
//                    .padding(8.dp),
//                containerColor = Color.White
//            ) {
//                Icon(
//                    imageVector = Icons.Filled.Map,
//                    contentDescription = "Zoom Map",
//                    tint = Color(0xFF4B675C)
//                )
//            }
//        }
//    }
//
//    @Composable
//    fun RecommendationSection() {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text(
//                text = "TPS Batam",
//                fontWeight = FontWeight.Bold,
//                fontSize = 18.sp,
//                modifier = Modifier.padding(vertical = 8.dp)
//            )
//            LazyRow(
//                horizontalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
//                items(5) {
//                    TPSRecommendationItem()
//                }
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // TPA Recommendations
//            Text(
//                text = "TPA Batam",
//                fontWeight = FontWeight.Bold,
//                fontSize = 18.sp,
//                modifier = Modifier.padding(vertical = 8.dp)
//            )
//            LazyRow(
//                horizontalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
//                items(5) {
//                    TPARecommendationItem()
//                }
//            }
//        }
//    }
//
//    @Composable
//    fun TPSRecommendationItem() {
//        Card(
//            modifier = Modifier
//                .size(width = 120.dp, height = 80.dp),
//        ) {
//            // Placeholder image for TPS Recommendation
//            Image(
//                painter = painterResource(id = R.drawable.tps1), // Ensure this drawable exists
//                contentDescription = "TPS Recommendation",
//                modifier = Modifier.fillMaxSize()
//            )
//        }
//    }
//
//    @Composable
//    fun TPARecommendationItem() {
//        Card(
//            modifier = Modifier
//                .size(width = 120.dp, height = 80.dp),
//        ) {
//            // Placeholder image for TPA Recommendation
//            Image(
//                painter = painterResource(id = R.drawable.tpa1), // Ensure this drawable exists
//                contentDescription = "TPA Recommendation",
//                modifier = Modifier.fillMaxSize()
//            )
//        }
//    }
//
//
//    @Preview(widthDp = 412, heightDp = 997)
//    @Composable
//    fun InformasiPreview() {
//        InformasiDataScreen()
//    }
//
