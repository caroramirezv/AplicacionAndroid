package com.example.aplicacionandroid.ui.pantallas

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.example.aplicacionandroid.ui.theme.TextoSecundario

@Composable
fun FinEstudiante(onSalir: () -> Unit) {
    BackHandler(enabled = true) { }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoPantalla)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        IconoCircular(Icons.Default.CheckCircle, 88.dp)
        Spacer(Modifier.height(16.dp))
        Text(
            "¡Gracias!",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = AzulTitulo
        )
        Spacer(Modifier.height(16.dp))
        Text(
            "Tus respuestas fueron enviadas correctamente. Avisa al psicólogo que terminaste.",
            style = MaterialTheme.typography.bodyLarge,
            color = TextoSecundario,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(32.dp))
        Button(
            onClick = onSalir,
            shape = FormaControl,
            modifier = Modifier
                .widthIn(max = 480.dp)
                .fillMaxWidth()
                .height(52.dp)
        ) { Text("Salir", fontWeight = FontWeight.SemiBold) }
    }
}
