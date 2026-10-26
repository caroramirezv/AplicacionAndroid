package com.example.aplicacionandroid.data.local

import androidx.room.Dao
import androidx.room.Query

@Dao
interface UsuarioDao {
    @Query("SELECT * FROM usuarios WHERE correo = :correo AND clave = :clave LIMIT 1")
    suspend fun login(correo: String, clave: String): UsuarioEntity?
}
