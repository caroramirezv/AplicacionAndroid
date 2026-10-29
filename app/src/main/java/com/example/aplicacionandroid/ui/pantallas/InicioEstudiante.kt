package com.example.aplicacionandroid.ui.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.aplicacionandroid.ui.componentes.IconoCircular
import com.example.aplicacionandroid.ui.theme.AzulTitulo
import com.example.aplicacionandroid.ui.theme.FondoPantalla
import com.example.aplicacionandroid.ui.theme.FormaControl
import com.example.aplicacionandroid.ui.theme.FormaTarjeta
import com.example.aplicacionandroid.ui.theme.PaletaAzul
import com.example.aplicacionandroid.ui.theme.TextoSecundario
import com.example.aplicacionandroid.viewModel.EstudianteViewModel

@Composable
fun InicioEstudiante(
    vm: EstudianteViewModel,
    onComenzar: () -> Unit,
    onSalir: () -> Unit
) {
    val instrumento = vm.instrumento

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoPantalla)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (instrumento == null) {
            Text(
                "La sesión terminó. Vuelve a ingresar tu código.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = onSalir,
                shape = FormaControl,
                modifier = Modifier
                    .widthIn(max = 480.dp)
                    .fillMaxWidth()
                    .height(52.dp)
            ) { Text("Volver", fontWeight = FontWeight.SemiBold) }
        } else {
            IconoCircular(Icons.Default.Extension, 88.dp)
            Spacer(Modifier.height(16.dp))
            Text(
                instrumento.nombre,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = AzulTitulo,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(16.dp))

            ElevatedCard(
                modifier = Modifier.widthIn(max = 480.dp).fillMaxWidth(),
                shape = FormaTarjeta,
                colors = CardDefaults.elevatedCardColors(containerColor = PaletaAzul.fondo)
            ) {
                Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        instrumento.instrucciones,
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextoSecundario
                    )
                    Text(
                        "${instrumento.preguntas.size} preguntas · aprox. ${instrumento.minutosEstimados} minutos",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = AzulTitulo
                    )
                }
            }

            Spacer(Modifier.height(24.dp))
            Button(
                onClick = onComenzar,
                shape = FormaControl,
                modifier = Modifier
                    .widthIn(max = 480.dp)
                    .fillMaxWidth()
                    .height(52.dp)
            ) { Text("Comenzar", fontWeight = FontWeight.SemiBold) }
            TextButton(onClick = onSalir) { Text("Salir") }
        }
    }
}
