package com.example.aplicacionandroid.data.repository

import com.example.aplicacionandroid.data.local.UsuarioDao
import com.example.aplicacionandroid.data.local.UsuarioEntity

class UsuarioRepository(private val dao: UsuarioDao) {

    private suspend fun asegurarCuentaDemo() {
        if (dao.buscarPorCorreo(CORREO_DEMO) == null) {
            dao.registrar(
                UsuarioEntity(
                    correo = CORREO_DEMO,
                    contrasena = CLAVE_DEMO,
                    nombre = "Ana Soto PIE",
                    rol = ROL_PSICOLOGO
                )
            )
        }
    }

    suspend fun iniciarSesion(correo: String, clave: String): UsuarioEntity? {
        asegurarCuentaDemo()
        return dao.login(correo.trim().lowercase(), clave)
    }

    companion object {
        const val ROL_PSICOLOGO = "PSICOLOGO"
        const val CORREO_DEMO = "ana.soto@mpaz.cl"
        const val CLAVE_DEMO = "ana1234"
    }
}
