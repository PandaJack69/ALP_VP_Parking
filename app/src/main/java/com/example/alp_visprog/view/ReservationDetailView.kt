package com.example.alp_visprog.view

//For QR Generator
import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.alp_visprog.R
import com.example.alp_visprog.enum.PagesEnum
import com.example.alp_visprog.ui.theme.ALP_VisProgTheme
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter

@Composable
fun ReservationDetailView(
    navController: NavController,
    userId: String,
    licensePlate: String,
    slotLabel: String
) {
//    val qrCodeContent = "UserID: $userId\nLicense Plate: $licensePlate\nSlot: $slotLabel"
    val qrCodeContent = "$userId"+"$licensePlate"+"$slotLabel"+"enter"

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
                    .clickable { navController.popBackStack() }
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
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
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

                // QR Code Box
                Box(
                    modifier = Modifier
                        .size(180.dp)
                        .background(Color.White, shape = MaterialTheme.shapes.medium),
                    contentAlignment = Alignment.Center
                ) {
                    val qrCodeBitmap = generateQrCodeBitmap(qrCodeContent)
                    if (qrCodeBitmap != null) {
                        Image(
                            bitmap = qrCodeBitmap,
                            contentDescription = "Generated QR Code",
                            modifier = Modifier.size(160.dp) // Adjust size as needed
                        )
                    } else {
                        Text("Failed to generate QR Code")
                    }
                }

                Spacer(modifier = Modifier.height(120.dp))

                // Reservation Details
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        ReservationDetail(label = "Name_ID", value = userId)
                        ReservationDetail(label = "License Plate", value = licensePlate)
                        ReservationDetail(label = "Parking Spot", value = slotLabel)
                    }
                }

                TextButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, bottom = 50.dp)
                        .background(color = Color(0xFFFFA000), RoundedCornerShape(20.dp)),
                    onClick = {
                        navController.navigate(PagesEnum.Home.name)
//                        navController.navigate("ReservationDetailView/2/123/$slotLabel")
                    }
                ) {
                    Text(
                        text = "Check Out",
                        color = Color.White
                    )
                }

            }
        }
    }
}

fun generateQrCodeBitmap(content: String): ImageBitmap? {
    return try {
        val size = 512
        val hints = mapOf(EncodeHintType.MARGIN to 1)
        val bits = QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, size, size, hints)
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565).apply {
            for (x in 0 until size) {
                for (y in 0 until size) {
                    setPixel(x, y, if (bits[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
                }
            }
        }
        bitmap.asImageBitmap()
    } catch (e: Exception) {
        e.printStackTrace()
        null
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

//@Composable
//fun QrCodeGenerator() {
//    var inputText by remember { mutableStateOf("") }
//    var qrCodeContent by remember { mutableStateOf<String?>(null) }
//
//    fun getQrCodeBitmap(qrCodeContent: String): ImageBitmap {
//        val size = 512
//        val hints = hashMapOf<EncodeHintType, Int>().also {
//            it[EncodeHintType.MARGIN] = 1
//        }
//        val bits = QRCodeWriter().encode(qrCodeContent, BarcodeFormat.QR_CODE, size, size, hints)
//        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565).also {
//            for (x in 0 until size) {
//                for (y in 0 until size) {
//                    it.setPixel(x, y, if (bits[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
//                }
//            }
//        }
//        return bitmap.asImageBitmap()
//    }
//
//    Scaffold {
//        Column(
//            modifier = Modifier
//                .padding(it)
//                .fillMaxSize(),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(
//                text = "QR Code Generator",
//                fontSize = 18.sp,
//                fontWeight = FontWeight.Bold,
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            OutlinedTextField(
//                value = inputText,
//                onValueChange = { inputText = it },
//                label = { Text("Enter text to generate QR code") },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp)
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Button(onClick = { qrCodeContent = inputText }) {
//                Text("Generate QR Code")
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            qrCodeContent?.let { content ->
//                Text("Generated QR Code:", fontSize = 16.sp)
//                Spacer(modifier = Modifier.height(8.dp))
//                Image(
//                    bitmap = getQrCodeBitmap(content),
//                    contentDescription = "Generated QR Code"
//                )
//            }
//        }
//    }
//}

//@Preview(showSystemUi = true, showBackground = true)
//@Composable
//fun ReservationDetailViewPreview() {
//    ALP_VisProgTheme {
//        ReservationDetailView(navController = rememberNavController())
//    }
//}