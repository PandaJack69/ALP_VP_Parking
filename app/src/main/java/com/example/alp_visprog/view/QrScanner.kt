package com.example.alp_visprog.view

import android.Manifest
import android.content.pm.PackageManager
import android.util.Size
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
//import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.alp_visprog.QrCodeAnalyzer
import com.example.alp_visprog.viewModel.QrScannerViewModel

@Composable
fun QrScanner(onCodeScanned: (String, String, String, String) -> Unit) {

    val qrScannerViewModel: QrScannerViewModel = viewModel() // Get ViewModel instance

    var code by remember { mutableStateOf("") }
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    var hasCamPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        )
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted -> hasCamPermission = granted }
    )

    LaunchedEffect(key1 = true) {
        launcher.launch(Manifest.permission.CAMERA)
    }

    fun handleQrCodeTarget(
        qrCode: String,
        qrScannerViewModel: QrScannerViewModel
    ): String {
        val parts = qrCode.split("_")
        if (parts.size != 4) {
            throw IllegalArgumentException("Invalid QR code format. Expected format: userId_licensePlate_lotNumber_target")
        }

        val userId = parts[0] // Assuming userId is a String
        val licensePlate = parts[1]
        val lotNumber = parts[2] // Assuming lotNumber should be an Int
        val target = parts[3]

        return when (target) {
            "in" -> {
                // Change the target to "out" and return the updated QR code
                "${userId}_${licensePlate}_${lotNumber}_out"
            }
            "out" -> {
                // Convert lotNumber to Int, assuming it's the reservation ID
                try {
                    val reservationId = lotNumber.toInt() // Convert lotNumber to Int
                    qrScannerViewModel.updateReservationStatus(reservationId, "complete")
                    qrCode // Return the original QR code
                } catch (e: NumberFormatException) {
                    println("Error: Invalid lotNumber format")
                    qrCode // Return the original QR code in case of failure
                }
            }
            else -> {
                throw IllegalArgumentException("Invalid target value. Expected 'in' or 'out', got: $target")
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        if (hasCamPermission) {
            AndroidView(
                factory = { context ->
                    val previewView = PreviewView(context)
                    val preview = androidx.camera.core.Preview.Builder().build()
                    val selector = CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build()
                    preview.setSurfaceProvider(previewView.surfaceProvider)

                    val imageAnalysis = ImageAnalysis.Builder()
                        .setTargetResolution(Size(previewView.width, previewView.height))
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build()
                    imageAnalysis.setAnalyzer(
                        ContextCompat.getMainExecutor(context),
                        QrCodeAnalyzer { result ->
                            code = result

                            // Parse and process the QR code result
                            val parts = result.split("_")
                            if (parts.size == 4) {
                                val userId = parts[0]
                                val licensePlate = parts[1]
                                val lotNumber = parts[2]
                                val target = parts[3]
                                onCodeScanned(userId, licensePlate, lotNumber, target)

                                handleQrCodeTarget(code, qrScannerViewModel)
                            }
//                            onCodeScanned(result)
                        }
                    )
                    try {
                        cameraProviderFuture.get().bindToLifecycle(lifecycleOwner, selector, preview, imageAnalysis)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    previewView
                },
                modifier = Modifier.weight(1f)
            )
            Text(
                text = code,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp)
            )
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun QrScannerPreview() {
    QrScanner { userId, licensePlate, lotNumber, target ->
        println("Scanned values:")
        println("User ID: $userId")
        println("License Plate: $licensePlate")
        println("Lot Number: $lotNumber")
        println("Target: $target")
    }
}
