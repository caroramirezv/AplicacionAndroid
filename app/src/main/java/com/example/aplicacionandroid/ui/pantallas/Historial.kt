package com.example.aplicacionandroid.ui.pantallas

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.aplicacionandroid.model.EstadoAplicacion
import com.example.aplicacionandroid.ui.componentes.EtiquetaEstado
import com.example.aplicacionandroid.ui.componentes.IconoCircular
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.mutableStateListOf

data class RegistroHistorial(
    val id: String,
    val estudiante: String,
    val curso: String,
    val fecha: String,
    val instrumento: String,
    val estado: EstadoAplicacion,
    var comentario: String = ""
)

@Composable
fun Historial(registrosIniciales: List<RegistroHistorial> = registrosEjemplo()) {

    val registros = remember {
        mutableStateListOf<RegistroHistorial>().apply { addAll(registrosIniciales) }
    }
    var registroSeleccionado by remember { mutableStateOf<RegistroHistorial?>(null) }
    val contexto = LocalContext.current

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
            IconoCircular(icono = Icons.Default.History, tamano = 48.dp)
            Text(
                text = "Historial de aplicaciones",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        if (registros.isEmpty()) {
            Box(Modifier.fillMaxSize().padding(24.dp), contentAlignment = Alignment.Center) {
                Text(
                    "No hay aplicaciones registradas aún",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text(
                        "Ejemplos ilustrativos con datos ficticios.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                items(registros, key = { it.id }) { registro ->
                    TarjetaHistorial(
                        registro = registro,
                        onClick = { registroSeleccionado = registro }
                    )
                }
            }
        }
    }


    registroSeleccionado?.let { registro ->
        DialogoAnotacion(
            registro = registro,
            onDismiss = { registroSeleccionado = null },
            onGuardar = { comentarioActualizado ->

                val index = registros.indexOfFirst { it.id == registro.id }
                if (index != -1) {
                    registros[index] = registro.copy(comentario = comentarioActualizado)
                }

                Toast.makeText(contexto, "Comentario guardado exitosamente", Toast.LENGTH_SHORT).show()
                registroSeleccionado = null
            }
        )
    }
}

@Composable
private fun TarjetaHistorial(registro: RegistroHistorial, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    registro.estudiante,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.weight(1f).padding(end = 8.dp)
                )
                EtiquetaEstado(registro.estado)
            }
            Text(
                "Curso: ${registro.curso}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
            )
            Text(
                "Instrumento: ${registro.instrumento}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                "Fecha: ${registro.fecha}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
            )

            if (registro.comentario.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Anotación:",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = registro.comentario,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}


@Composable
fun DialogoAnotacion(
    registro: RegistroHistorial,
    onDismiss: () -> Unit,
    onGuardar: (String) -> Unit
) {
    var textoComentario by remember { mutableStateOf(registro.comentario) }

    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth()
            ) {

                Text(
                    text = "Anotación",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(16.dp))


                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilaDetalle(etiqueta = "Estudiante:", valor = registro.estudiante)
                        FilaDetalle(etiqueta = "Instrumento:", valor = registro.instrumento)
                        FilaDetalle(etiqueta = "Fecha:", valor = registro.fecha)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))


                OutlinedTextField(
                    value = textoComentario,
                    onValueChange = { textoComentario = it },
                    label = { Text("Escribe un comentario...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    maxLines = 5,
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = { onDismiss() }) {
                        Text("Cancelar")
                    }
                    Button(
                        onClick = { onGuardar(textoComentario) },
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Text("Guardar")
                    }
                }
            }
        }
    }
}
@Composable
private fun FilaDetalle(etiqueta: String, valor: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = etiqueta,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(end = 6.dp)
        )
        Text(
            text = valor,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

private fun registrosEjemplo(): List<RegistroHistorial> = listOf(
    RegistroHistorial("1", "Estudiante EST-001", "5.º A", "24-10-2026", "Cuestionario de hábitos de estudio", EstadoAplicacion.REVISADA),
    RegistroHistorial("2", "Estudiante EST-002", "6.º B", "22-10-2026", "Mini cuestionario de comprensión lectora (simulado)", EstadoAplicacion.FINALIZADA),
    RegistroHistorial("3", "Estudiante EST-003", "7.º C", "18-10-2026", "Escala de clima escolar (simulada)", EstadoAplicacion.PENDIENTE)
)