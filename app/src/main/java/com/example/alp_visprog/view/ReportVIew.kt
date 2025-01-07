package com.example.alp_visprog.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.Stroke.Companion.DefaultMiter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.alp_visprog.R

@Composable
fun ReportView(navController: NavController) {
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
                    .clickable { /* Handle Back Navigation */ }
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
                        color = Color(0xFFFFA155), // Orange dotted outline
                        size = size,
                        style = Stroke(
                            width = strokeWidth,
                            pathEffect = pathEffect,
                            miter = DefaultMiter
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
        CustomTextField(
            label = "License Plate:",
            placeholder = "Write the license plate...",
            keyboardOptions = KeyboardOptions.Default,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Report Description Input
        CustomTextField(
            label = "Report Description:",
            placeholder = "Write your report description...",
            keyboardOptions = KeyboardOptions.Default,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Submit Button
        Button(
            onClick = { /* Handle Submit Report */ },
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
fun CustomTextField(
    label: String,
    placeholder: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    leadingIcon: (@Composable () -> Unit)? = null, // Add default value for leadingIcon
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(56.dp)
) {
    var text by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
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
    ReportView(
        navController = rememberNavController()
    )
}