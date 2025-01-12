package com.lia.alp_vp_2.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lia.alp_vp_2.R

@Composable
fun ParkingLot() {
    var selectedFloor by remember { mutableStateOf(2) } // Lantai yang dipilih
    var selectedSlot by remember { mutableStateOf(-1) } // Slot parkir yang dipilih

    // Daftar nama untuk setiap kelompok slot parkir
    val parkingLotNames = (1..12).flatMap { group ->
        listOf("${group}A", "${group}B", "${group}C")
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
                IconButton(onClick = { /* Handle back navigation */ }) {
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
            ParkingSlots(
                parkingLotNames = parkingLotNames,
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
        ){
            TextButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp)
                    .background(color = Color(0xFFFFA000), RoundedCornerShape(20.dp)),
                onClick = { /* Action for continue button */ }
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
                parkingLotNames.chunked(6).take(5).forEach { group ->
                    group.subList(3, 6).forEach { name ->
                        val slotIndex = parkingLotNames.indexOf(name)
                        ParkingSlotWithLabel(
                            label = name,
                            isSelected = selectedSlot == slotIndex,
                            isOccupied = slotIndex % 6 == 0,
                            onClick = { onSlotSelected(slotIndex) }
                        )
                    }
                }
                parkingLotNames.chunked(6).take(1).forEach { group ->
                    group.subList(0, 3).forEach { name ->
                        val slotIndex = parkingLotNames.indexOf(name)
                        ParkingSlotWithLabel(
                            label = name,
                            isSelected = selectedSlot == slotIndex,
                            isOccupied = slotIndex % 6 == 0,
                            onClick = { onSlotSelected(slotIndex) }
                        )
                    }
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
                parkingLotNames.chunked(6).take(5).forEach { group ->
                    group.subList(0, 3).forEach { name ->
                        val slotIndex = parkingLotNames.indexOf(name)
                        ParkingSlotWithLabel(
                            label = name,
                            isSelected = selectedSlot == slotIndex,
                            isOccupied = slotIndex % 6 == 0,
                            onClick = { onSlotSelected(slotIndex) }
                        )
                    }
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
                parkingLotNames.chunked(6).take(1).forEach { group ->
                    group.subList(3, 6).forEach { name ->
                        val slotIndex = parkingLotNames.indexOf(name)
                        ParkingSlotWithLabel(
                            label = name,
                            isSelected = selectedSlot == slotIndex,
                            isOccupied = slotIndex % 6 == 0,
                            onClick = { onSlotSelected(slotIndex) }
                        )
                    }
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
    isOccupied: Boolean,
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
                .clickable(enabled = !isOccupied, onClick = onClick)
                .then(
                    if (isOccupied && randomImageResource != null) {
                        Modifier
                    } else {
                        Modifier.border(1.dp, Color(0xFFFFA000), shape = RoundedCornerShape(8.dp)) // Border ditambahkan
                    }
                )
                .background(
                    color = if (isOccupied) Color.Transparent
                    else if (isSelected) Color(0xFFFFA000)
                    else Color(0xFFFFEDCF),
                    shape = RoundedCornerShape(8.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isOccupied && randomImageResource != null) {
                Image(
                    painter = painterResource(id = randomImageResource),
                    contentDescription = "Occupied Slot",
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Text(
                    text = when {
                        isSelected -> "Selected"
                        isOccupied -> "Occupied"
                        else -> label
                    },
                    fontSize = 12.sp,
                    color = when {
                        isOccupied || isSelected -> Color.White
                        else -> Color(0xFFFFA000)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ParkingLotView() {
    ParkingLot()
}