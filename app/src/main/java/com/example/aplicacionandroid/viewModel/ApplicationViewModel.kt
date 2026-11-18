package com.example.aplicacionandroid.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplicacionandroid.data.remote.Sesion
import com.example.aplicacionandroid.data.repository.AplicacionRepository
import com.example.aplicacionandroid.data.repository.mensajeDeError
import com.example.aplicacionandroid.model.Aplicacion
import com.example.aplicacionandroid.model.Estudiante
import kotlinx.coroutines.launch

class ApplicationViewModel : ViewModel() {

    private val repo = AplicacionRepository.instancia

    val aplicaciones = repo.aplicaciones
    val registro = repo.registro
    val instrumentos = repo.instrumentos

    var usuario by mutableStateOf("Psicólogo PIE")
        private set
    var cargando by mutableStateOf(false)
        private set
    var error by mutableStateOf<String?>(null)
        private set

    fun iniciarSesion(nombre: String) {
        usuario = nombre
        viewModelScope.launch {
            repo.registrar("Inició sesión")
            cargarTodo()
        }
    }

    fun cerrarSesion() {
        viewModelScope.launch {
            repo.registrar("Cerró sesión")
            Sesion.cerrar()
            repo.limpiarCache()
        }
    }

    fun recargar() {
        viewModelScope.launch { cargarTodo() }
    }

    fun recargarAplicaciones() {
        viewModelScope.launch {
            repo.cargarAplicaciones().onFailure { error = mensajeDeError(it) }
        }
    }

    fun recargarRegistro() {
        viewModelScope.launch {
            repo.cargarRegistro().onFailure { error = mensajeDeError(it) }
        }
    }

    private suspend fun cargarTodo() {
        cargando = true
        val resultados = listOf(
            repo.cargarInstrumentos(),
            repo.cargarAplicaciones(),
            repo.cargarRegistro()
        )
        cargando = false
        resultados.firstOrNull { it.isFailure }?.exceptionOrNull()?.let { error = mensajeDeError(it) }
    }

    fun limpiarError() {
        error = null
    }

    fun crearAplicacion(
        idEstudiante: String,
        curso: String,
        instrumentoId: String,
        onResultado: (Aplicacion?, String?) -> Unit
    ) {
        val instrumento = repo.buscarInstrumento(instrumentoId)?.takeIf { it.asignable }
        if (instrumento == null) {
            onResultado(null, null)
            return
        }
        viewModelScope.launch {
            val estudiante = Estudiante(idEstudiante.trim().uppercase(), curso.trim())
            val resultado = repo.crear(estudiante, instrumento)
            onResultado(resultado.getOrNull(), resultado.exceptionOrNull()?.let { mensajeDeError(it) })
        }
    }

    fun registrarConsulta(id: Int) {
        viewModelScope.launch { repo.registrar("Consultó la aplicación #$id") }
    }

    fun regenerarCodigo(id: Int) {
        viewModelScope.launch {
            repo.regenerarCodigo(id).onFailure { error = mensajeDeError(it) }
        }
    }

    fun guardarObservacion(id: Int, texto: String, onTerminado: (Boolean) -> Unit = {}) {
        viewModelScope.launch {
            val resultado = repo.guardarObservacion(id, texto)
            resultado.onFailure { error = mensajeDeError(it) }
            onTerminado(resultado.isSuccess)
        }
    }

    fun marcarRevisada(id: Int) {
        viewModelScope.launch {
            repo.marcarRevisada(id).onFailure { error = mensajeDeError(it) }
        }
    }

    fun obtener(id: Int): Aplicacion? = repo.obtener(id)
}
