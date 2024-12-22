package com.agritech.pejantaraapp.ui.screen.register

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun VerifikasiEmailScreen(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel(),
    email: String?
) {
    val kodeInputs = List(4) { remember { mutableStateOf("") } }
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    val isLoading = remember { mutableStateOf(false) }
    val context = LocalContext.current

    fun handleNextFocus(index: Int, value: String) {
        if (value.length == 1 && index < kodeInputs.size - 1) {
            kodeInputs[index + 1].value = ""
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = CenterHorizontally
    ) {
        Text("Masukkan kode verifikasi 4 angka yang dikirimkan ke email Anda.")

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            kodeInputs.forEachIndexed { index, kodeInput ->
                TextField(
                    value = kodeInput.value,
                    onValueChange = { newText ->
                        if (newText.length <= 1) {
                            kodeInput.value = newText
                            handleNextFocus(index, newText)
                        }
                    },
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp),
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.Center),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val kodeVerifikasi = kodeInputs.joinToString("") { it.value }
                if (kodeVerifikasi.length == 4) {
                    isLoading.value = true
                    viewModel.verifyEmailCode(
                        userId = userId,
                        enteredCode = kodeVerifikasi,
                        onSuccess = {
                            isLoading.value = false
                            navController.navigate("home") {
                                popUpTo(0) { inclusive = true }
                            }
                        },
                        onFailure = { error ->
                            isLoading.value = false
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                        }
                    )
                } else {
                    Toast.makeText(context, "Masukkan kode lengkap!", Toast.LENGTH_SHORT).show()
                }
            },
            enabled = !isLoading.value
        ) {
            Text("Verifikasi")
        }

        if (isLoading.value) {
            CircularProgressIndicator()
        }
    }
}



