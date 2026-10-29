package com.example.aplicacionandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.aplicacionandroid.ui.componentes.AppScaffold
import com.example.aplicacionandroid.ui.navegacion.AppNavHost
import com.example.aplicacionandroid.ui.navegacion.Ruta
import com.example.aplicacionandroid.ui.theme.AplicacionAndroidTheme
import com.example.aplicacionandroid.viewModel.ApplicationViewModel
import com.example.aplicacionandroid.viewModel.EstudianteViewModel
import com.example.aplicacionandroid.viewModel.LoginViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AplicacionAndroidTheme {
                val nav = rememberNavController()
                val vm: ApplicationViewModel = viewModel()
                val login: LoginViewModel = viewModel()
                val estudiante: EstudianteViewModel = viewModel()
                AppScaffold(
                    navController = nav,
                    onAtras = {
                        if (nav.currentDestination?.route == Ruta.Estudiante.ruta) estudiante.reiniciar()
                        nav.popBackStack()
                    }
                ) { padding -> AppNavHost(nav, padding, vm, login, estudiante) }
            }
        }
    }
}
