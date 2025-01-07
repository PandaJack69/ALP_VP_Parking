package com.example.alp_visprog.route

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.alp_visprog.view.LoginView
import com.example.alp_visprog.view.HomeScreen
import com.example.alp_visprog.view.QrScanner
import com.example.alp_visprog.view.QrGenerator

//enum class QRCodeScreen {
//    Scanner,
//    Generator
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun AppRouting() {
//    val navController = rememberNavController()
//    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
//    val title = when (currentRoute) {
//        QRCodeScreen.Scanner.name -> "QR Scanner"
//        QRCodeScreen.Generator.name -> "QR Generator"
//        else -> "QR Code App"
//    }
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = MaterialTheme.colorScheme.primaryContainer,
//                    titleContentColor = MaterialTheme.colorScheme.primary
//                ),
//                title = { Text(title) }
//            )
//        }
//    ) { innerPadding ->
//        NavHost(
//            navController = navController,
//            startDestination = QRCodeScreen.Scanner.name,
//            modifier = Modifier.padding(innerPadding)
//        ) {
//            composable(route = QRCodeScreen.Scanner.name) {
//                QrScanner { result ->
//                    // Handle the scanned QR code result
//                }
//            }
//
//            composable(route = QRCodeScreen.Generator.name) {
//                QrGenerator()
//            }
//        }
//    }
//}

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Home : Screen("home")
    object QRScanner : Screen("qr_scanner")
    object QRGenerator : Screen("qr_generator")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppRouting() {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text(text = "QR App") }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = Screen.Home.route) {
                HomeScreen(navController = navController)
            }
            composable(route = Screen.QRScanner.route) {
                QrScanner { result ->
                    //Handle result
                }
            }
            composable(route = Screen.QRGenerator.route) {
                QrGenerator()
            }
        }
    }
}
