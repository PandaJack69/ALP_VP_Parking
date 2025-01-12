package com.lia.alp_vp_2.view

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.lia.alp_vp_2.R
import com.lia.alp_vp_2.model.ReportModel
import com.lia.alp_vp_2.ui.theme.ALP_VP_2Theme
import com.lia.alp_vp_2.viewModel.ReportViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ReportView(navController: NavController, viewModel: ReportViewModel) {
    var licensePlate by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(30.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_back_ios_new_24),
                contentDescription = null,
                tint = Color(0xFFFFA155),
                modifier = Modifier
                    .size(24.dp)
                    .clickable { navController.popBackStack() }
            )
        }
        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = "Report",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))

        // Report Image Section
        Text(
            text = "Report Image:",
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .clickable { /* Handle Image Upload */ }
                .drawBehind {
                    val strokeWidth = 2.dp.toPx()
                    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                    drawRoundRect(
                        color = Color(0xFFFFA155),
                        size = size,
                        style = Stroke(
                            width = strokeWidth,
                            pathEffect = pathEffect,
                            miter = Stroke.DefaultMiter
                        ),
                        cornerRadius = CornerRadius(16.dp.toPx(), 16.dp.toPx())
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_add_photo_alternate_24),
                    contentDescription = null,
                    tint = Color(0xFFFFA155),
                    modifier = Modifier.size(30.dp)
                )
                Text(
                    text = "Insert an Image",
                    fontSize = 12.sp,
                    color = Color(0xFFFFA155)
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "*image must contain: a license plate and evidence of improper parking",
            fontSize = 12.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(16.dp))

        // License Plate Input
        ReportCustomTextField(
            label = "License Plate:",
            placeholder = "Write the license plate...",
            value = licensePlate,
            onValueChange = { licensePlate = it },
            keyboardOptions = KeyboardOptions.Default,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Report Description Input
        ReportCustomTextField(
            label = "Report Description:",
            placeholder = "Write your report description...",
            value = description,
            onValueChange = { description = it },
            keyboardOptions = KeyboardOptions.Default,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Submit Button
        Button(
            onClick = {
                viewModel.createReport("user-token", ReportModel(
                    title = "Improper Parking Report",
                    description = description,
                    userId = 1 // Replace with actual user ID
                ))
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA155))
        ) {
            Text(
                text = "Submit Report",
                color = Color.White,
                modifier = Modifier.padding(8.dp),
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun ReportCustomTextField(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(56.dp)
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    color = Color(0xFFFFA155)
                )
            },
            shape = RoundedCornerShape(25.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedBorderColor = Color(0xFFFFA155),
                unfocusedBorderColor = Color(0xFFFFA155)
            ),
            keyboardOptions = keyboardOptions,
            modifier = modifier
        )
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
fun ReportViewPreview() {
    ALP_VP_2Theme {
        ReportView(
            navController = rememberNavController(),
            viewModel = viewModel()
        )
    }
}

