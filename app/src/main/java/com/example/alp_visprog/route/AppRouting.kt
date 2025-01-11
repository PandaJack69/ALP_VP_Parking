package com.example.alp_visprog.route

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.alp_visprog.view.LoginView
import com.example.alp_visprog.view.RegisterView
import com.example.alp_visprog.view.HomeScreen
import com.example.alp_visprog.view.QrScanner
import com.example.alp_visprog.view.QrGenerator
import com.example.alp_visprog.view.PenaltyView
import com.example.alp_visprog.view.PenaltyCard

import com.example.alp_visprog.enum.PagesEnum
import com.example.alp_visprog.model.PenaltyModelItem
import com.example.alp_visprog.view.PenaltyCard
import com.example.alp_visprog.viewModel.AuthenticationViewModel
import com.example.alp_visprog.viewModel.HomeViewModel

//sealed class Screen(val route: String) {
//    object Login : Screen("login")
//    object Home : Screen("home")
//    object QRScanner : Screen("qr_scanner")
//    object QRGenerator : Screen("qr_generator")
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppRouting(
    navController: NavHostController = rememberNavController(),
    homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory),
//    todoListFormViewModel: TodoListFormViewModel = viewModel(factory = TodoListFormViewModel.Factory),
    authenticationViewModel: AuthenticationViewModel = viewModel(factory = AuthenticationViewModel.Factory),
//    todoDetailViewModel: TodoDetailViewModel = viewModel(factory = TodoDetailViewModel.Factory)
) {
//    val navController = rememberNavController()

    val localContext = LocalContext.current
    val token = homeViewModel.token.collectAsState()

    NavHost(navController = navController, startDestination = if(token.value != "Unknown" && token.value != "") {
        PagesEnum.Home.name
    } else {
        PagesEnum.Login.name
    }) {
        composable(route = PagesEnum.Login.name) {
            LoginView(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                authenticationViewModel = authenticationViewModel,
                navController = navController,
                context = localContext
            )
        }

        composable(route = PagesEnum.Register.name) {
            RegisterView(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                authenticationViewModel = authenticationViewModel,
                navController = navController,
                context = localContext
            )
        }

        composable (route = PagesEnum.Penalty.name) {
            PenaltyView(

            )
        }

        composable (route = PagesEnum.PenaltyCard.name) {
            PenaltyCard(penalty = PenaltyModelItem()) {

            }
        }

//        composable(route = PagesEnum.Home.name) {
//            HomeView(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(Color.White),
//                homeViewModel = homeViewModel,
//                navController = navController,
//                token = token.value,
//                todoDetailViewModel = todoDetailViewModel,
//                context = localContext
//            )
//        }

    }
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = MaterialTheme.colorScheme.primaryContainer,
//                    titleContentColor = MaterialTheme.colorScheme.primary,
//                ),
//                title = { Text(text = "QR App") }
//            )
//        }
//    ) { innerPadding ->
//        NavHost(
//            navController = navController,
//            startDestination = Screen.Login.route, // Set Login as the starting destination
//            modifier = Modifier.padding(innerPadding)
//        ) {
//            composable(route = Screen.Login.route) {
//                LoginView(
//                    navController = navController,
//                    onLoginSuccess = {
//                        // Navigate to Home after successful login
//                        navController.navigate(Screen.Home.route) {
//                            popUpTo(Screen.Login.route) { inclusive = true } // Remove Login from back stack
//                        }
//                    }
//                )
//            }
//            composable(route = Screen.Home.route) {
//                HomeScreen(navController = navController)
//            }
//            composable(route = Screen.QRScanner.route) {
//                QrScanner { result ->
//                    // Handle QR scanner result
//                }
//            }
//            composable(route = Screen.QRGenerator.route) {
//                QrGenerator()
//            }
//        }
//    }
}
