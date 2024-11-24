package com.example.pejantara.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pejantara.AturPasswordBaruScreen
import com.example.pejantara.HomeScreen
import com.example.pejantara.KeteranganScreen
import com.example.pejantara.LoginScreen
import com.example.pejantara.LupaPasswordScreen
import com.example.pejantara.ProfileScreen
import com.example.pejantara.RegisterScreen
import com.example.pejantara.UbahPassword
import com.example.pejantara.VerifikasiEmailScreen

@Composable
fun NavigationGraph(navController: NavHostController){
    NavHost(navController = navController, startDestination = "login") {
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