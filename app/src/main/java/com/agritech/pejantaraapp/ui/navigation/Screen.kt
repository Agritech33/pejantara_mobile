package com.agritech.pejantaraapp.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Home : Screen("home")
    object History : Screen("history")
    object Scan : Screen("scan")
    object ScanHistory : Screen("scan_history")
    object Lapor : Screen("lapor")
    object Profile : Screen("profile")

    object Edukasi : Screen("edukasi")

    object Result : Screen("result")

    object BankSampah : Screen("bank_sampah")

    object Login : Screen("login")

}