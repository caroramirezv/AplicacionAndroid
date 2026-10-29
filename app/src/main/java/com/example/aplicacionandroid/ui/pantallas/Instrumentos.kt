package com.example.aplicacionandroid.ui.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Visibility
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
import com.example.aplicacionandroid.model.EstadoInstrumento
import com.example.aplicacionandroid.model.Instrumento
import com.example.aplicacionandroid.ui.componentes.IconoCircular
import com.example.aplicacionandroid.ui.componentes.TituloPantalla
import com.example.aplicacionandroid.ui.theme.FondoPantalla
import com.example.aplicacionandroid.ui.theme.FormaTarjeta
import com.example.aplicacionandroid.ui.theme.PaletaTarjeta
import com.example.aplicacionandroid.ui.theme.PaletasTarjeta
import com.example.aplicacionandroid.ui.theme.TextoSecundario
import com.example.aplicacionandroid.viewModel.ApplicationViewModel

private val iconosInstrumento = listOf(Icons.Default.Extension, Icons.Default.Visibility, Icons.Default.School)

@Composable
fun Instrumentos(vm: ApplicationViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoPantalla)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TituloPantalla("Instrumentos")

        vm.instrumentos.forEachIndexed { i, inst ->
            TarjetaInstrumento(
                inst = inst,
                icono = if (inst.asignable) iconosInstrumento[i % iconosInstrumento.size] else Icons.Default.Lock,
                paleta = PaletasTarjeta[i % PaletasTarjeta.size]
            )
        }
    }
}

@Composable
private fun TarjetaInstrumento(inst: Instrumento, icono: ImageVector, paleta: PaletaTarjeta) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = FormaTarjeta,
        colors = CardDefaults.elevatedCardColors(containerColor = paleta.fondo),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            IconoCircular(icono)
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = inst.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = paleta.titulo
                )
                Text(inst.tipo.etiqueta, style = MaterialTheme.typography.labelMedium, color = TextoSecundario)
                Text(inst.descripcion, style = MaterialTheme.typography.bodyMedium, color = TextoSecundario)
                Text("Dirigido a: ${inst.rangoEdad}", style = MaterialTheme.typography.bodySmall, color = TextoSecundario)
                if (inst.preguntas.isNotEmpty()) {
                    Text(
                        "${inst.preguntas.size} preguntas · ${inst.minutosEstimados} min aprox.",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextoSecundario
                    )
                }
                Text(
                    "Estado: ${inst.estado.etiqueta}",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = if (inst.estado == EstadoInstrumento.DISPONIBLE)
                        MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
