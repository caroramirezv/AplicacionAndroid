package com.example.aplicacionandroid.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.aplicacionandroid.data.CatalogoInstrumentos
import com.example.aplicacionandroid.data.repository.AplicacionRepository
import com.example.aplicacionandroid.model.Aplicacion
import com.example.aplicacionandroid.model.Estudiante
class ApplicationViewModel : ViewModel() {

    private val repo = AplicacionRepository.instancia

    val aplicaciones = repo.aplicaciones
    val registro = repo.registro
    val instrumentos = CatalogoInstrumentos.todos

    var usuario by mutableStateOf("Psicólogo PIE")
        private set

    fun iniciarSesion(nombre: String) {
        usuario = nombre
        repo.registrar(usuario, "Inició sesión")
    }

    fun cerrarSesion() {
        repo.registrar(usuario, "Cerró sesión")
    }

    fun crearAplicacion(idEstudiante: String, curso: String, instrumentoId: String): Aplicacion? {
        val instrumento = CatalogoInstrumentos.buscar(instrumentoId)?.takeIf { it.asignable } ?: return null
        val estudiante = Estudiante(idEstudiante.trim().uppercase(), curso.trim())
        return repo.crear(estudiante, instrumento, usuario)
    }

    fun registrarConsulta(id: Int) {
        repo.registrar(usuario, "Consultó la aplicación #$id")
    }

    fun regenerarCodigo(id: Int) = repo.regenerarCodigo(id, usuario)
    fun guardarObservacion(id: Int, texto: String) = repo.guardarObservacion(id, texto, usuario)
    fun marcarRevisada(id: Int) = repo.marcarRevisada(id, usuario)
    fun obtener(id: Int): Aplicacion? = repo.obtener(id)
}
