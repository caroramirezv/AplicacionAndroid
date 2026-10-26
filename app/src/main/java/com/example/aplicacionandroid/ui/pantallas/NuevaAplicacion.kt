package com.example.aplicacionandroid.ui.pantallas

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun NuevaAplicacion() {
    val context = LocalContext.current
    var idAlumno by rememberSaveable { mutableStateOf("") }
    var cursoAlumno by rememberSaveable { mutableStateOf("") }
    val forma = RoundedCornerShape(14.dp)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Crear aplicación",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        ElevatedCard(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = idAlumno,
                    onValueChange = { idAlumno = it },
                    label = { Text("Id Alumno") },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                    singleLine = true,
                    shape = forma,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = cursoAlumno,
                    onValueChange = { cursoAlumno = it },
                    label = { Text("Curso Alumno") },
                    singleLine = true,
                    shape = forma,
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        Toast.makeText(
                            context,
                            "Esta opción estará disponible próximamente",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    shape = forma,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) {
                    Text("Crear aplicación", fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}
