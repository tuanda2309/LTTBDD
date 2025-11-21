package com.example.uthapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.uthapi.navigation.AppNavGraph
import com.example.uthapi.ui.theme.UTHAPITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UTHAPITheme {
                val navController = rememberNavController()
                AppNavGraph(navController)
            }
        }
    }
}
