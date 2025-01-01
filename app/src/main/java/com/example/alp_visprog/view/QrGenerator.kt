package com.example.alp_visprog.view

import android.graphics.Bitmap
import android.graphics.Color
import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter


@Composable
fun QrGenerator() {
    var inputText by remember { mutableStateOf("") }
    var qrCodeContent by remember { mutableStateOf<String?>(null) }

    fun getQrCodeBitmap(qrCodeContent: String): ImageBitmap {
        val size = 512
        val hints = hashMapOf<EncodeHintType, Int>().also {
            it[EncodeHintType.MARGIN] = 1
        }
        val bits = QRCodeWriter().encode(qrCodeContent, BarcodeFormat.QR_CODE, size, size, hints)
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565).also {
            for (x in 0 until size) {
                for (y in 0 until size) {
                    it.setPixel(x, y, if (bits[x, y]) Color.BLACK else Color.WHITE)
                }
            }
        }
        return bitmap.asImageBitmap()
    }

    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "QR Code Generator",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                label = { Text("Enter text to generate QR code") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { qrCodeContent = inputText }) {
                Text("Generate QR Code")
            }

            Spacer(modifier = Modifier.height(16.dp))

            qrCodeContent?.let { content ->
                Text("Generated QR Code:", fontSize = 16.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    bitmap = getQrCodeBitmap(content),
                    contentDescription = "Generated QR Code"
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun QrGeneratorPreview() {
    QrGenerator()
}
