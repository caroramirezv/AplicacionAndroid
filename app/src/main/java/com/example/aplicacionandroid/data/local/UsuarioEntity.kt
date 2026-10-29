package com.example.aplicacionandroid.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val correo: String,
    val contrasena: String,
    val nombre: String,
    val rol: String
)
