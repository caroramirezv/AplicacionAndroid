package com.example.aplicacionandroid.ui.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.aplicacionandroid.ui.componentes.IconoCircular

@Composable
fun Instrumentos() {
    Column(
        modifier = Modifier
            .fillMaxSize()

            .background(Color(0xFFF4F6F9))
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Instrumentos",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0D47A1),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )


        TarjetaInstrumento(
            icono = Icons.Default.Extension,
            titulo = "Reconocimiento de patrones",
            descripcion = "Esta actividad busca reconocer el desarrollo de cada alumno aspectos como la lógica, resolución de problemas, etc",
            bgColor = Color(0xFFE3F2FD),
            titleColor = Color(0xFF0D47A1)
        )


        TarjetaInstrumento(
            icono = Icons.Default.Visibility,
            titulo = "Actividad Enfoque y Concentración",
            descripcion = "Esta actividad busca desarrollar el enfoque y concentración de cada alumno, priorizando la atención por sobre el desarrollo del problema",
            bgColor = Color(0xFFE8F5E9),
            titleColor = Color(0xFF1B5E20)
        )
    }
}


@Composable
private fun TarjetaInstrumento(
    icono: ImageVector,
    titulo: String,
    descripcion: String,
    bgColor: Color,
    titleColor: Color
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = bgColor
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            IconoCircular(icono)
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = titulo,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = titleColor
                )
                Text(
                    text = descripcion,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF37474F)
                )
            }
        }
    }
}
