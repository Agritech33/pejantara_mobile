package com.example.pejantara

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController) {
    val nama = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val konfirmasiPassword = remember { mutableStateOf("") }
    val noHandphone = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize() .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = "Daftar", fontSize = 28.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.padding(10.dp))

        Text("Segera daftarkan diri anda ke aplikasi")

        Spacer(modifier = Modifier.padding(10.dp))

        Text("Nama Anda")

        Spacer(modifier = Modifier.padding(5.dp))

        TextField(
            value = nama.value,
            onValueChange = { newText -> nama.value = newText },
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(0xFFE5E5E5)
            )
        )

        Spacer(modifier = Modifier.padding(5.dp))

        Text("Email")
        
        Spacer(modifier = Modifier.padding(5.dp))

        TextField(
            value = email.value,
            onValueChange = { newText -> email.value = newText },
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(0xFFE5E5E5)
            )
        )

        Spacer(modifier = Modifier.padding(5.dp))

        Text("Password")

        Spacer(modifier = Modifier.padding(5.dp))

        TextField(
            value = password.value,
            onValueChange = { newText -> password.value = newText },
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(0xFFE5E5E5)
            ),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.padding(5.dp))

        Text("Konfirmasi Password")

        Spacer(modifier = Modifier.padding(5.dp))

        TextField(
            value = konfirmasiPassword.value,
            onValueChange = { newText -> konfirmasiPassword.value = newText },
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(0xFFE5E5E5)
            ),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.padding(5.dp))

        Text("No Handphone")

        Spacer(modifier = Modifier.padding(5.dp))

        TextField(
            value = noHandphone.value,
            onValueChange = { newText -> noHandphone.value = newText },
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(0xFFE5E5E5)
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.padding(10.dp))

        Button(
            onClick = { /* TODO: Handle login click */ },
            colors = ButtonDefaults.buttonColors(Color(0xFF273526)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Daftar", color = Color.White)
        }

        Spacer(modifier = Modifier.padding(10.dp))

        Text(text = "Atau",
            modifier = Modifier.align(CenterHorizontally), fontSize = 12.sp)

        Spacer(modifier = Modifier.padding(10.dp))

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Image(painter = painterResource(id = R.drawable.google),
                contentDescription = "Google",
                modifier = Modifier
                    .size(60.dp)
                    .clickable {
                        /* TODO: Handle Google login click */
                    }
            )

            Image(painter = painterResource(id = R.drawable.facebook),
                contentDescription = "Facebook",
                modifier = Modifier
                    .size(60.dp)
                    .clickable {
                        /* TODO: Handle Facebook login click */
                    }
            )
        }

    }
}