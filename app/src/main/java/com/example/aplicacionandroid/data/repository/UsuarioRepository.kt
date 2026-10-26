package com.example.aplicacionandroid.data.repository

import com.example.aplicacionandroid.data.local.UsuarioDao
import com.example.aplicacionandroid.data.local.UsuarioEntity

class UsuarioRepository(private val dao: UsuarioDao) {
    suspend fun iniciarSesion(correo: String, clave: String): UsuarioEntity? =
        dao.login(correo.trim().lowercase(), clave)
}
