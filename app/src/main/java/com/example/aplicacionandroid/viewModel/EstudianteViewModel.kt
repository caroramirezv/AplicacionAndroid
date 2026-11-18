package com.example.aplicacionandroid.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplicacionandroid.data.repository.AplicacionRepository
import com.example.aplicacionandroid.data.repository.ResultadoCodigo
import com.example.aplicacionandroid.data.repository.mensajeDeError
import com.example.aplicacionandroid.model.Instrumento
import kotlinx.coroutines.launch

class EstudianteViewModel : ViewModel() {

    private val repo = AplicacionRepository.instancia

    var codigo by mutableStateOf("")
    var error by mutableStateOf<String?>(null)

    var cargando by mutableStateOf(false)
        private set
    var instrumento by mutableStateOf<Instrumento?>(null)
        private set
    var aplicacionId by mutableStateOf<Int?>(null)
        private set
    var indice by mutableIntStateOf(0)
        private set
    val respuestas = mutableStateMapOf<Int, Int>()

    private var codigoActivo = ""

    fun ingresar(onOk: () -> Unit) {
        if (cargando) return
        if (codigo.length < AplicacionRepository.LONGITUD_CODIGO) {
            error = "Ingresa el código de ${AplicacionRepository.LONGITUD_CODIGO} caracteres"
            return
        }
        viewModelScope.launch {
            cargando = true
            val resultado = repo.validarCodigo(codigo)
            cargando = false
            resultado
                .onSuccess { validacion ->
                    when (validacion) {
                        is ResultadoCodigo.Valido -> {
                            cargar(validacion)
                            error = null
                            onOk()
                        }
                        ResultadoCodigo.NoExiste -> error = "Código no válido"
                        ResultadoCodigo.Expirado -> error = "El código venció. Pide uno nuevo al psicólogo."
                        ResultadoCodigo.YaUsado -> error = "Este código ya fue utilizado."
                    }
                }
                .onFailure { error = mensajeDeError(it) }
        }
    }

    private fun cargar(validacion: ResultadoCodigo.Valido) {
        val inst = validacion.instrumento
        instrumento = inst
        aplicacionId = validacion.aplicacion.id
        codigoActivo = codigo.trim().uppercase()
        respuestas.clear()
        respuestas.putAll(validacion.aplicacion.respuestas)
        val primeraSinResponder = inst.preguntas.indexOfFirst { it.id !in respuestas }
        indice = if (primeraSinResponder >= 0) primeraSinResponder else 0
    }

    fun comenzar() {
        if (codigoActivo.isEmpty()) return
        viewModelScope.launch { repo.iniciar(codigoActivo) }
    }

    fun seleccionar(preguntaId: Int, alternativa: Int) {
        if (codigoActivo.isEmpty()) return
        respuestas[preguntaId] = alternativa
        viewModelScope.launch { repo.guardarRespuesta(codigoActivo, preguntaId, alternativa) }
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
        if (cargando || !completo || codigoActivo.isEmpty()) return
        viewModelScope.launch {
            cargando = true
            error = null
            val resultado = repo.finalizar(codigoActivo, respuestas.toMap())
            cargando = false
            resultado
                .onSuccess { onOk() }
                .onFailure { error = mensajeDeError(it) }
        }
    }

    fun reiniciar() {
        codigo = ""
        error = null
        cargando = false
        instrumento = null
        aplicacionId = null
        codigoActivo = ""
        indice = 0
        respuestas.clear()
    }
}
