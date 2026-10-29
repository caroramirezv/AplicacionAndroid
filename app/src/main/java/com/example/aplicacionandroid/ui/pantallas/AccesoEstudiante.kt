package com.example.aplicacionandroid.ui.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.TextButton
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplicacionandroid.data.repository.AplicacionRepository
import com.example.aplicacionandroid.ui.componentes.IconoCircular
import com.example.aplicacionandroid.ui.theme.FormaControl
import com.example.aplicacionandroid.viewModel.EstudianteViewModel

@Composable
fun AccesoEstudiante(
    vm: EstudianteViewModel,
    onContinuar: () -> Unit,
    onVolverLogin: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        IconoCircular(Icons.Default.Person, 88.dp)

        Spacer(Modifier.height(16.dp))

        Text(
            "Ingresa tu código",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            "Pídele el código al psicólogo que está contigo.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(24.dp))

        ElevatedCard(modifier = Modifier.widthIn(max = 480.dp).fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = vm.codigo,
                    onValueChange = { nuevo ->
                        vm.codigo = nuevo.uppercase()
                            .filter { it.isLetterOrDigit() }
                            .take(AplicacionRepository.LONGITUD_CODIGO)
                        vm.error = null
                    },
                    label = { Text("Código temporal") },
                    textStyle = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 28.sp, textAlign = TextAlign.Center),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Characters),
                    isError = vm.error != null,
                    supportingText = { vm.error?.let { Text(it) } },
                    shape = FormaControl,
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = { vm.ingresar(onContinuar) },
                    shape = FormaControl,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) { Text("Ingresar", fontWeight = FontWeight.SemiBold) }
            }
        }

        Spacer(Modifier.height(8.dp))

        TextButton(onClick = onVolverLogin) {
            Text("Volver al inicio de sesión")
        }
    }
}
