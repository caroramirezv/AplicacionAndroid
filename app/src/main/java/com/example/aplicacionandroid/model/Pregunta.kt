package com.example.aplicacionandroid.model

data class Pregunta(
    val id: Int,
    val texto: String,
    val alternativas: List<String>,
    val correcta: Int? = null
)
