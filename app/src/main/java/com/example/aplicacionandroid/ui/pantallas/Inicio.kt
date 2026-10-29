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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.example.aplicacionandroid.ui.componentes.TituloPantalla
import com.example.aplicacionandroid.ui.navegacion.Ruta
import com.example.aplicacionandroid.ui.theme.FondoPantalla
import com.example.aplicacionandroid.ui.theme.FormaTarjeta
import com.example.aplicacionandroid.ui.theme.PaletaAzul
import com.example.aplicacionandroid.ui.theme.PaletaMorada
import com.example.aplicacionandroid.ui.theme.PaletaNaranja
import com.example.aplicacionandroid.ui.theme.PaletaTarjeta
import com.example.aplicacionandroid.ui.theme.PaletaVerde
import com.example.aplicacionandroid.ui.theme.TextoSecundario

@Composable
fun Inicio(onIr: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoPantalla)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        TituloPantalla("Panel de psicólogo PIE")

        Text(
            text = "Acciones principales",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = TextoSecundario,
            modifier = Modifier.padding(horizontal = 4.dp)
        )

        TarjetaAccion(
            icono = Icons.Default.AddCircle,
            titulo = "Nueva aplicación",
            descripcion = "Asigna un instrumento a un estudiante",
            paleta = PaletaAzul,
            onClick = { onIr(Ruta.Nueva.ruta) }
        )
        TarjetaAccion(
            icono = Icons.AutoMirrored.Filled.List,
            titulo = "Catálogo de instrumentos",
            descripcion = "Revisa los instrumentos disponibles",
            paleta = PaletaVerde,
            onClick = { onIr(Ruta.Instrumentos.ruta) }
        )
        TarjetaAccion(
            icono = Icons.Default.History,
            titulo = "Historial",
            descripcion = "Aplicaciones registradas y su estado",
            paleta = PaletaNaranja,
            onClick = { onIr(Ruta.Historial.ruta) }
        )
        TarjetaAccion(
            icono = Icons.Default.Person,
            titulo = "Acceso estudiante",
            descripcion = "Ingreso con código temporal",
            paleta = PaletaMorada,
            onClick = { onIr(Ruta.Estudiante.ruta) }
        )

        Spacer(Modifier.height(8.dp))

    }
}

@Composable
private fun TarjetaAccion(
    icono: ImageVector,
    titulo: String,
    descripcion: String,
    paleta: PaletaTarjeta,
    onClick: () -> Unit
) {
    ElevatedCard(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = FormaTarjeta,
        colors = CardDefaults.elevatedCardColors(containerColor = paleta.fondo),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp, pressedElevation = 6.dp)
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
                    color = paleta.titulo
                )
                Text(
                    text = descripcion,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextoSecundario
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = paleta.titulo
            )
        }
    }
}
