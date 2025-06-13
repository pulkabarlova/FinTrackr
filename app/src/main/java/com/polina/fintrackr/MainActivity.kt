package com.polina.fintrackr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.polina.fintrackr.core.navigation.BottomNavBar
import com.polina.fintrackr.core.theme.FinTrackrTheme
import com.polina.fintrackr.core.ui.AppScaffold

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FinTrackrTheme {
                AppUi()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {}
