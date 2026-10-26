package com.example.aplicacionandroid.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplicacionandroid.data.local.AppDatabase
import com.example.aplicacionandroid.data.repository.UsuarioRepository
import com.example.aplicacionandroid.model.PIE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Todo lo que la pantalla de login necesita saber, en un solo objeto.
data class LoginUiState(
    val cargando: Boolean = false,
    val error: String? = null,
    val psicologo: PIE? = null      // distinto de null = sesión iniciada
)

class LoginViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = UsuarioRepository(AppDatabase.get(app).usuarioDao())

    private val _estado = MutableStateFlow(LoginUiState())
    val estado: StateFlow<LoginUiState> = _estado.asStateFlow()

    fun ingresar(correo: String, clave: String) {
        if (_estado.value.cargando) return                    // evita doble toque

        if (correo.isBlank() || clave.isBlank()) {
            _estado.update { it.copy(error = "Ingresa tu usuario y contraseña") }
            return
        }

        viewModelScope.launch {
            _estado.update { it.copy(cargando = true, error = null) }
            try {
                val usuario = repo.iniciarSesion(correo, clave)
                _estado.update {
                    if (usuario != null) {
                        it.copy(cargando = false, psicologo = PIE(usuario.correo, usuario.nombre))
                    } else {
                        it.copy(cargando = false, error = "Usuario o contraseña incorrectos")
                    }
                }
            } catch (e: Exception) {
                _estado.update { it.copy(cargando = false, error = "No se pudo acceder a la base de datos") }
            }
        }
    }

    fun limpiarError() {
        _estado.update { it.copy(error = null) }
    }
}
