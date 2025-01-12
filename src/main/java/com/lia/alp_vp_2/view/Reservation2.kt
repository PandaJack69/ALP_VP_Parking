package com.lia.alp_vp_2.view

import com.lia.alp_vp_2.ui.theme.ALP_VP_2Theme
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.lia.alp_vp_2.R

@Composable
fun Reservation2(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start
    ) {
        // Back Navigation Icon
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_back_ios_new_24),
                contentDescription = "Back",
                tint = Color(0xFFFFA155),
                modifier = Modifier
                    .size(24.dp)
                    .clickable { /* Handle Back Navigation */ }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Title
        Text(
            text = "Your Reservation",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Ticket Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            contentAlignment = Alignment.TopCenter
        ) {
            // Ticket Image Background
            Image(
                painter = painterResource(id = R.drawable.ticket), // Using your ticket image resource
                contentDescription = "Ticket Background",
                contentScale = ContentScale.Crop, // Ensures the image fills the entire area while maintaining aspect ratio
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight() // Ensures the image stretches to fit the entire screen
            )


            // Ticket Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(60.dp))

                // QR Code Placeholder
                Box(
                    modifier = Modifier
                        .size(180.dp)
                        .background(Color.White, shape = MaterialTheme.shapes.medium),
                    contentAlignment = Alignment.Center
                ) {
                    BasicText("QR Code")
                }

                Spacer(modifier = Modifier.height(120.dp))

                // Reservation Details
                Box(
                    modifier = Modifier
                        .fillMaxSize(), // Ensures the parent Box covers the entire screen
                    contentAlignment = Alignment.BottomStart // Aligns the content to the bottom-left
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp), // Adds padding for spacing from the edges
                        horizontalAlignment = Alignment.Start // Ensures the items inside are aligned to the start
                    ) {
                        ReservationDetail(label = "Name", value = "Igny Romy")
                        ReservationDetail(label = "License Plate", value = "L 120 MY")
                        ReservationDetail(label = "Hours", value = "09.00 - 15.00 WIB")
                        ReservationDetail(label = "Date", value = "December 3rd, 2024")
                        ReservationDetail(label = "Parking Spot", value = "P13-10A")
                    }
                }
            }
        }
    }
}

@Composable
fun ReservationDetail(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White // Text color updated to white
        )
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White // Text color updated to white
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun Reservation2Preview() {
    ALP_VP_2Theme {
        Reservation2(navController = rememberNavController())
    }
}
