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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

import com.example.alp_visprog.view.LoginView
import com.example.alp_visprog.view.RegisterView
import com.example.alp_visprog.view.HomePage
import com.example.alp_visprog.view.QrScanner
import com.example.alp_visprog.view.QrGenerator
import com.example.alp_visprog.view.PenaltyView
import com.example.alp_visprog.view.PenaltyCard

import com.example.alp_visprog.enum.PagesEnum
import com.example.alp_visprog.model.PenaltyModelItem
import com.example.alp_visprog.view.ParkingLot
//import com.example.alp_visprog.view.ParkingLotView
import com.example.alp_visprog.view.PenaltyCard
import com.example.alp_visprog.view.ProfileView
import com.example.alp_visprog.view.ReportView
import com.example.alp_visprog.view.ReservationView
import com.example.alp_visprog.view.AdminView
import com.example.alp_visprog.view.ReservationDetailView
import com.example.alp_visprog.viewModel.AuthenticationViewModel
import com.example.alp_visprog.viewModel.HomeViewModel
import com.example.alp_visprog.viewModel.PenaltyViewModel
import com.example.alp_visprog.viewModel.ProfileViewModel
import com.example.alp_visprog.viewModel.QrScannerViewModel
import com.example.alp_visprog.viewModel.ReportViewModel
import com.example.alp_visprog.viewModel.ReservationViewModel
import okhttp3.internal.platform.android.ConscryptSocketAdapter.Companion.factory
import com.example.alp_visprog.view.CodeScanner

@Composable
fun AppRouting(
    navController: NavHostController = rememberNavController(),
    homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory),
    profileViewModel: ProfileViewModel = viewModel(factory = ProfileViewModel.Factory),
//    profileViewModel: ProfileViewModel = viewModel(factory = ProfileViewModel.Factory),
//    homeViewModel: HomeViewModel = viewModel(),
    authenticationViewModel: AuthenticationViewModel = viewModel(factory = AuthenticationViewModel.Factory),
//    profileViewModel: ProfileViewModel = viewModel(),
    penaltyViewModel: PenaltyViewModel = viewModel(factory = PenaltyViewModel.Factory),
    reportViewModel: ReportViewModel = viewModel(factory = ReportViewModel.Factory),
    reservationViewModel: ReservationViewModel = viewModel(factory = ReservationViewModel.Factory),
//    QrScannerViewModel: QrScannerViewModel = viewModel(factory = QrScannerViewModel.Factory)
) {
    val localContext = LocalContext.current
    val token = homeViewModel.token.collectAsState()


    NavHost(
        navController = navController,
        startDestination = if (token.value != "Unknown" && token.value != "") {
            PagesEnum.Home.name
        } else {
            PagesEnum.Login.name
        }
    ) {
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

//        composable(route = PagesEnum.Home.name) {
//            HomePage(
//                navController = navController,
//                homeViewModel = homeViewModel,
//                hasReservation = reservationViewModel.hasReservation.value,
//                reservedSpot = reservationViewModel.reservedSpot.value
//            )
//        }

        composable(route = PagesEnum.Home.name) {
            // Obtain the HomeViewModel using the factory

            val hasReservation by reservationViewModel.hasReservation.collectAsState()
            val reservedSpot by reservationViewModel.reservedSpot.collectAsState()

            HomePage(
                navController = navController,

                homeViewModel = homeViewModel,
                hasReservation = hasReservation,
                reservedSpot = reservedSpot
            )
        }

        composable(route = PagesEnum.Admin.name) {
            AdminView(navController = navController)
        }

        composable(route = PagesEnum.Profile.name) {
            ProfileView(
                navController = navController,
                profileViewModel = profileViewModel
            )
        }

        composable(route = PagesEnum.Penalty.name) {
            PenaltyView(
                navController = navController,
                viewModel = penaltyViewModel
            )
        }

        composable(route = "${PagesEnum.Penalty.name}/{penaltyId}") { backStackEntry ->
            val penaltyId = backStackEntry.arguments?.getString("penaltyId")?.toIntOrNull() ?: return@composable
            PenaltyCard(
                penalty = penaltyViewModel.penalties.value.find { it.id == penaltyId } ?: return@composable,
                onCardClick = {}
            )
        }

        composable(route = PagesEnum.Report.name) {
            ReportView(
                navController = navController,
                viewModel = reportViewModel
            )
        }

//        NavHost(navController = navController, startDestination = PagesEnum.ParkingLot.name) {
//            composable(route = PagesEnum.ParkingLot.name) {
//                ParkingLot(navController = navController)
//            }
//            composable(route = "ReservationView") {
//                ReservationView()
//            }
//        }

        composable(route = PagesEnum.ParkingLot.name) {
            ParkingLot(navController = navController)
        }

        composable(
            "ReservationView/{slotLabel}",
            arguments = listOf(navArgument("slotLabel") { type = NavType.StringType })
        ) { backStackEntry ->
            val slotLabel = backStackEntry.arguments?.getString("slotLabel")
            ReservationView(navController, slotLabel, reservationViewModel)
        }

        composable("ReservationDetailView/{userId}/{licensePlate}/{slotLabel}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            val licensePlate = backStackEntry.arguments?.getString("licensePlate") ?: ""
            val slotLabel = backStackEntry.arguments?.getString("slotLabel") ?: ""
            ReservationDetailView(navController, userId, licensePlate, slotLabel)
        }

        composable(route = PagesEnum.QRScanner.name) {
//            QrScanner()
//            QrScanner(onCodeScanned: (String, String, String, String))
        }



    }
}
