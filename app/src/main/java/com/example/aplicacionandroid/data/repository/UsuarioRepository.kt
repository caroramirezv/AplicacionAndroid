package com.example.aplicacionandroid.data.repository

import com.example.aplicacionandroid.data.local.UsuarioDao
import com.example.aplicacionandroid.data.local.UsuarioEntity

class UsuarioRepository(private val dao: UsuarioDao) {

    private suspend fun asegurarCuentasDemo() {
        val usuariosDemo = listOf(
            UsuarioEntity(
                correo = CORREO_DEMO_1,
                contrasena = CLAVE_DEMO_1,
                nombre = "Ana Soto PIE",
                rol = ROL_PSICOLOGO
            ),
            UsuarioEntity(
                correo = CORREO_DEMO_2,
                contrasena = CLAVE_DEMO_2,
                nombre = "Carlos Pérez PIE",
                rol = ROL_PSICOLOGO
            )
        )

        for (usuario in usuariosDemo) {
            if (dao.buscarPorCorreo(usuario.correo) == null) {
                dao.registrar(usuario)
            }
        }
    }

    suspend fun iniciarSesion(correo: String, clave: String): UsuarioEntity? {
        asegurarCuentasDemo()
        return dao.login(correo.trim().lowercase(), clave)
    }

    companion object {
        const val ROL_PSICOLOGO = "PSICOLOGO"

        const val CORREO_DEMO_1 = "ana.soto@mpaz.cl"
        const val CLAVE_DEMO_1 = "ana1234"

        const val CORREO_DEMO_2 = "carlos.perez@mpaz.cl"
        const val CLAVE_DEMO_2 = "carlos1234"
    }
}