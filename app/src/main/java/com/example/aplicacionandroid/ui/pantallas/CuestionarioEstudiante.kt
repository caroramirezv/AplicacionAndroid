package com.example.aplicacionandroid.ui.pantallas

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.aplicacionandroid.ui.theme.AzulTitulo
import com.example.aplicacionandroid.ui.theme.FondoPantalla
import com.example.aplicacionandroid.ui.theme.FormaControl
import com.example.aplicacionandroid.ui.theme.FormaTarjeta
import com.example.aplicacionandroid.ui.theme.PaletaAzul
import com.example.aplicacionandroid.viewModel.EstudianteViewModel

@Composable
fun CuestionarioEstudiante(
    vm: EstudianteViewModel,
    onFinalizado: () -> Unit,
    onSalir: () -> Unit
) {
    BackHandler(enabled = true) { }

    val instrumento = vm.instrumento
    if (instrumento == null || instrumento.preguntas.isEmpty()) {
        Column(
            Modifier
                .fillMaxSize()
                .background(FondoPantalla)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text("La sesión terminó. Vuelve a ingresar tu código.", style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = onSalir,
                shape = FormaControl,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) { Text("Volver", fontWeight = FontWeight.SemiBold) }
        }
        return
    }

    val total = instrumento.preguntas.size
    val indice = vm.indice.coerceIn(0, total - 1)
    val pregunta = instrumento.preguntas[indice]
    val elegida = vm.respuestas[pregunta.id]
    val ultima = indice == total - 1

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoPantalla)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            "Pregunta ${indice + 1} de $total",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            color = AzulTitulo
        )
        LinearProgressIndicator(
            progress = { (indice + 1) / total.toFloat() },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(50))
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ElevatedCard(Modifier.fillMaxWidth(), shape = FormaTarjeta) {
                Text(
                    pregunta.texto,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = AzulTitulo,
                    modifier = Modifier.padding(20.dp)
                )
            }
            Spacer(Modifier.height(4.dp))
            pregunta.alternativas.forEachIndexed { i, alternativa ->
                val seleccionada = elegida == i
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = FormaControl,
                    colors = CardDefaults.cardColors(
                        containerColor = if (seleccionada) PaletaAzul.fondo else Color.White
                    ),
                    border = if (seleccionada) BorderStroke(2.dp, AzulTitulo) else null
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = seleccionada,
                                role = Role.RadioButton,
                                onClick = { vm.seleccionar(pregunta.id, i) }
                            )
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        RadioButton(selected = seleccionada, onClick = null)
                        Text(alternativa, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedButton(
                onClick = { vm.anterior() },
                enabled = indice > 0,
                shape = FormaControl,
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp)
            ) { Text("Anterior", fontWeight = FontWeight.SemiBold) }

            if (ultima) {
                Button(
                    onClick = { vm.finalizar(onFinalizado) },
                    enabled = vm.completo,
                    shape = FormaControl,
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp)
                ) { Text("Finalizar", fontWeight = FontWeight.SemiBold) }
            } else {
                Button(
                    onClick = { vm.siguiente() },
                    enabled = elegida != null,
                    shape = FormaControl,
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp)
                ) { Text("Siguiente", fontWeight = FontWeight.SemiBold) }
            }
        }
    }
}
