package com.example.aplicacionandroid.ui.navegacion

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Ruta(val ruta: String, val titulo: String, val icono: ImageVector? = null) {
    object Login : Ruta("login", "Inicio de sesión")

    object Inicio : Ruta("inicio", "Inicio", Icons.Default.Home)
    object Instrumentos : Ruta("instrumentos", "Instrumentos", Icons.AutoMirrored.Filled.List)
    object Nueva : Ruta("nueva", "Nueva", Icons.Default.AddCircle)
    object Historial : Ruta("historial", "Historial", Icons.Default.History)
    object Ajustes : Ruta("ajustes", "Ajustes", Icons.Default.Settings)
    object Detalle : Ruta("detalle/{id}", "Detalle") {
        fun conId(id: Int) = "detalle/$id"
    }

    object Estudiante : Ruta("estudiante", "Acceso estudiante")
    object EstInstrucciones : Ruta("est_instrucciones", "Instrucciones")
    object EstCuestionario : Ruta("est_cuestionario", "Cuestionario")
    object EstFin : Ruta("est_fin", "Envío completado")

    companion object {
        val barra: List<Ruta> get() = listOf(Inicio, Instrumentos, Nueva, Historial)
    }
}
