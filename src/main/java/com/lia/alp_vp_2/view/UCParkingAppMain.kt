package com.lia.alp_vp_2.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lia.alp_vp_2.enum.PagesEnum
import com.lia.alp_vp_2.viewModel.AuthenticationViewModel
import com.lia.alp_vp_2.viewModel.ReservationViewModel
import com.lia.alp_vp_2.viewmodels.ProfileViewModel

@Composable
fun UCParkingAppMain(
    navController: NavHostController = rememberNavController(),
    profileViewModel: ProfileViewModel = viewModel(),
    reservationViewModel: ReservationViewModel = viewModel(),
    authenticationViewModel: AuthenticationViewModel = viewModel()
) {
    val localContext = LocalContext.current

    NavHost(navController = navController, startDestination = PagesEnum.Login.name) {
        composable(route = PagesEnum.Login.name) {
            LoginView(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                authenticationViewModel = authenticationViewModel,
                navController = navController
            )
        }

        composable(route = PagesEnum.SignUp.name) {
            SignUpView(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                authenticationViewModel = authenticationViewModel,
                navController = navController
            )
        }

//        composable(route = PagesEnum.Profile.name) {
//            ProfileView(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(16.dp),
//                profileViewModel = profileViewModel,
//                navController = navController
//            )
//        }
//
//        composable(route = PagesEnum.Reservation.name) {
//            ReservationView(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(16.dp),
//                reservationViewModel = reservationViewModel,
//                navController = navController
//            )
//        }
//
//        composable(route = PagesEnum.ParkingLot.name) {
//            ParkingLotView(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(16.dp),
//                navController = navController
//            )
//        }
//
//        composable(route = PagesEnum.Report.name) {
//            ReportView(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(16.dp),
//                navController = navController
//            )
//        }
    }
}
