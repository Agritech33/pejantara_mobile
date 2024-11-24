package com.example.pejantara

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "home") {
                composable("login") {
                    LoginScreen(navController)
                }
                composable("register") {
                    RegisterScreen(navController)
                }
                composable("home") {
                    HomeScreen(navController)
                }
                composable("lupapassword") {
                    LupaPasswordScreen(navController)
                }
                composable("verifikasi") {
                    VerifikasiEmailScreen(navController)
                }
                composable("passwordbaru") {
                    AturPasswordBaruScreen(navController)
                }
                composable("profil") {
                    ProfileScreen(navController)
                }
                composable("keterangan") {
                    KeteranganScreen(navController)
                }
                composable("ubahpassword") {
                    UbahPassword(navController)
                }
            }
        }
    }
}

