package com.example.aplicacionandroid.model

data class Aplicacion(
    val id: Int,
    val estudiante: Estudiante,
    val instrumentoId: String,
    val codigo: String,
    val codigoGeneradoEn: Long,
    val estado: EstadoAplicacion = EstadoAplicacion.PENDIENTE,
    val fechaCreacion: Long,
    val fechaInicio: Long? = null,
    val fechaFin: Long? = null,
    /** idPregunta -> índice de la alternativa elegida */
    val respuestas: Map<Int, Int> = emptyMap(),
    val puntaje: Int? = null,
    val observacion: String = "",
    val creadaPor: String
) {
    val codigoActivo: Boolean
        get() = estado == EstadoAplicacion.PENDIENTE || estado == EstadoAplicacion.EN_DESARROLLO
}

data class RegistroActividad(
    val fecha: Long,
    val usuario: String,
    val accion: String
)
