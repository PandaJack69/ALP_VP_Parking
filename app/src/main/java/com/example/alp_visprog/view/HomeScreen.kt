package com.example.alp_visprog.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.alp_visprog.route.Screen

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { navController.navigate(Screen.QRScanner.route) },
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text(text = "Open QR Scanner")
        }

        Button(onClick = { navController.navigate(Screen.QRGenerator.route) }) {
            Text(text = "Open QR Generator")
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun HomeScreenPreview() {
//    // A fake NavController for preview purposes
//    val fakeNavController = object : NavController(LocalContext.current) {}
//    HomeScreen(navController = fakeNavController)
    HomeScreen(navController = rememberNavController())
}