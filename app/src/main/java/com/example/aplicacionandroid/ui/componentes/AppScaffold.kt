package com.example.aplicacionandroid.ui.componentes

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

const val RUTA_LOGIN = "login"
const val RUTA_INICIO = "inicio"
const val RUTA_INSTRUMENTOS = "instrumentos"
const val RUTA_NUEVA = "nueva"
const val RUTA_HISTORIAL = "historial"
const val RUTA_ESTUDIANTE = "estudiante"
const val RUTA_INICIO_ESTUDIANTE = "inicio_estudiante"
const val RUTA_AJUSTES = "ajustes"

private class Pestana(val ruta: String, val titulo: String, val icono: ImageVector)

private val pestanas = listOf(
    Pestana(RUTA_INICIO, "", Icons.Default.Home),
    Pestana(RUTA_INSTRUMENTOS, "", Icons.AutoMirrored.Filled.List),
    Pestana(RUTA_NUEVA, "", Icons.Default.AddCircle),
    Pestana(RUTA_HISTORIAL, "", Icons.Default.History)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(navController: NavHostController, content: @Composable (PaddingValues) -> Unit) {
    val actual = navController.currentBackStackEntryAsState().value?.destination?.route
    val pestana = pestanas.firstOrNull { it.ruta == actual }
    val enSecundaria = actual == RUTA_ESTUDIANTE || actual == RUTA_AJUSTES || actual == RUTA_INICIO_ESTUDIANTE
    val titulo = when {
        pestana != null -> pestana.titulo
        actual == RUTA_ESTUDIANTE -> "Acceso estudiante"
        actual == RUTA_INICIO_ESTUDIANTE -> "Inicio estudiante"
        else -> "Ajustes"
    }

    Scaffold(
        topBar = {
            if (pestana != null || enSecundaria) {
                CenterAlignedTopAppBar(
                    title = { Text(titulo, fontWeight = FontWeight.SemiBold) },
                    navigationIcon = {
                        if (enSecundaria) {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                            }
                        }
                    },
                    actions = {
                        if (pestana != null) {
                            IconButton(onClick = {
                                navController.navigate(RUTA_AJUSTES) { launchSingleTop = true }
                            }) {
                                Icon(Icons.Default.Settings, contentDescription = "Ajustes")
                            }
                        }
                    }
                )
            }
        },
        bottomBar = {
            if (pestana != null || actual == RUTA_AJUSTES) {
                NavigationBar(
                    containerColor = Color.White
                ) {
                    pestanas.forEach { item ->
                        NavigationBarItem(
                            selected = actual == item.ruta,
                            onClick = {
                                if (actual == item.ruta) return@NavigationBarItem

                                navController.navigate(item.ruta) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(item.icono, item.titulo) },
                            label = { Text(item.titulo) },
                            colors = NavigationBarItemDefaults.colors(
                                unselectedIconColor = Color(0xFF0D47A1),
                                unselectedTextColor = Color(0xFF0D47A1),
                                selectedIconColor = Color(0xFF002171),
                                selectedTextColor = Color(0xFF002171),
                                indicatorColor = Color(0xFFBBDEFB)
                            )
                        )
                    }
                }
            }
        },
        content = content
    )
}