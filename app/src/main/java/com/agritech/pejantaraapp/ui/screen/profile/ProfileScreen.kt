package com.agritech.pejantaraapp.ui.screen.profile

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.agritech.pejantaraapp.R
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val profileState by viewModel.profileState.collectAsState()

    var showNameDialog by remember { mutableStateOf(false) }
    var showPhoneDialog by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(profileState) {
        Log.d("ProfileScreen", "Profile State: $profileState")
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            viewModel.uploadProfileImage(
                context = context,
                uri = it,
                onSuccess = {
                    Toast.makeText(context, "Foto profil berhasil diubah", Toast.LENGTH_SHORT).show()
                },
                onFailure = { error ->
                    Toast.makeText(context, "Error: $error", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .heightIn(max = 600.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                contentAlignment = Alignment.Center
            ) {
                ProfilePicture(
                    photoUri = profileState.photoUri?.toUri(),
                    onEditClick = { launcher.launch("image/*") }
                )
                Spacer(modifier = Modifier.height(20.dp))
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                contentAlignment = Alignment.Center
            ) {
                JumlahUang(money = profileState.money ?: 0)
                Spacer(
                    modifier = Modifier.height(35.dp)

                )
            }
        }

        item {
            EditableRow(
                title = "Nama",
                value = profileState.name ?: "Nama tidak tersedia",
                onEditClick = { showNameDialog = true }
            )
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Email",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = profileState.email ?: "Email tidak tersedia",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray
                )
            }
        }

        item {
            EditableRow(
                title = "Telepon",
                value = profileState.phone ?: "Telepon tidak tersedia",
                onEditClick = { showPhoneDialog = true }
            )
        }

        item {
            SwitchRow(
                title = "Hidupkan Lokasi",
                description = "Gunakan layanan lokasi aplikasi",
                switchState = profileState.isLocationEnabled,
                onSwitchChange = { viewModel.updateLocationPreference(it) }
            )
        }

        item {
            ArrowRow(
                title = "Ubah kata sandi",
                onClick = { navController.navigate("change_password") }
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { showLogoutDialog = true },
                colors = ButtonDefaults.buttonColors(Color.Red),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Logout", color = Color.White)
            }
        }
    }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text(text = "Konfirmasi Logout") },
            text = { Text(text = "Apakah Anda yakin ingin keluar dari akun Anda?") },
            confirmButton = {
                TextButton(onClick = {
                    try {
                        FirebaseAuth.getInstance().signOut()
                        navController.navigate("login") {
                            popUpTo(0) { inclusive = true }
                        }
                    } catch (e: Exception) {
                        Toast.makeText(context, "Gagal logout: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                    }
                    showLogoutDialog = false
                }) {
                    Text("Ya", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Tidak")
                }
            }
        )
    }

    if (showNameDialog) {
        EditDialog(
            title = "Edit Nama",
            initialValue = profileState.name ?: "",
            onDismiss = { showNameDialog = false },
            onConfirm = { newName ->
                viewModel.updateName(newName)
                showNameDialog = false
            }
        )
    }

    if (showPhoneDialog) {
        EditDialog(
            title = "Edit Telepon",
            initialValue = profileState.phone ?: "",
            onDismiss = { showPhoneDialog = false },
            onConfirm = { newPhone ->
                viewModel.updatePhone(newPhone)
                showPhoneDialog = false
            }
        )
    }
}

@Composable
fun ProfilePicture(photoUri: Uri?, onEditClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.BottomEnd,
        modifier = Modifier
            .size(120.dp)
    ) {
        if (photoUri != null) {
            Image(
                painter = rememberAsyncImagePainter(photoUri),
                contentDescription = "Foto Profil",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        } else {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tambahkan Foto",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(Color(0xFF45624E))
                .clickable { onEditClick() }
                .padding(4.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_edit_camera),
                contentDescription = "Edit Foto",
                tint = Color.White
            )
        }
    }
}

@Composable
fun JumlahUang(money: Int) {
    val formattedMoney = "Rp. ${money * 4},00"
    Box(
        modifier = Modifier
            .shadow(elevation = 4.dp, RectangleShape)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.img_coin),
                    contentDescription = "Jumlah Uang",
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("$money", fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(formattedMoney, fontSize = 24.sp)
        }
    }
}

@Composable
fun EditableRow(title: String, value: String, onEditClick: () -> Unit) {
    Box(
        modifier = Modifier
            .shadow(elevation = 4.dp, RectangleShape)
            .padding(16.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Text(title, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(10.dp))
                Text(value, fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.ic_edit),
                contentDescription = "Edit",
                modifier = Modifier.clickable { onEditClick() }
            )
        }
    }
}

@Composable
fun SwitchRow(
    title: String,
    description: String? = null,
    switchState: Boolean,
    onSwitchChange: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .shadow(elevation = 4.dp, RectangleShape)
            .padding(16.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Text(title, fontSize = 18.sp)
                if (description != null) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(description, fontSize = 12.sp)
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = switchState,
                onCheckedChange = onSwitchChange,
                colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFF45624E))
            )
        }
    }
}

@Composable
fun ArrowRow(title: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .shadow(elevation = 4.dp, RectangleShape)
            .padding(16.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(title, fontSize = 18.sp)
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_right),
                contentDescription = "Arrow",
                modifier = Modifier.clickable { onClick() }
            )
        }
    }
}

@Composable
fun EditDialog(
    title: String,
    initialValue: String,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var text by remember { mutableStateOf(initialValue) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Masukkan $title") }
            )
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(text) }) {
                Text("Simpan")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Batal")
            }
        }
    )
}
