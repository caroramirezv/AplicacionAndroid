package com.example.aplicacionandroid.ui.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.aplicacionandroid.data.CatalogoInstrumentos
import com.example.aplicacionandroid.ui.componentes.IconoCircular
import com.example.aplicacionandroid.ui.componentes.TituloPantalla
import com.example.aplicacionandroid.ui.componentes.copiarCodigo
import com.example.aplicacionandroid.ui.theme.AzulTitulo
import com.example.aplicacionandroid.ui.theme.FormaControl
import com.example.aplicacionandroid.ui.theme.FormaTarjeta
import com.example.aplicacionandroid.ui.theme.PaletaAzul
import com.example.aplicacionandroid.ui.theme.TextoSecundario
import com.example.aplicacionandroid.viewModel.ApplicationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuevaAplicacion(vm: ApplicationViewModel) {
    var expandedCurso by remember { mutableStateOf(false) }

    val listaCursos = remember {
        val basicos = (1..8).flatMap { listOf("$it.° Básico A", "$it.° Básico B") }
        val medios = (1..4).flatMap { listOf("$it.° Medio A", "$it.° Medio B") }
        basicos + medios
    }
    val contexto = LocalContext.current
    val aplicaciones by vm.aplicaciones.collectAsState()

    var idEstudiante by rememberSaveable { mutableStateOf("") }
    var curso by rememberSaveable { mutableStateOf("") }
    var instrumentoId by rememberSaveable { mutableStateOf<String?>(null) }
    var creadaId by rememberSaveable { mutableStateOf<Int?>(null) }
    var error by remember { mutableStateOf<String?>(null) }

    val creada = aplicaciones.firstOrNull { it.id == creadaId }

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TituloPantalla(if (creada != null) "Aplicación creada" else "Crear aplicación")

        if (creada != null) {
            val nombreInstrumento = CatalogoInstrumentos.buscar(creada.instrumentoId)?.nombre ?: creada.instrumentoId
            ElevatedCard(
                Modifier.fillMaxWidth(),
                shape = FormaTarjeta,
                colors = CardDefaults.elevatedCardColors(containerColor = PaletaAzul.fondo)
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IconoCircular(Icons.Default.CheckCircle, 64.dp)
                    Text(
                        "$nombreInstrumento\nEstudiante ${creada.estudiante.idEstudiante} · ${creada.estudiante.curso}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextoSecundario,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        "Código de acceso (vigente por 2 horas)",
                        style = MaterialTheme.typography.labelLarge,
                        color = AzulTitulo
                    )
                    Text(
                        creada.codigo,
                        style = MaterialTheme.typography.displayMedium,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        color = AzulTitulo
                    )
                    Button(
                        onClick = { copiarCodigo(contexto, creada.codigo) },
                        shape = FormaControl,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                    ) {
                        Icon(Icons.Default.ContentCopy, contentDescription = null)
                        Text("  Copiar código", fontWeight = FontWeight.SemiBold)
                    }
                }
            }
            OutlinedButton(
                onClick = {
                    creadaId = null
                    idEstudiante = ""
                    curso = ""
                    instrumentoId = null
                },
                shape = FormaControl,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) { Text("Crear otra aplicación", fontWeight = FontWeight.SemiBold) }
        } else {
            Text(
                "Usa solo estudiantes ficticios. No ingreses nombres, RUT ni datos reales.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 4.dp)
            )

            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedTextField(
                        value = idEstudiante,
                        onValueChange = { idEstudiante = it },
                        label = { Text("ID estudiante (ficticio, ej: EST-001)") },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                        singleLine = true,
                        shape = FormaControl,
                        modifier = Modifier.fillMaxWidth()
                    )
                    ExposedDropdownMenuBox(
                        expanded = expandedCurso,
                        onExpandedChange = { expandedCurso = !expandedCurso },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = curso,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Selecciona el curso") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCurso) },
                            shape = FormaControl,
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                        )
                        ExposedDropdownMenu(
                            expanded = expandedCurso,
                            onDismissRequest = { expandedCurso = false }
                        ) {
                            listaCursos.forEach { opcion ->
                                DropdownMenuItem(
                                    text = { Text(opcion) },
                                    onClick = {
                                        curso = opcion
                                        expandedCurso = false
                                    }
                                )
                            }
                        }
                    }

                    Text("Instrumento", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    vm.instrumentos.forEach { inst ->
                        val habilitado = inst.asignable
                        val seleccionado = instrumentoId == inst.id
                        Card(
                            Modifier.fillMaxWidth(),
                            shape = FormaControl,
                            colors = CardDefaults.cardColors(
                                containerColor = if (seleccionado) PaletaAzul.fondo
                                else MaterialTheme.colorScheme.surfaceVariant
                            )
                        ) {
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .selectable(
                                        selected = seleccionado,
                                        enabled = habilitado,
                                        role = Role.RadioButton,
                                        onClick = { instrumentoId = inst.id }
                                    )
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                RadioButton(selected = seleccionado, onClick = null, enabled = habilitado)
                                Column {
                                    Text(inst.nombre, style = MaterialTheme.typography.bodyLarge)
                                    Text(
                                        if (habilitado) "${inst.preguntas.size} preguntas · ${inst.minutosEstimados} min"
                                        else inst.estado.etiqueta,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }

                    error?.let { Text(it, color = MaterialTheme.colorScheme.error) }

                    Button(
                        onClick = {
                            when {
                                idEstudiante.isBlank() -> error = "Ingresa el ID del estudiante ficticio"
                                curso.isBlank() -> error = "Ingresa el curso ficticio"
                                instrumentoId == null -> error = "Selecciona un instrumento"
                                else -> {
                                    val nueva = vm.crearAplicacion(idEstudiante, curso, instrumentoId!!)
                                    if (nueva == null) {
                                        error = "No se pudo crear la aplicación con ese instrumento"
                                    } else {
                                        error = null
                                        creadaId = nueva.id
                                    }
                                }
                            }
                        },
                        shape = FormaControl,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                    ) { Text("Crear aplicación", fontWeight = FontWeight.SemiBold) }
                }
            }
        }
    }
}
