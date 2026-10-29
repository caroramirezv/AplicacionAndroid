package com.example.aplicacionandroid.ui.navegacion

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.aplicacionandroid.ui.pantallas.AccesoEstudiante
import com.example.aplicacionandroid.ui.pantallas.Ajustes
import com.example.aplicacionandroid.ui.pantallas.CuestionarioEstudiante
import com.example.aplicacionandroid.ui.pantallas.DetalleAplicacion
import com.example.aplicacionandroid.ui.pantallas.FinEstudiante
import com.example.aplicacionandroid.ui.pantallas.Historial
import com.example.aplicacionandroid.ui.pantallas.InicioEstudiante
import com.example.aplicacionandroid.ui.pantallas.Inicio
import com.example.aplicacionandroid.ui.pantallas.Instrumentos
import com.example.aplicacionandroid.ui.pantallas.LoginScreen
import com.example.aplicacionandroid.ui.pantallas.NuevaAplicacion
import com.example.aplicacionandroid.viewModel.ApplicationViewModel
import com.example.aplicacionandroid.viewModel.EstudianteViewModel
import com.example.aplicacionandroid.viewModel.LoginViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    padding: PaddingValues,
    vm: ApplicationViewModel,
    login: LoginViewModel,
    estudiante: EstudianteViewModel
) {
    fun volverAlLogin() {
        navController.navigate(Ruta.Login.ruta) {
            popUpTo(navController.graph.id) { inclusive = true }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Ruta.Login.ruta,
        modifier = Modifier.padding(padding),
        enterTransition = { slideInHorizontally { it / 4 } + fadeIn() },
        exitTransition = { slideOutHorizontally { -it / 4 } + fadeOut() },
        popEnterTransition = { slideInHorizontally { -it / 4 } + fadeIn() },
        popExitTransition = { slideOutHorizontally { it / 4 } + fadeOut() }
    ) {
        composable(Ruta.Login.ruta) {
            LoginScreen(
                viewModel = login,
                onLoginSuccess = { psicologo ->
                    vm.iniciarSesion(psicologo.nombre)
                    navController.navigate(Ruta.Inicio.ruta) {
                        popUpTo(Ruta.Login.ruta) { inclusive = true }
                    }
                },
                onAccesoEstudiante = { navController.navigate(Ruta.Estudiante.ruta) }
            )
        }

        composable(Ruta.Inicio.ruta) {
            Inicio(onIr = { destino -> navController.navigate(destino) { launchSingleTop = true } })
        }
        composable(Ruta.Instrumentos.ruta) { Instrumentos(vm) }
        composable(Ruta.Nueva.ruta) { NuevaAplicacion(vm) }
        composable(Ruta.Historial.ruta) {
            Historial()
        }
        composable(
            route = Ruta.Detalle.ruta,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { entrada ->
            DetalleAplicacion(id = entrada.arguments?.getInt("id") ?: -1, vm = vm)
        }
        composable(Ruta.Ajustes.ruta) {
            Ajustes(vm, onCerrarSesion = {
                vm.cerrarSesion()
                login.limpiar()
                volverAlLogin()
            })
        }

        composable(Ruta.Estudiante.ruta) {
            AccesoEstudiante(
                vm = estudiante,
                onContinuar = {
                    navController.navigate(Ruta.EstInstrucciones.ruta) {
                        popUpTo(Ruta.Estudiante.ruta) { inclusive = true }
                    }
                },
                onVolverLogin = {
                    estudiante.reiniciar()
                    volverAlLogin()
                }
            )
        }
        composable(Ruta.EstInstrucciones.ruta) {
            InicioEstudiante(
                vm = estudiante,
                onComenzar = {
                    estudiante.comenzar()
                    navController.navigate(Ruta.EstCuestionario.ruta)
                },
                onSalir = {
                    estudiante.reiniciar()
                    volverAlLogin()
                }
            )
        }
        composable(Ruta.EstCuestionario.ruta) {
            CuestionarioEstudiante(
                vm = estudiante,
                onFinalizado = {
                    navController.navigate(Ruta.EstFin.ruta) {
                        popUpTo(Ruta.EstCuestionario.ruta) { inclusive = true }
                    }
                },
                onSalir = {
                    estudiante.reiniciar()
                    volverAlLogin()
                }
            )
        }
        composable(Ruta.EstFin.ruta) {
            FinEstudiante(onSalir = {
                estudiante.reiniciar()
                volverAlLogin()
            })
        }
    }
}
