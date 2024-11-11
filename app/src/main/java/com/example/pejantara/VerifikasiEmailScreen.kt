package com.example.pejantara

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerifikasiEmailScreen(navController: NavController) {
    val kode1 = remember { mutableStateOf("") }
    val kode2 = remember { mutableStateOf("") }
    val kode3 = remember { mutableStateOf("") }
    val kode4 = remember { mutableStateOf("") }

    Column (
        modifier = Modifier.fillMaxSize()
            .padding(top = 25.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row (modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF45624E)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                    )
            }
            Text(text = "Verifikasi Email", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.padding(16.dp))

        Text(text = "Silahkan cek email Anda!!!Kami mengirimkan")
        Text(text = "kode verifikasi registrasi",
            modifier = Modifier.padding(top = 4.dp))

        Spacer(modifier = Modifier.padding(10.dp))

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TextField(
                value = kode1.value,
                onValueChange = {newText -> kode1.value = newText},
                modifier = Modifier
                    .width(50.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0xFFE5E5E5)
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            TextField(
                value = kode2.value,
                onValueChange = {newText -> kode2.value = newText},
                modifier = Modifier
                    .width(50.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0xFFE5E5E5)
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            TextField(
                value = kode3.value,
                onValueChange = {newText -> kode3.value = newText},
                modifier = Modifier
                    .width(50.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0xFFE5E5E5)
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            TextField(
                value = kode4.value,
                onValueChange = {newText -> kode4.value = newText},
                modifier = Modifier
                    .width(50.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0xFFE5E5E5)
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }

        Spacer(modifier = Modifier.padding(10.dp))

        Text(text = "Belum menerima kode Kirim ulang?", modifier = Modifier
            .align(CenterHorizontally)
            .clickable { /* TODO: Handle resend code click */
            }
        )

        Button(
            onClick = {navController.navigate("home")},
            colors = ButtonDefaults.buttonColors(Color(0xFF273526)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Konfirmasi", color = Color.White)
        }

    }
}