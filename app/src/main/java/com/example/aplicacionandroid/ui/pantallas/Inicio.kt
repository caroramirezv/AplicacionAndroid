package com.example.aplicacionandroid.ui.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.aplicacionandroid.ui.componentes.IconoCircular
import com.example.aplicacionandroid.ui.componentes.RUTA_ESTUDIANTE
import com.example.aplicacionandroid.ui.componentes.RUTA_HISTORIAL
import com.example.aplicacionandroid.ui.componentes.RUTA_INSTRUMENTOS
import com.example.aplicacionandroid.ui.componentes.RUTA_NUEVA
import com.example.aplicacionandroid.ui.componentes.TarjetaProximamente

@Composable
fun Inicio(onIr: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            "Panel del psicólogo PIE",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        TarjetaAccion(
            Icons.Default.AddCircle,
            "Nueva aplicación",
            "Asigna un instrumento a un estudiante ficticio"
        ) { onIr(RUTA_NUEVA) }
        TarjetaAccion(
            Icons.AutoMirrored.Filled.List,
            "Catálogo de instrumentos",
            "Revisa los instrumentos simulados"
        ) { onIr(RUTA_INSTRUMENTOS) }
        TarjetaAccion(
            Icons.Default.History,
            "Historial",
            "Aplicaciones registradas y su estado"
        ) { onIr(RUTA_HISTORIAL) }
        TarjetaAccion(
            Icons.Default.Person,
            "Acceso estudiante",
            "Ingreso con código temporal"
        ) { onIr(RUTA_ESTUDIANTE) }

        TarjetaProximamente("Supervisión del progreso en tiempo real")
        TarjetaProximamente("Revisión de respuestas y puntajes")

        Text(
            "Herramienta de apoyo: no emite diagnósticos ni conclusiones. Todos los datos son ficticios.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun TarjetaAccion(icono: ImageVector, titulo: String, descripcion: String, onClick: () -> Unit) {
    ElevatedCard(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            IconoCircular(icono)
            Column(Modifier.weight(1f)) {
                Text(
                    titulo,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    descripcion,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
