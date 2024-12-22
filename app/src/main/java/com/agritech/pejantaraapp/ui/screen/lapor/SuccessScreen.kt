package com.agritech.pejantaraapp.ui.screen.lapor

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.agritech.pejantaraapp.R

@Composable
fun LaporSuccess(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(88.dp)
//                .clip(RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp))
//                .background(Color(0xff45624e))
//        )

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.wrapContentSize().align(Alignment.Center)
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_success),
                contentDescription = "Success Icon",
                modifier = Modifier.size(200.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Laporan Berhasil Dibuat",
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Laporan Anda akan diproses oleh Admin.",
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 16.sp)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(Color(0xff273526)),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .height(55.dp)
            ) {
                Text(
                    text = "Kembali Ke Beranda",
                    color = Color.White,
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}


    @Preview(widthDp = 412, heightDp = 917)
    @Composable
    private fun LaporSuccessPreview() {
        LaporSuccess(onClick = {})
    }
