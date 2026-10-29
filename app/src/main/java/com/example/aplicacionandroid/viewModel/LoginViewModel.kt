package com.example.aplicacionandroid.viewModel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplicacionandroid.data.local.AppDatabase
import com.example.aplicacionandroid.data.repository.UsuarioRepository
import com.example.aplicacionandroid.model.PIE
import kotlinx.coroutines.launch

class LoginViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = UsuarioRepository(AppDatabase.getDatabase(app).usuarioDao())

    var correo by mutableStateOf("")
    var contrasena by mutableStateOf("")
    var errorMessage by mutableStateOf<String?>(null)
    var cargando by mutableStateOf(false)
        private set

    fun onLoginClicked(onSuccess: (PIE) -> Unit) {
        if (correo.isBlank() || contrasena.isBlank()) {
            errorMessage = "Completa todos los campos"
            return
        }
        viewModelScope.launch {
            cargando = true
            val usuario = repo.iniciarSesion(correo, contrasena)
            cargando = false
            if (usuario != null && usuario.rol == UsuarioRepository.ROL_PSICOLOGO) {
                limpiar()
                onSuccess(PIE(usuario.nombre, usuario.correo))
            } else {
                errorMessage = "Credenciales incorrectas"
            }
        }
    }

    fun limpiar() {
        correo = ""
        contrasena = ""
        errorMessage = null
    }
}
