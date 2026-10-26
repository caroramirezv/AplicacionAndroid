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
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.aplicacionandroid.ui.componentes.IconoCircular

@Composable
fun Instrumentos() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        TarjetaInstrumento(
            Icons.Default.Extension,
            "Reconocimiento de patrones",
            "Esta actividad busca reconocer el desarrollo de cada alumno aspectos como la lógica, resolución de problemas, etc"
        )
        TarjetaInstrumento(
            Icons.Default.Visibility,
            "Actividad Enfoque y Concentración",
            "Esta actividad busca desarrollar el enfoque y concentración de cada alumno, priorizando la atención por sobre el desarrollo del problema"
        )
    }
}

@Composable
private fun TarjetaInstrumento(icono: ImageVector, titulo: String, descripcion: String) {
    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            IconoCircular(icono)
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    titulo,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    descripcion,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
