package com.lia.alp_vp_2

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Size
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.lia.alp_vp_2.route.AppRouting
import com.lia.alp_vp_2.ui.theme.ALP_VP_2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ALP_VP_2Theme {
                AppRouting()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    ALP_VP_2Theme {
        AppRouting()
    }
}