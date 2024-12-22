package com.agritech.pejantaraapp.ui.screen.profile

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun UbahPassword(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val passwordLama = remember { mutableStateOf("") }
    val passwordBaru = remember { mutableStateOf("") }
    val konfirmasiPassword = remember { mutableStateOf("") }
    val isError = remember { mutableStateOf(false) }
    val errorMessage = remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 25.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
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
            Text(
                text = "Ubah kata sandi",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.padding(16.dp))

        Text("Silahkan atur ulang kata sandi baru anda!!", fontSize = 18.sp)

        Spacer(modifier = Modifier.padding(16.dp))

        PasswordField(
            label = "Sandi Lama",
            value = passwordLama.value,
            onValueChange = { passwordLama.value = it }
        )

        Spacer(modifier = Modifier.padding(5.dp))

        PasswordField(
            label = "Sandi Baru",
            value = passwordBaru.value,
            onValueChange = { passwordBaru.value = it }
        )

        Spacer(modifier = Modifier.padding(5.dp))

        PasswordField(
            label = "Konfirmasi Sandi Baru",
            value = konfirmasiPassword.value,
            onValueChange = { konfirmasiPassword.value = it }
        )

        Spacer(modifier = Modifier.padding(10.dp))

        if (isError.value) {
            Text(
                text = errorMessage.value,
                color = Color.Red,
                modifier = Modifier.padding(16.dp)
            )
        }

        Button(
            onClick = {
                when {
                    passwordLama.value.isEmpty() || passwordBaru.value.isEmpty() || konfirmasiPassword.value.isEmpty() -> {
                        isError.value = true
                        errorMessage.value = "Semua field harus diisi."
                    }
                    passwordBaru.value != konfirmasiPassword.value -> {
                        isError.value = true
                        errorMessage.value = "Password baru dan konfirmasi tidak sama."
                    }
                    else -> {
                        isError.value = false
                        errorMessage.value = ""
                        viewModel.updatePassword(
                            oldPassword = passwordLama.value,
                            newPassword = passwordBaru.value,
                            onSuccess = {
                                Toast.makeText(context, "Password berhasil diubah!", Toast.LENGTH_SHORT).show()
                                navController.popBackStack()
                            },
                            onFailure = { error ->
                                isError.value = true
                                errorMessage.value = error
                            }
                        )
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(Color(0xFF273526)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Simpan", color = Color.White)
        }
    }
}

@Composable
fun PasswordField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            label,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 16.dp)
        )
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFFE5E5E5),
                focusedContainerColor = Color(0xFFE5E5E5)
            ),
            visualTransformation = PasswordVisualTransformation()
        )
    }
}


