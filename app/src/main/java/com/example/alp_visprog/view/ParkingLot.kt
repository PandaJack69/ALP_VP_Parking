package com.example.alp_visprog.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.alp_visprog.R

@Composable
fun ParkingLot(
    navController: NavController,
) {
    var selectedFloor by remember { mutableStateOf(2) } // Lantai yang dipilih
    var selectedSlot by remember { mutableStateOf(-1) } // Slot parkir yang dipilih

    val parkingLotNames = remember(selectedFloor) {
        (1..36).map { slot -> "P$selectedFloor-$slot" }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            // Header
            Row(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        Icons.Filled.KeyboardArrowLeft,
                        contentDescription = "Back",
                        modifier = Modifier.size(35.dp),
                        tint = Color(0xFFFFA000)
                    )
                }
                Text(
                    text = "Pick Parking Spot",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Tabs for Floor Selection
            LazyRow(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                items((2..5).toList()) { floor ->
                    Box(
                        modifier = Modifier
                            .border(
                                BorderStroke(0.5.dp, if (selectedFloor == floor) Color.Transparent else Color(0xFFFFA000)),
                                shape = RoundedCornerShape(30.dp)
                            )
                            .background(
                                if (selectedFloor == floor) Color(0xFFFFA000) else Color.Transparent,
                                shape = RoundedCornerShape(30.dp)
                            )
                            .padding(10.dp)
                            .width(80.dp)
                            .clickable { selectedFloor = floor },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${floor}nd Floor",
                            color = if (selectedFloor == floor) Color.White else Color(0xFFFFA000)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Parking Slots
            val filteredParkingLotNames = (1..36).map { slot -> "P$selectedFloor-$slot" }
            ParkingSlots(
                parkingLotNames = filteredParkingLotNames,
                selectedSlot = selectedSlot,
                onSlotSelected = { selectedSlot = it }
            )
        }

        // Footer Button
        Box(
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxWidth()
                .size(100.dp)
                .align(Alignment.BottomCenter)
        ) {
            TextButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp, bottom = 40.dp)
                    .background(
                        color = if (selectedSlot != -1) Color(0xFFFFA000) else Color.Gray, // Change background based on enabled state
                        shape = RoundedCornerShape(20.dp)
                    ),
                onClick = {
                    if (selectedSlot != -1) {
                        val selectedSlotLabel = parkingLotNames[selectedSlot]
                        // Navigate to ReservationView with the selected slot label
                        navController.navigate("ReservationView/$selectedSlotLabel")
                    }
                },
                enabled = selectedSlot != -1 // Disable the button if no slot is selected
            ) {
                Text(
                    text = "Continue",
                    color = if (selectedSlot != -1) Color.White else Color.LightGray // LightGray text when disabled
                )
            }
        }
    }
}

@Composable
fun ParkingSlots(
    parkingLotNames: List<String>,
    selectedSlot: Int,
    onSlotSelected: (Int) -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left Column
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(90.dp)
                        .height(200.dp)
                        .padding(vertical = 10.dp)
                ){
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .align(Alignment.Center),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Text(
                            text = "Entry"
                        )
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 45.dp),
                            color = Color.Black
                        )
                        Text(
                            text = "Previous\nFloor"
                        )
                    }
                }
                parkingLotNames.take(18).forEachIndexed { index, name ->
                    ParkingSlotWithLabel(
                        label = name,
                        isSelected = selectedSlot == index,
                        isOccupied = false,  // Ensure it's empty by default
                        onClick = { onSlotSelected(index) }
                    )
                }
                Box(
                    modifier = Modifier
                        .width(90.dp)
                        .height(200.dp)
                        .padding(vertical = 10.dp)
                ){
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .align(Alignment.Center),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Text(
                            text = "Exit"
                        )
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 45.dp),
                            color = Color.Black
                        )
                        Text(
                            text = "Next\nFloor"
                        )
                    }
                }
            }

            // Right Column
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.End
            ) {
                parkingLotNames.drop(18).take(15).forEachIndexed { index, name ->
                    ParkingSlotWithLabel(
                        label = name,
                        isSelected = selectedSlot == index + 18,
                        isOccupied = false,  // Ensure it's empty by default
                        onClick = { onSlotSelected(index + 18) }
                    )
                }
                Column {
                    Box(
                        modifier = Modifier
                            .padding(10.dp)
                            .padding(bottom = 10.dp)
                            .align(Alignment.CenterHorizontally)
                    ){
                        Text(
                            text = "Entrance",
                            color = Color.Gray
                        )
                    }

                    Box(
                        modifier = Modifier
                            .height(360.dp)
                            .width(100.dp)
                            .background(color = Color.LightGray)
                            .align(Alignment.End)
                    )
                }
                parkingLotNames.drop(33).take(3).forEachIndexed { index, name ->
                    ParkingSlotWithLabel(
                        label = name,
                        isSelected = selectedSlot == index + 24,
                        isOccupied = false,  // Ensure it's empty by default
                        onClick = { onSlotSelected(index + 24) }
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .background(color = Color.Blue)
                .size(200.dp)
        )
    }
}

@Composable
fun ParkingSlotWithLabel(
    label: String,
    isSelected: Boolean,
    isOccupied: Boolean, // Control the empty/occupied state
    onClick: () -> Unit
) {
    val imageResources = listOf(
        R.drawable.blue_car,
        R.drawable.black_car,
        R.drawable.red_car,
        R.drawable.silver_car
    )
    val randomImageResource = if (isOccupied) imageResources.random() else null

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(100.dp, 60.dp)
                .clickable(enabled = !isOccupied, onClick = onClick) // Slot is clickable if not occupied
                .then(
                    if (isOccupied && randomImageResource != null) {
                        Modifier
                    } else {
                        Modifier.border(1.dp, Color(0xFFFFA000), shape = RoundedCornerShape(8.dp)) // Border for empty slots
                    }
                )
                .background(
                    color = if (isOccupied) Color.Transparent
                    else if (isSelected) Color(0xFFFFA000)
                    else Color(0xFFFFEDCF), // Yellow when selected, light yellow otherwise
                    shape = RoundedCornerShape(8.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            // If the slot is occupied and has a random car image, show the car
            if (isOccupied && randomImageResource != null) {
                Image(
                    painter = painterResource(id = randomImageResource),
                    contentDescription = "Occupied Slot",
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                // If not occupied, show the slot label (empty or selected)
                Text(
                    text = when {
                        isSelected -> "Selected"
                        isOccupied -> "Occupied"
                        else -> label // Empty slot label
                    },
                    fontSize = 12.sp,
                    color = when {
                        isOccupied || isSelected -> Color.White
                        else -> Color(0xFFFFA000) // Color for empty slots
                    }
                )
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ParkingLotView() {
    ParkingLot(navController = rememberNavController())
}