package com.example.aplicacionandroid.ui.componentes

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.aplicacionandroid.model.EstadoAplicacion
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun copiarCodigo(context: Context, codigo: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    clipboard.setPrimaryClip(ClipData.newPlainText("Código de acceso", codigo))
    Toast.makeText(context, "Código copiado: $codigo", Toast.LENGTH_SHORT).show()
}

fun formatearFecha(ms: Long): String =
    SimpleDateFormat("dd-MM-yyyy HH:mm", Locale("es", "CL")).format(Date(ms))

fun formatearFechaCompleta(ms: Long): String =
    SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale("es", "CL")).format(Date(ms))

@Composable
fun FilaCodigo(codigo: String, grande: Boolean = false, modifier: Modifier = Modifier) {
    val contexto = LocalContext.current
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = codigo,
            style = if (grande) MaterialTheme.typography.displaySmall else MaterialTheme.typography.titleMedium,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold
        )
        IconButton(onClick = { copiarCodigo(contexto, codigo) }) {
            Icon(Icons.Default.ContentCopy, contentDescription = "Copiar código")
        }
    }
}

@Composable
fun EtiquetaEstado(estado: EstadoAplicacion) {
    val color = when (estado) {
        EstadoAplicacion.PENDIENTE -> MaterialTheme.colorScheme.tertiaryContainer
        EstadoAplicacion.EN_DESARROLLO -> MaterialTheme.colorScheme.secondaryContainer
        EstadoAplicacion.FINALIZADA -> MaterialTheme.colorScheme.primaryContainer
        EstadoAplicacion.REVISADA -> MaterialTheme.colorScheme.surfaceVariant
    }
    Surface(shape = RoundedCornerShape(50), color = color) {
        Text(
            text = estado.etiqueta,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
        )
    }
}
