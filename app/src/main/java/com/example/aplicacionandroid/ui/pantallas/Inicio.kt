package com.example.aplicacionandroid.ui.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
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
            .background(Color(0xFFF4F6F9))
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            text = "Panel de psicólogo PIE",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0D47A1),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = "Acciones principales",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF37474F),
            modifier = Modifier.padding(horizontal = 4.dp)
        )

        TarjetaAccion(
            icono = Icons.Default.AddCircle,
            titulo = "Nueva aplicación",
            descripcion = "Asigna un instrumento a un estudiante",
            bgColor = Color(0xFFE3F2FD),   // Azul pastel
            titleColor = Color(0xFF0D47A1), // Azul oscuro
            onClick = { onIr(RUTA_NUEVA) }
        )

        TarjetaAccion(
            icono = Icons.AutoMirrored.Filled.List,
            titulo = "Catálogo de instrumentos",
            descripcion = "Revisa los instrumentos simulados",
            bgColor = Color(0xFFE8F5E9),   // Verde pastel
            titleColor = Color(0xFF1B5E20), // Verde oscuro
            onClick = { onIr(RUTA_INSTRUMENTOS) }
        )

        TarjetaAccion(
            icono = Icons.Default.History,
            titulo = "Historial",
            descripcion = "Aplicaciones registradas y su estado",
            bgColor = Color(0xFFFFF3E0),
            titleColor = Color(0xFFE65100),
            onClick = { onIr(RUTA_HISTORIAL) }
        )

        TarjetaAccion(
            icono = Icons.Default.Person,
            titulo = "Acceso estudiante",
            descripcion = "Ingreso con código temporal",
            bgColor = Color(0xFFF3E5F5),
            titleColor = Color(0xFF4A148C),
            onClick = { onIr(RUTA_ESTUDIANTE) }
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Próximamente",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF37474F),
            modifier = Modifier.padding(horizontal = 4.dp)
        )

        TarjetaProximamente("Supervisión del progreso en tiempo real")
        TarjetaProximamente("Revisión de respuestas y puntajes")

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            "Herramienta de apoyo: no emite diagnósticos ni conclusiones. Todos los datos son ficticios.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
    }
}

@Composable
private fun TarjetaAccion(
    icono: ImageVector,
    titulo: String,
    descripcion: String,
    bgColor: Color,
    titleColor: Color,
    onClick: () -> Unit
) {
    ElevatedCard(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = bgColor
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 2.dp,
            pressedElevation = 6.dp
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            IconoCircular(icono)
            Column(Modifier.weight(1f)) {
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
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = titleColor
            )
        }
    }
}