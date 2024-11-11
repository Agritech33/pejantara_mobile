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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.semantics.Role.Companion.Checkbox
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = "Masuk", fontSize = 28.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.padding(8.dp))

        Text(text = "Selamat datang kembali!")

        Spacer(modifier = Modifier.padding(20.dp))

        Text(text = "Email / Username")

        Spacer(modifier = Modifier.padding(10.dp))

        TextField(
            value = email.value,
            onValueChange = { newText -> email.value = newText },
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(0xFFE5E5E5)
            )
        )

        Spacer(modifier = Modifier.padding(10.dp))

        Text(text = "Password")

        Spacer(modifier = Modifier.padding(10.dp))

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

        Spacer(modifier = Modifier.padding(10.dp))

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = false, onCheckedChange = {}
                )
                Text(text = "Ingat Saya")
            }
            Text(text = "Lupa Password?",
                modifier = Modifier.clickable {
                    navController.navigate("lupaPassword")
                }
            )
        }

        Spacer(modifier = Modifier.padding(10.dp))

        Button(
            onClick = {navController.navigate("verifikasi")},
            colors = ButtonDefaults.buttonColors(Color(0xFF273526)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Masuk", color = Color.White)
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

        Spacer(modifier = Modifier.padding(20.dp))

        Text(text = "Belum punya akun? Daftar", modifier = Modifier
            .align(CenterHorizontally)
            .clickable {
                navController.navigate("register")
            }
        )

    }
}


