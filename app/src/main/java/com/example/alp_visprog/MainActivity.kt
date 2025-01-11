package com.example.alp_visprog

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Size
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.alp_visprog.route.AppRouting
import com.example.alp_visprog.ui.theme.ALP_VisProgTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ALP_VisProgTheme {
                AppRouting()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    ALP_VisProgTheme {
        AppRouting()
    }
}
