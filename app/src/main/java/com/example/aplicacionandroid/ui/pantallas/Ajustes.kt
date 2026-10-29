package com.example.aplicacionandroid.ui.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.aplicacionandroid.ui.componentes.IconoCircular
import com.example.aplicacionandroid.ui.componentes.formatearFechaCompleta
import com.example.aplicacionandroid.ui.theme.FormaControl
import com.example.aplicacionandroid.ui.theme.FormaTarjeta
import com.example.aplicacionandroid.ui.theme.TextoSecundario
import com.example.aplicacionandroid.viewModel.ApplicationViewModel

@Composable
fun Ajustes(vm: ApplicationViewModel, onCerrarSesion: () -> Unit) {
    val registro by vm.registro.collectAsState()

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            ElevatedCard(Modifier.fillMaxWidth(), shape = FormaTarjeta) {
                Row(
                    Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    IconoCircular(Icons.Default.Person)
                    Column {
                        Text("Sesión activa", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Text(
                            vm.usuario,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
        item {
            OutlinedButton(
                onClick = onCerrarSesion,
                shape = FormaControl,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) { Text("Cerrar sesión", fontWeight = FontWeight.SemiBold) }
        }
        item {
            Text(
                "Registro de actividad",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = TextoSecundario,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 4.dp)
            )
        }
        items(registro) { r ->
            Card(
                Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(Modifier.padding(12.dp)) {
                    Text(r.accion, style = MaterialTheme.typography.bodyMedium)
                    Text(
                        "${formatearFechaCompleta(r.fecha)} · ${r.usuario}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
