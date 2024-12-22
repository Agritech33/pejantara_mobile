package com.agritech.pejantaraapp

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.agritech.pejantaraapp.ui.navigation.NavigationItem
import com.agritech.pejantaraapp.ui.navigation.Screen
import com.agritech.pejantaraapp.ui.screen.bank_sampah.BankSampahScreen
import com.agritech.pejantaraapp.ui.screen.detail_result.ErrorScreen
import com.agritech.pejantaraapp.ui.screen.detail_result.ResultTutorialScreen
import com.agritech.pejantaraapp.ui.screen.deteksi_sampah.ScanScreen
import com.agritech.pejantaraapp.ui.screen.edukasi.EdukasiScreen
import com.agritech.pejantaraapp.ui.screen.edukasi.EdukasiViewModel
import com.agritech.pejantaraapp.ui.screen.history.history_laporan.HistoryLaporanScreen
import com.agritech.pejantaraapp.ui.screen.history.history_laporan.HistoryLaporanViewModel
import com.agritech.pejantaraapp.ui.screen.history.history_scan.HistoryScanScreen
import com.agritech.pejantaraapp.ui.screen.history.history_scan.HistoryScanViewModel
import com.agritech.pejantaraapp.ui.screen.homepage.HomeScreen
import com.agritech.pejantaraapp.ui.screen.lapor.LaporScreen
import com.agritech.pejantaraapp.ui.screen.lapor.LaporSuccess
import com.agritech.pejantaraapp.ui.screen.lapor.LaporViewModel
import com.agritech.pejantaraapp.ui.screen.login.LoginScreen
import com.agritech.pejantaraapp.ui.screen.profile.ProfileScreen
import com.agritech.pejantaraapp.ui.screen.profile.ProfileViewModel
import com.agritech.pejantaraapp.ui.screen.profile.UbahPassword
import com.agritech.pejantaraapp.ui.screen.register.RegisterScreen
import com.agritech.pejantaraapp.ui.screen.register.VerifikasiEmailScreen
import com.google.android.gms.location.LocationServices
import java.io.File

@Composable
fun ResikelApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val startDestination = "splash"

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable("splash") {
            Log.d("NavHost", "Navigating to SplashScreen")
            SplashScreen(navController)
        }

        composable("login") {
            Log.d("NavHost", "Navigating to LoginScreen")
            LoginScreen(navController)
        }

        composable("register") {
            RegisterScreen(navController)
        }

        composable("verifikasi_email/{email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email")
            VerifikasiEmailScreen(navController = navController, email = email)
        }

        composable(Screen.Home.route) {
            Log.d("NavHost", "Navigating to HomeScreen")
            MainScreenWithScaffold(navController) {
                HomeScreen(navController)
            }
        }

        composable(Screen.History.route) {
            Log.d("NavHost", "Navigating to HistoryLaporanScreen")
            MainScreenWithScaffold(navController) {
                val viewModel: HistoryLaporanViewModel = hiltViewModel()
                HistoryLaporanScreen(viewModel = viewModel)
            }
        }

        composable(Screen.ScanHistory.route) {
            Log.d("NavHost", "Navigating to HistoryScanScreen")
            MainScreenWithScaffold(navController) {
                val viewModel: HistoryScanViewModel = hiltViewModel()
                HistoryScanScreen(viewModel = viewModel)
            }
        }

        composable(Screen.Scan.route) {
            Log.d("NavHost", "Navigating to ScanScreen")
            MainScreenWithScaffold(navController) {
                ScanScreen(navController)
            }
        }

        composable(Screen.Lapor.route) {
            Log.d("NavHost", "Navigating to LaporScreen")
            MainScreenWithScaffold(navController) {
                val laporViewModel: LaporViewModel = hiltViewModel()
                val profileViewModel: ProfileViewModel = hiltViewModel()
                val fusedLocationClient = LocationServices.getFusedLocationProviderClient(LocalContext.current)

                LaporScreen(
                    navController = navController,
                    laporViewModel = laporViewModel,
                    profileViewModel = profileViewModel,
                    fusedLocationClient = fusedLocationClient,
                    onSubmit = {
                        Log.d("LaporScreen", "Navigating to LaporSuccess")
                        navController.navigate("lapor_success")
                    }
                )
            }
        }

        composable("lapor_success") {
            LaporSuccess(
                onClick = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Lapor.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Profile.route) {
            Log.d("NavHost", "Navigating to ProfileScreen")
            MainScreenWithScaffold(navController) {
                ProfileScreen(navController)
            }
        }

        composable(Screen.Edukasi.route) {
            MainScreenWithScaffold(navController) {
                val viewModel: EdukasiViewModel = hiltViewModel()
                EdukasiScreen(viewModel = viewModel)
            }
        }

        composable("change_password") {
            MainScreenWithScaffold(navController) {
                UbahPassword(navController = navController)
            }
        }

        composable("result/{imagePath}", arguments = listOf(navArgument("imagePath") {
            type = NavType.StringType
        })) { backStackEntry ->
            val imagePath = backStackEntry.arguments?.getString("imagePath").orEmpty()
            MainScreenWithScaffold(navController) {
                if (imagePath.isBlank() || !File(imagePath).exists()) {
                    Log.e("NavHost", "Invalid imagePath: $imagePath")
                    ErrorScreen("Gambar tidak ditemukan. Silakan coba lagi.") {
                        navController.popBackStack()
                    }
                } else {
                    ResultTutorialScreen(
                        imagePath = imagePath,
                        onBack = { navController.popBackStack(Screen.Scan.route, false) }
                    )
                }
            }
        }


        composable(Screen.BankSampah.route) {
            val context = LocalContext.current
            val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

            MainScreenWithScaffold(navController) {
                BankSampahScreen(
                    fusedLocationProviderClient = fusedLocationProviderClient,
                    context = context
                )
            }
        }

    }
}

@Composable
fun MainScreenWithScaffold(
    navController: NavHostController,
    content: @Composable () -> Unit
) {
    val currentRoute = navController.currentBackStackEntry?.destination?.route.orEmpty()

    Scaffold(
        topBar = {
            if (currentRoute !in listOf("register", "verifikasi_email", Screen.Home.route, Screen.Scan.route, "change_password", "result/{imagePath}")) {
                TopBar(currentRoute)
            }
        },
        bottomBar = {
            if (currentRoute !in listOf("register", "verifikasi_email", Screen.Scan.route, "result/{imagePath}", Screen.BankSampah.route, "change_password")) {
                BottomBar(navController)
            }
        }
    ) { innerPadding ->
        Column (
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            content()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(currentRoute: String?) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF4B675C)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)),
        title = {
            Text(
                text = when (currentRoute) {
                    Screen.Lapor.route -> "Lapor"
                    Screen.History.route -> "Riwayat"
                    Screen.Profile.route -> "Profil"
                    Screen.Edukasi.route -> "Edukasi"
                    Screen.BankSampah.route -> "Map Bank Sampah"
                    else -> "Statistik Sampah"
                },
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)
            )
        }
    )
}

@Composable
fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(Alignment.CenterVertically)
            .padding(horizontal = 16.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 25.dp, topEnd = 25.dp,
                    bottomStart = 25.dp, bottomEnd = 25.dp
                )
            )
            .background(Color(0xFF4B675C))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            val navigationItems = listOf(
                NavigationItem(
                    title = "Home",
                    iconResId = R.drawable.icon_home,
                    screen = Screen.Home
                ),
                NavigationItem(
                    title = "Lapor",
                    iconResId = R.drawable.icon_report,
                    screen = Screen.Lapor
                ),
                NavigationItem(
                    title = "Scan",
                    iconResId = R.drawable.icon_scan,
                    screen = Screen.Scan
                ),
                NavigationItem(
                    title = "History",
                    iconResId = R.drawable.icon_history,
                    screen = Screen.History
                ),
                NavigationItem(
                    title = "Profile",
                    iconResId = R.drawable.profile,
                    screen = Screen.Profile,
                )
            )

            navigationItems.forEach { item ->
                IconButton(
                    onClick = {
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(id = item.iconResId),
                        contentDescription = item.title,
                        tint = if (currentRoute == item.screen.route) Color(0xFF00C853) else Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun Preview() {
    ResikelApp(navController = rememberNavController())
}