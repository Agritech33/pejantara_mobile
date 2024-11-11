package com.example.pejantara

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AturPasswordBaruScreen (navController: NavController) {
    val password = remember { mutableStateOf("") }
    val konfirmasiPassword = remember { mutableStateOf("") }

    Column (
        modifier = Modifier.fillMaxSize()
            .padding(top = 25.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row (
            modifier = Modifier
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
            Text(text = "Atur Password Baru",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.padding(16.dp))

        Text("Silahkan atur ulang password baru anda")

        Spacer(modifier = Modifier.padding(16.dp))

        Text("Password",
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 16.dp))

        TextField(
            value = password.value,
            onValueChange = { newText -> password.value = newText },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(0xFFE5E5E5)
            ),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.padding(5.dp))

        Text("Konfirmasi Password",
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 16.dp))

        TextField(
            value = konfirmasiPassword.value,
            onValueChange = { newText -> konfirmasiPassword.value = newText },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(0xFFE5E5E5)
            ),
            visualTransformation = PasswordVisualTransformation()
        )

        Button(
            onClick = { navController.navigate("login") },
            colors = ButtonDefaults.buttonColors(Color(0xFF273526)),
            modifier = Modifier.fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Konfirmasi", color = Color.White)
        }

    }
}