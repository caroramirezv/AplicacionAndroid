package com.example.aplicacionandroid.data.repository

import com.example.aplicacionandroid.data.remote.AuthLoginRequest
import com.example.aplicacionandroid.data.remote.AuthResponse
import com.example.aplicacionandroid.data.remote.Sesion
import com.example.aplicacionandroid.data.remote.SupabaseApi
import com.example.aplicacionandroid.data.remote.SupabaseClient
import com.example.aplicacionandroid.model.PIE
import retrofit2.HttpException

class UsuarioRepository(private val api: SupabaseApi = SupabaseClient.api) {

    suspend fun iniciarSesion(correo: String, clave: String): Result<PIE?> = llamar(api) {
        val email = correo.trim().lowercase()
        val auth = autenticar(email, clave)
        if (auth == null) {
            null
        } else {
            Sesion.guardar(auth.accessToken, auth.refreshToken)
            val perfil = try {
                auth.user?.id?.let { api.perfil("eq.$it", "nombre").firstOrNull() }
            } catch (e: Exception) {
                Sesion.cerrar()
                throw e
            }
            if (perfil == null) {
                Sesion.cerrar()
                throw SinPermisoException()
            }
            PIE(nombre = perfil.nombre, correo = email)
        }
    }

    private suspend fun autenticar(email: String, clave: String): AuthResponse? =
        try {
            api.loginAuth("password", AuthLoginRequest(email, clave))
        } catch (e: HttpException) {
            if (e.code() == 400 || e.code() == 401 || e.code() == 422) null else throw e
        }
}
