package com.example.alp_visprog.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.alp_visprog.viewModel.ReservationViewModel

@Composable
fun ReservationView(
    navController: NavController,
    slotLabel: String?,
//    id: Int,
//    spot: String,
//    navController: NavHostController,
    reservationViewModel: ReservationViewModel
){
    // Observe ViewModel state
    val userId by reservationViewModel.userId.collectAsState()
    val licensePlate by reservationViewModel.licensePlate.collectAsState()
    val reservedSpot by reservationViewModel.reservedSpot.collectAsState()

    var hourStart by remember { mutableStateOf("") }
    var minuteStart by remember { mutableStateOf("") }
    var hourEnd by remember { mutableStateOf("") }
    var minuteEnd by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize()
    ){
        // Header
        Row(
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Back",
                    modifier = Modifier.size(35.dp),
                    tint = Color(0xFFFFA000)
                )
            }
        }
        Text(
            text = "Your Reservation",
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            modifier = Modifier.padding(start = 20.dp, top = 20.dp)
        )
        Column(
            modifier = Modifier
                .padding(top = 30.dp, start = 20.dp)
        ){
            Text(
                text = "LICENSE PLATE",
                color = Color(0xFFFFA000),
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "L 120 MY",
                fontSize = 16.sp
            )
        }
        Column(
            modifier = Modifier
                .padding(top = 30.dp, start = 20.dp)
        ){
            Text(
                text = "PARKING SPOT",
                color = Color(0xFFFFA000),
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "$slotLabel",
                fontSize = 16.sp
            )
        }
        Column(
            modifier = Modifier
                .padding(top = 30.dp, start = 20.dp)
        ){
//            Text(
//                text = "TIME",
//                color = Color(0xFFFFA000),
//                fontSize = 14.sp
//            )

            Spacer(modifier = Modifier.height(5.dp))

            Row {
                // row untuk time start
//                Row {
//                    BasicTextField(
//                        value = hourStart,
//                        onValueChange = { input ->
//                            if (input.length <= 2 && (input.toIntOrNull() ?: 0) in 6..20) {
//                                hourStart = input
//                            }
//                        },
//                        modifier = Modifier
//                            .padding(8.dp)
//                            .width(40.dp)
//                            .height(35.dp)
//                            .background(Color(0xFFF0F0F0), RoundedCornerShape(10.dp))
//                            .padding(horizontal = 4.dp)
//                            .align(Alignment.CenterVertically),
//                        textStyle = LocalTextStyle.current.copy(fontSize = 16.sp, textAlign = TextAlign.Center),
//                        singleLine = true,
//                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
//                    )
//                    Text(
//                        text = ":",
//                        modifier = Modifier
//                            .align(Alignment.CenterVertically)
//                            .padding(horizontal = 4.dp)
//                    )
//                    BasicTextField(
//                        value = minuteStart,
//                        onValueChange = { input ->
//                            if (input.length <= 2 && (input.toIntOrNull() ?: 0) in 0..59) {
//                                minuteStart = input
//                            }
//                        },
//                        modifier = Modifier
//                            .padding(8.dp)
//                            .width(40.dp)
//                            .height(35.dp)
//                            .background(Color(0xFFF0F0F0), RoundedCornerShape(10.dp))
//                            .padding(horizontal = 4.dp)
//                            .align(Alignment.CenterVertically),
//                        textStyle = LocalTextStyle.current.copy(fontSize = 16.sp, textAlign = TextAlign.Center),
//                        singleLine = true,
//                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
//                    )
//                }

//                HorizontalDivider(
//                    modifier = Modifier
//                        .size(10.dp)
//                        .align(Alignment.CenterVertically)
//                        .padding(2.dp),
//                    color = Color.Black,
//                    thickness = 2.dp
//                )

                // row untuk time end
//                Row {
//                    BasicTextField(
//                        value = hourEnd,
//                        onValueChange = { input ->
//                            if (input.length <= 2 && (input.toIntOrNull() ?: 0) in 6..20) {
//                                hourEnd = input
//                            }
//                        },
//                        modifier = Modifier
//                            .padding(8.dp)
//                            .width(40.dp)
//                            .height(35.dp)
//                            .background(Color(0xFFF0F0F0), RoundedCornerShape(10.dp))
//                            .padding(horizontal = 4.dp)
//                            .align(Alignment.CenterVertically),
//                        textStyle = LocalTextStyle.current.copy(fontSize = 16.sp, textAlign = TextAlign.Center),
//                        singleLine = true,
//                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
//                    )
//                    Text(
//                        text = ":",
//                        modifier = Modifier
//                            .align(Alignment.CenterVertically)
//                            .padding(horizontal = 4.dp)
//                    )
//                    BasicTextField(
//                        value = minuteEnd,
//                        onValueChange = { input ->
//                            if (input.length <= 2 && (input.toIntOrNull() ?: 0) in 0..59) {
//                                minuteEnd = input
//                            }
//                        },
//                        modifier = Modifier
//                            .padding(8.dp)
//                            .width(40.dp)
//                            .height(35.dp)
//                            .background(Color(0xFFF0F0F0), RoundedCornerShape(10.dp))
//                            .padding(horizontal = 4.dp)
//                            .align(Alignment.CenterVertically),
//                        textStyle = LocalTextStyle.current.copy(fontSize = 16.sp, textAlign = TextAlign.Center),
//                        singleLine = true,
//                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
//                    )
//                }

            }
        }
        Column(
            modifier = Modifier
                .padding(top = 100.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Bottom
        ){
            TextButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, bottom = 50.dp)
                    .background(color = Color(0xFFFFA000), RoundedCornerShape(20.dp)),
                onClick = {
                    // Navigate to ReservationDetailView with the selected slot label
                    // Ensure userId and licensePlate are not null before navigating
//                    if (userId != null && licensePlate != null && reservedSpot != null) {
//                        navController.navigate("ReservationDetailView/${userId}/${licensePlate}/${slotLabel}")
//                    }
                    navController.navigate("ReservationDetailView/2/123/$slotLabel")
//                    navController.navigate("ReservationDetailView/1/123/$slotLabel")
                }
            ) {
                Text(
                    text = "Continue",
                    color = Color.White
                )
            }
        }

    }
}

@Composable
fun ReservationScreen(
    navController: NavController,
    userId: Int
) {
    val reservationViewModel: ReservationViewModel = viewModel()

    // Fetch the user's reservation when this screen is composed
    LaunchedEffect(userId) {
        reservationViewModel.fetchUserReservations(userId)
    }

//    ReservationView(navController = navController, reservationViewModel = reservationViewModel)
}