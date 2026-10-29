package com.example.aplicacionandroid.model

enum class EstadoInstrumento(val etiqueta: String) {
    DISPONIBLE("Disponible"),
    NO_DISPONIBLE("No disponible"),
    PENDIENTE_AUTORIZACION("Pendiente de autorización")
}

enum class TipoInstrumento(val etiqueta: String) {
    CUESTIONARIO_EDUCATIVO("Cuestionario educativo"),
    PRUEBA_PSICOMETRICA("Prueba psicométrica (simulada)")
}

data class Instrumento(
    val id: String,
    val nombre: String,
    val tipo: TipoInstrumento,
    val descripcion: String,
    val rangoEdad: String,
    val instrucciones: String,
    val minutosEstimados: Int,
    val estado: EstadoInstrumento,
    val calculaPuntaje: Boolean,
    val preguntas: List<Pregunta>
) {
    val asignable: Boolean get() = estado == EstadoInstrumento.DISPONIBLE && preguntas.isNotEmpty()

    fun calcularPuntaje(respuestas: Map<Int, Int>): Int? {
        if (!calculaPuntaje) return null
        var total = 0
        for (p in preguntas) {
            val r = respuestas[p.id] ?: continue
            total += if (p.correcta != null) {
                if (r == p.correcta) 1 else 0
            } else {
                r
            }
        }
        return total
    }

    fun puntajeMaximo(): Int? {
        if (!calculaPuntaje) return null
        var max = 0
        for (p in preguntas) {
            max += if (p.correcta != null) 1 else p.alternativas.size - 1
        }
        return max
    }
}
