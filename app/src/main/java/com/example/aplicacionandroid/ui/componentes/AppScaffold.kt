package com.example.aplicacionandroid.ui.componentes

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.aplicacionandroid.ui.navegacion.Ruta
import com.example.aplicacionandroid.ui.theme.AzulClaro
import com.example.aplicacionandroid.ui.theme.AzulOscuro
import com.example.aplicacionandroid.ui.theme.AzulTitulo

private fun tituloDe(ruta: String?): String = when (ruta) {
    Ruta.Ajustes.ruta -> "Ajustes"
    Ruta.Detalle.ruta -> "Detalle de aplicación"
    Ruta.Estudiante.ruta -> "Acceso estudiante"
    else -> ""
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    navController: NavHostController,
    onAtras: () -> Unit = { navController.popBackStack() },
    content: @Composable (PaddingValues) -> Unit
) {
    val actual = navController.currentBackStackEntryAsState().value?.destination?.route
    val esPestana = Ruta.barra.any { it.ruta == actual }
    val enSecundaria = actual == Ruta.Ajustes.ruta ||
        actual == Ruta.Detalle.ruta ||
        actual == Ruta.Estudiante.ruta
    // La barra inferior solo existe en el perfil psicólogo
    val mostrarBarra = esPestana || actual == Ruta.Ajustes.ruta || actual == Ruta.Detalle.ruta

    Scaffold(
        topBar = {
            if (esPestana || enSecundaria) {
                CenterAlignedTopAppBar(
                    title = { Text(tituloDe(actual), fontWeight = FontWeight.SemiBold) },
                    navigationIcon = {
                        if (enSecundaria) {
                            IconButton(onClick = onAtras) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                            }
                        }
                    },
                    actions = {
                        if (esPestana) {
                            IconButton(onClick = {
                                navController.navigate(Ruta.Ajustes.ruta) { launchSingleTop = true }
                            }) {
                                Icon(Icons.Default.Settings, contentDescription = "Ajustes")
                            }
                        }
                    }
                )
            }
        },
        bottomBar = {
            if (mostrarBarra) {
                NavigationBar(containerColor = Color.White) {
                    Ruta.barra.forEach { item ->
                        NavigationBarItem(
                            selected = actual == item.ruta ||
                                (item == Ruta.Historial && actual == Ruta.Detalle.ruta),
                            onClick = {
                                if (actual == item.ruta) return@NavigationBarItem
                                navController.navigate(item.ruta) {
                                    popUpTo(Ruta.Inicio.ruta) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { item.icono?.let { Icon(it, item.titulo) } },
                            colors = NavigationBarItemDefaults.colors(
                                unselectedIconColor = AzulTitulo,
                                selectedIconColor = AzulOscuro,
                                indicatorColor = AzulClaro
                            )
                        )
                    }
                }
            }
        },
        content = content
    )
}
