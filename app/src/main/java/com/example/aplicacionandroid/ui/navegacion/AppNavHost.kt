package com.example.aplicacionandroid.ui.navegacion

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.aplicacionandroid.ui.componentes.RUTA_AJUSTES
import com.example.aplicacionandroid.ui.componentes.RUTA_ESTUDIANTE
import com.example.aplicacionandroid.ui.componentes.RUTA_HISTORIAL
import com.example.aplicacionandroid.ui.componentes.RUTA_INICIO
import com.example.aplicacionandroid.ui.componentes.RUTA_INSTRUMENTOS
import com.example.aplicacionandroid.ui.componentes.RUTA_LOGIN
import com.example.aplicacionandroid.ui.componentes.RUTA_NUEVA
import com.example.aplicacionandroid.ui.pantallas.AccesoEstudiante
import com.example.aplicacionandroid.ui.pantallas.Ajustes
import com.example.aplicacionandroid.ui.pantallas.Historial
import com.example.aplicacionandroid.ui.pantallas.Inicio
import com.example.aplicacionandroid.ui.pantallas.Instrumentos
import com.example.aplicacionandroid.ui.pantallas.Login
import com.example.aplicacionandroid.ui.pantallas.NuevaAplicacion
import com.example.aplicacionandroid.viewModel.LoginViewModel

@Composable
fun AppNavHost(navController: NavHostController, padding: PaddingValues) {
    val ir: (String) -> Unit = { navController.navigate(it) { launchSingleTop = true } }

    NavHost(navController, startDestination = RUTA_LOGIN, modifier = Modifier.padding(padding)) {
        composable(RUTA_LOGIN) {
            val loginVm: LoginViewModel = viewModel()
            val estado by loginVm.estado.collectAsState()

            LaunchedEffect(estado.psicologo) {
                if (estado.psicologo != null) {
                    navController.navigate(RUTA_INICIO) { popUpTo(RUTA_LOGIN) { inclusive = true } }
                }
            }

            Login(
                error = estado.error,
                cargando = estado.cargando,
                onIngresar = loginVm::ingresar,
                onEditar = loginVm::limpiarError,
                onAccesoEstudiante = { ir(RUTA_ESTUDIANTE) }
            )
        }
        composable(RUTA_INICIO) {Inicio(onIr = ir) }
        composable(RUTA_INSTRUMENTOS) { Instrumentos() }
        composable(RUTA_NUEVA) { NuevaAplicacion() }
        composable(RUTA_HISTORIAL) { Historial() }
        composable(RUTA_ESTUDIANTE) { AccesoEstudiante() }
        composable(RUTA_AJUSTES) {
            Ajustes(
                onCerrarSesion = {
                    navController.navigate(RUTA_LOGIN) { popUpTo(RUTA_INICIO) { inclusive = true } }
                }
            )
        }
    }
}
