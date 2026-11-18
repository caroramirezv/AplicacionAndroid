package com.example.aplicacionandroid.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplicacionandroid.data.repository.UsuarioRepository
import com.example.aplicacionandroid.data.repository.mensajeDeError
import com.example.aplicacionandroid.model.PIE
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val repo = UsuarioRepository()

    var correo by mutableStateOf("")
    var contrasena by mutableStateOf("")
    var errorMessage by mutableStateOf<String?>(null)
    var cargando by mutableStateOf(false)
        private set

    fun onLoginClicked(onSuccess: (PIE) -> Unit) {
        if (cargando) return
        if (correo.isBlank() || contrasena.isBlank()) {
            errorMessage = "Completa todos los campos"
            return
        }
        viewModelScope.launch {
            cargando = true
            val resultado = repo.iniciarSesion(correo, contrasena)
            cargando = false
            resultado
                .onSuccess { psicologo ->
                    if (psicologo != null) {
                        limpiar()
                        onSuccess(psicologo)
                    } else {
                        errorMessage = "Credenciales incorrectas"
                    }
                }
                .onFailure { errorMessage = mensajeDeError(it) }
        }
    }

    fun limpiar() {
        correo = ""
        contrasena = ""
        errorMessage = null
    }
}
