package com.example.aplicacionandroid.ui.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.aplicacionandroid.ui.componentes.IconoCircular

data class RegistroHistorial(
    val id: String,
    val nombreAlumno: String,
    val curso: String,
    val fecha: String,
    val instrumentoPsicologico: String
)

@Composable
fun Historial(
    registros: List<RegistroHistorial> = obtenerRegistrosEjemplo()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            IconoCircular(
                icono = Icons.Default.History,
                tamano = 48.dp
            )
            Text(
                text = "Historial de Evaluaciones",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        if (registros.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "No hay evaluaciones registradas aún",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(registros, key = { it.id }) { registro ->
                    TarjetaHistorial(registro = registro)
                }
            }
        }
    }
}

@Composable
fun TarjetaHistorial(registro: RegistroHistorial) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Text(
                text = registro.nombreAlumno,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = "Curso: ${registro.curso}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(4.dp))


            Text(
                text = "Instrumento: ${registro.instrumentoPsicologico}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(4.dp))


            Text(
                text = "Fecha: ${registro.fecha}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
            )
        }
    }
}


private fun obtenerRegistrosEjemplo(): List<RegistroHistorial> {
    return listOf(
        RegistroHistorial(
            id = "1",
            nombreAlumno = "Carlos Mendoza Reyes",
            curso = "3º Medio A",
            fecha = "24/10/2026",
            instrumentoPsicologico = "Inventario de Ansiedad de Beck (BAI)"
        ),
        RegistroHistorial(
            id = "2",
            nombreAlumno = "María Fernández Silva",
            curso = "1º Medio B",
            fecha = "22/10/2026",
            instrumentoPsicologico = "Escala de Autoestima de Rosenberg"
        ),
        RegistroHistorial(
            id = "3",
            nombreAlumno = "Juan Pablo Gómez",
            curso = "4º Medio C",
            fecha = "18/10/2026",
            instrumentoPsicologico = "Cuestionario de Depresión de Beck (BDI-II)"
        )
    )
}