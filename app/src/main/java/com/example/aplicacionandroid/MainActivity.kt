package com.example.aplicacionandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.aplicacionandroid.ui.componentes.AppScaffold
import com.example.aplicacionandroid.ui.navegacion.AppNavHost
import com.example.aplicacionandroid.ui.theme.AplicacionAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AplicacionAndroidTheme {
                val nav = rememberNavController()
                AppScaffold(nav) { padding -> AppNavHost(nav, padding) }
            }
        }
    }
}
