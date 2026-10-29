package com.example.aplicacionandroid.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.aplicacionandroid.data.CatalogoInstrumentos
import com.example.aplicacionandroid.data.repository.AplicacionRepository
import com.example.aplicacionandroid.data.repository.ResultadoCodigo
import com.example.aplicacionandroid.model.Aplicacion
import com.example.aplicacionandroid.model.Instrumento

class EstudianteViewModel : ViewModel() {

    private val repo = AplicacionRepository.instancia

    var codigo by mutableStateOf("")
    var error by mutableStateOf<String?>(null)

    var instrumento by mutableStateOf<Instrumento?>(null)
        private set
    var aplicacionId by mutableStateOf<Int?>(null)
        private set
    var indice by mutableIntStateOf(0)
        private set
    val respuestas = mutableStateMapOf<Int, Int>()

    private var actor = "Estudiante"

    fun ingresar(onOk: () -> Unit) {
        if (codigo.length < AplicacionRepository.LONGITUD_CODIGO) {
            error = "Ingresa el código de ${AplicacionRepository.LONGITUD_CODIGO} caracteres"
            return
        }
        when (val resultado = repo.validarCodigo(codigo)) {
            is ResultadoCodigo.Valido -> {
                cargar(resultado.aplicacion)
                error = null
                onOk()
            }
            ResultadoCodigo.NoExiste -> error = "Código no válido"
            ResultadoCodigo.Expirado -> error = "El código venció. Pide uno nuevo al psicólogo."
            ResultadoCodigo.YaUsado -> error = "Este código ya fue utilizado."
        }
    }

    private fun cargar(app: Aplicacion) {
        val inst = CatalogoInstrumentos.buscar(app.instrumentoId)
        instrumento = inst
        aplicacionId = app.id
        actor = "Estudiante ${app.estudiante.idEstudiante}"
        respuestas.clear()
        respuestas.putAll(app.respuestas)
        val primeraSinResponder = inst?.preguntas?.indexOfFirst { it.id !in respuestas } ?: -1
        indice = if (primeraSinResponder >= 0) primeraSinResponder else 0
    }

    fun comenzar() {
        aplicacionId?.let { repo.iniciar(it, actor) }
    }

    fun seleccionar(preguntaId: Int, alternativa: Int) {
        val id = aplicacionId ?: return
        respuestas[preguntaId] = alternativa
        repo.guardarRespuesta(id, preguntaId, alternativa)
    }

    fun siguiente() {
        val total = instrumento?.preguntas?.size ?: return
        if (indice < total - 1) indice++
    }

    fun anterior() {
        if (indice > 0) indice--
    }

    val completo: Boolean
        get() = instrumento?.preguntas?.all { it.id in respuestas } == true

    fun finalizar(onOk: () -> Unit) {
        val inst = instrumento ?: return
        val id = aplicacionId ?: return
        if (!completo) return
        repo.finalizar(id, inst.calcularPuntaje(respuestas.toMap()), actor)
        onOk()
    }

    fun reiniciar() {
        codigo = ""
        error = null
        instrumento = null
        aplicacionId = null
        indice = 0
        respuestas.clear()
    }
}
