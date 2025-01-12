package com.lia.alp_vp_2.route

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.lia.alp_vp_2.view.*
import com.lia.alp_vp_2.enum.PagesEnum
import com.lia.alp_vp_2.viewModel.*
import com.lia.alp_vp_2.viewmodels.ProfileViewModel

@Composable
fun AppRouting(
    navController: NavHostController = rememberNavController(),
    homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory),
    authenticationViewModel: AuthenticationViewModel = viewModel(factory = AuthenticationViewModel.Factory),
    profileViewModel: ProfileViewModel = viewModel(),
    penaltyViewModel: PenaltyViewModel = viewModel(),
    reportViewModel: ReportViewModel = viewModel(),
    reservationViewModel: ReservationViewModel = viewModel()
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
                navController = navController
            )
        }

        composable(route = PagesEnum.Register.name) {
            RegisterView(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                authenticationViewModel = authenticationViewModel,
                navController = navController
            )
        }

        composable(route = PagesEnum.Home.name) {
            HomeView(
                navController = navController,
                homeViewModel = homeViewModel
            )
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

        composable(route = PagesEnum.Reservation.name) {
            ReservationView(
                navController = navController,
                viewModel = reservationViewModel
            )
        }

        composable(route = PagesEnum.ParkingLot.name) {
            ParkingLotView(
                navController = navController
            )
        }
    }
}

