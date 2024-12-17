package com.agritech.pejantaraapp.ui.screen.register

import android.widget.Toast
import androidx.compose.material3.TopAppBar

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.agritech.pejantaraapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerifikasiEmailScreen(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel(),
    email: String?
) {
    val kode1 = remember { mutableStateOf("") }
    val kode2 = remember { mutableStateOf("") }
    val kode3 = remember { mutableStateOf("") }
    val kode4 = remember { mutableStateOf("") }
    val isLoading = remember { mutableStateOf(false) }
    val context = LocalContext.current

    fun handleNextFocus(current: MutableState<String>, next: MutableState<String>) {
        if (current.value.length == 1) {
            next.value = ""
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 15.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = CenterHorizontally
    ) {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFF45624E)
            ),
            title = {
                Text("Verifikasi Email", color = Color.White)
            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }
        )

        Spacer(modifier = Modifier.padding(16.dp))

        Text(text = "Silahkan cek email Anda! Kami mengirimkan")
        Text(
            text = "kode verifikasi registrasi.",
            modifier = Modifier.padding(top = 4.dp)
        )

        Spacer(modifier = Modifier.padding(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TextField(
                value = kode1.value,
                onValueChange = { newText -> kode1.value = newText },
                modifier = Modifier.width(50.dp),
                colors = TextFieldDefaults.textFieldColors(containerColor = Color(0xFFE5E5E5)),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            TextField(
                value = kode2.value,
                onValueChange = { newText -> kode2.value = newText },
                modifier = Modifier.width(50.dp),
                colors = TextFieldDefaults.textFieldColors(containerColor = Color(0xFFE5E5E5)),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            TextField(
                value = kode3.value,
                onValueChange = { newText -> kode3.value = newText },
                modifier = Modifier.width(50.dp),
                colors = TextFieldDefaults.textFieldColors(containerColor = Color(0xFFE5E5E5)),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            TextField(
                value = kode4.value,
                onValueChange = { newText -> kode4.value = newText },
                modifier = Modifier.width(50.dp),
                colors = TextFieldDefaults.textFieldColors(containerColor = Color(0xFFE5E5E5)),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }

        Spacer(modifier = Modifier.padding(10.dp))

        Text(
            text = "Belum menerima kode? Kirim ulang.",
            modifier = Modifier
                .align(CenterHorizontally)
                .clickable {
                    isLoading.value = true
                    viewModel.resendVerificationEmail(
                        onSuccess = {
                            isLoading.value = false
                            Toast.makeText(context, "Kode verifikasi dikirim ulang!", Toast.LENGTH_SHORT).show()
                        },
                        onFailure = { error ->
                            isLoading.value = false
                            Toast.makeText(context, "Error: $error", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
        )

        Spacer(modifier = Modifier.padding(16.dp))

        Button(
            onClick = {
                val kodeVerifikasi = kode1.value + kode2.value + kode3.value + kode4.value

                if (kodeVerifikasi.length == 4) {
                    isLoading.value = true
                    viewModel.verifyEmailCode(
                        enteredCode = kodeVerifikasi,
                        onSuccess = {
                            isLoading.value = false
                            navController.navigate("home") { popUpTo(0) }
                        },
                        onFailure = { error ->
                            isLoading.value = false
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                        }
                    )
                } else {
                    Toast.makeText(context, "Masukkan kode verifikasi lengkap!", Toast.LENGTH_SHORT).show()
                }
            },
            colors = ButtonDefaults.buttonColors(Color(0xFF273526)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Konfirmasi", color = Color.White)
        }

        if (isLoading.value) {
            Spacer(modifier = Modifier.padding(10.dp))
            CircularProgressIndicator(color = Color(0xFF45624E))
        }
    }
}


