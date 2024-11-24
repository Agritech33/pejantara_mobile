package com.example.pejantara.navigation

sealed class Screen (val route: String) {
    data object Home : Screen("home")
    data object Login : Screen("login")
    data object Register : Screen("register")
    data object LupaPassword : Screen("lupapassword")
    data object VerifikasiEmail : Screen("verifikasi")
    data object AturPasswordBaru : Screen("passwordbaru")
    data object Profile : Screen("profil")
    data object Keterangan : Screen("keterangan")
    data object UbahPassword : Screen("ubahpassword")
}