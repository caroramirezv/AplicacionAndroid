package com.example.aplicacionandroid.ui.pantallas

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.aplicacionandroid.data.CatalogoInstrumentos
import com.example.aplicacionandroid.data.repository.AplicacionRepository
import com.example.aplicacionandroid.model.EstadoAplicacion
import com.example.aplicacionandroid.model.Respuesta
import com.example.aplicacionandroid.ui.componentes.EtiquetaEstado
import com.example.aplicacionandroid.ui.componentes.FilaCodigo
import com.example.aplicacionandroid.ui.componentes.IconoCircular
import com.example.aplicacionandroid.ui.componentes.formatearFecha
import com.example.aplicacionandroid.ui.theme.AzulTitulo
import com.example.aplicacionandroid.ui.theme.FormaControl
import com.example.aplicacionandroid.ui.theme.FormaTarjeta
import com.example.aplicacionandroid.ui.theme.PaletaAzul
import com.example.aplicacionandroid.ui.theme.PaletaVerde
import com.example.aplicacionandroid.ui.theme.TextoSecundario
import com.example.aplicacionandroid.viewModel.ApplicationViewModel

@Composable
fun DetalleAplicacion(id: Int, vm: ApplicationViewModel) {
    val contexto = LocalContext.current
    val lista by vm.aplicaciones.collectAsState()
    val app = lista.firstOrNull { it.id == id }
    var observacion by remember(id) { mutableStateOf(vm.obtener(id)?.observacion ?: "") }

    LaunchedEffect(id) { vm.registrarConsulta(id) }

    if (app == null) {
        Text("La aplicación no existe.", Modifier.padding(16.dp))
        return
    }

    val instrumento = CatalogoInstrumentos.buscar(app.instrumentoId)
    val terminada = app.estado == EstadoAplicacion.FINALIZADA || app.estado == EstadoAplicacion.REVISADA
    val vencido = app.codigoActivo &&
        System.currentTimeMillis() - app.codigoGeneradoEn > AplicacionRepository.VIGENCIA_CODIGO_MS

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            IconoCircular(Icons.Default.Description)
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    instrumento?.nombre ?: app.instrumentoId,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                EtiquetaEstado(app.estado)
            }
        }

        ElevatedCard(Modifier.fillMaxWidth(), shape = FormaTarjeta) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    "Estudiante ${app.estudiante.idEstudiante} · ${app.estudiante.curso}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text("Creada: ${formatearFecha(app.fechaCreacion)}", style = MaterialTheme.typography.bodySmall)
                app.fechaInicio?.let { Text("Inicio: ${formatearFecha(it)}", style = MaterialTheme.typography.bodySmall) }
                app.fechaFin?.let { Text("Término: ${formatearFecha(it)}", style = MaterialTheme.typography.bodySmall) }
                if (app.estado == EstadoAplicacion.EN_DESARROLLO && instrumento != null) {
                    Text(
                        "Progreso: ${app.respuestas.size} de ${instrumento.preguntas.size} preguntas respondidas",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        if (app.codigoActivo) {
            ElevatedCard(
                Modifier.fillMaxWidth(),
                shape = FormaTarjeta,
                colors = CardDefaults.elevatedCardColors(containerColor = PaletaAzul.fondo)
            ) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "Código de acceso del estudiante",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = AzulTitulo
                    )
                    if (vencido) {
                        Text("El código venció.", color = MaterialTheme.colorScheme.error)
                    } else {
                        FilaCodigo(app.codigo, grande = true)
                    }
                    OutlinedButton(
                        onClick = { vm.regenerarCodigo(app.id) },
                        shape = FormaControl
                    ) { Text("Generar nuevo código") }
                }
            }
        }

        if (terminada && instrumento != null) {
            ElevatedCard(
                Modifier.fillMaxWidth(),
                shape = FormaTarjeta,
                colors = CardDefaults.elevatedCardColors(containerColor = PaletaVerde.fondo)
            ) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    val maximo = instrumento.puntajeMaximo()
                    Text(
                        if (app.puntaje != null && maximo != null) "Puntaje de prueba: ${app.puntaje} / $maximo"
                        else "Este instrumento no calcula puntaje",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = PaletaVerde.titulo
                    )
                }
            }

            Text("Respuestas", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = TextoSecundario)
            val respuestas = instrumento.preguntas.map { p ->
                Respuesta(p.texto, app.respuestas[p.id]?.let { p.alternativas.getOrNull(it) } ?: "Sin responder")
            }
            ElevatedCard(Modifier.fillMaxWidth(), shape = FormaTarjeta) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    respuestas.forEachIndexed { i, r ->
                        Column {
                            Text("${i + 1}. ${r.pregunta}", style = MaterialTheme.typography.bodyMedium)
                            Text("→ ${r.respuesta}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }

            OutlinedTextField(
                value = observacion,
                onValueChange = { observacion = it },
                label = { Text("Observación profesional (ficticia)") },
                shape = FormaControl,
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
            Button(
                onClick = {
                    vm.guardarObservacion(app.id, observacion)
                    Toast.makeText(contexto, "Observación guardada", Toast.LENGTH_SHORT).show()
                },
                shape = FormaControl,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) { Text("Guardar observación", fontWeight = FontWeight.SemiBold) }

            if (app.estado == EstadoAplicacion.FINALIZADA) {
                OutlinedButton(
                    onClick = { vm.marcarRevisada(app.id) },
                    shape = FormaControl,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) { Text("Marcar como revisada", fontWeight = FontWeight.SemiBold) }
            }
        }
    }
}
