package com.example.aplicacionandroid.data

import com.example.aplicacionandroid.model.EstadoInstrumento
import com.example.aplicacionandroid.model.Instrumento
import com.example.aplicacionandroid.model.Pregunta
import com.example.aplicacionandroid.model.TipoInstrumento

object CatalogoInstrumentos {

    private val frecuencia = listOf("Nunca", "A veces", "Casi siempre", "Siempre")
    private val acuerdo = listOf(
        "Muy en desacuerdo", "En desacuerdo", "Ni de acuerdo ni en desacuerdo",
        "De acuerdo", "Muy de acuerdo"
    )

    private val habitosEstudio = Instrumento(
        id = "habitos_estudio",
        nombre = "Cuestionario de hábitos de estudio",
        tipo = TipoInstrumento.CUESTIONARIO_EDUCATIVO,
        descripcion = "Cuestionario educativo propio, creado para demostrar el funcionamiento de la aplicación.",
        rangoEdad = "3.º a 8.º básico",
        instrucciones = "Lee cada frase y elige la alternativa que mejor describe lo que haces habitualmente. " +
            "No hay respuestas buenas ni malas.",
        minutosEstimados = 5,
        estado = EstadoInstrumento.DISPONIBLE,
        calculaPuntaje = true,
        preguntas = listOf(
            Pregunta(1, "Reviso mis tareas antes de entregarlas.", frecuencia),
            Pregunta(2, "Estudio en un lugar tranquilo.", frecuencia),
            Pregunta(3, "Pido ayuda cuando no entiendo algo.", frecuencia),
            Pregunta(4, "Preparo mis útiles antes de comenzar a trabajar.", frecuencia),
            Pregunta(5, "Planifico el tiempo que necesito para estudiar.", frecuencia),
            Pregunta(6, "Termino mis trabajos dentro del tiempo dado.", frecuencia)
        )
    )

    private val comprensionLectora = Instrumento(
        id = "comprension_lectora",
        nombre = "Mini cuestionario de comprensión lectora (simulado)",
        tipo = TipoInstrumento.CUESTIONARIO_EDUCATIVO,
        descripcion = "Preguntas con una alternativa correcta, creadas para probar el cálculo automático de puntaje.",
        rangoEdad = "1.º a 4.º básico",
        instrucciones = "Lee con calma cada texto corto y elige la alternativa correcta.",
        minutosEstimados = 3,
        estado = EstadoInstrumento.DISPONIBLE,
        calculaPuntaje = true,
        preguntas = listOf(
            Pregunta(
                1, "Sofía plantó una semilla de girasol y la regó todos los días. ¿Qué hizo Sofía todos los días?",
                listOf("Cortó el brote", "Regó la semilla", "Cambió la maceta", "Guardó la semilla"), correcta = 1
            ),
            Pregunta(
                2, "Pedro llegó tarde porque perdió el bus. ¿Por qué llegó tarde Pedro?",
                listOf("Se quedó dormido", "Olvidó su mochila", "Perdió el bus", "Estaba enfermo"), correcta = 2
            ),
            Pregunta(
                3, "El gato duerme sobre el sillón cuando hace frío. ¿Cuándo duerme el gato sobre el sillón?",
                listOf("Cuando hace frío", "Cuando llueve", "Cuando tiene hambre", "Cuando es de noche"), correcta = 0
            ),
            Pregunta(
                4, "Ana guardó sus lápices en un estuche rojo. ¿De qué color es el estuche?",
                listOf("Azul", "Verde", "Rojo", "Amarillo"), correcta = 2
            )
        )
    )

    private val climaEscolar = Instrumento(
        id = "clima_escolar_sim",
        nombre = "Escala de clima escolar (simulada)",
        tipo = TipoInstrumento.PRUEBA_PSICOMETRICA,
        descripcion = "Estructura simulada de una escala tipo Likert con ítems ficticios, sin valor clínico.",
        rangoEdad = "5.º a 8.º básico",
        instrucciones = "Indica qué tan de acuerdo estás con cada afirmación. Responde con sinceridad.",
        minutosEstimados = 5,
        estado = EstadoInstrumento.DISPONIBLE,
        calculaPuntaje = true,
        preguntas = listOf(
            Pregunta(1, "Me siento acompañado por mis compañeros en la sala.", acuerdo),
            Pregunta(2, "Siento que puedo preguntar mis dudas en clase.", acuerdo),
            Pregunta(3, "Me gusta venir a la escuela.", acuerdo),
            Pregunta(4, "Me siento tranquilo durante los recreos.", acuerdo),
            Pregunta(5, "Mis profesores me escuchan cuando necesito algo.", acuerdo),
            Pregunta(6, "Siento que mi opinión es considerada en el curso.", acuerdo)
        )
    )

    private val estandarizada = Instrumento(
        id = "estandarizada_pendiente",
        nombre = "Prueba estandarizada (no incluida)",
        tipo = TipoInstrumento.PRUEBA_PSICOMETRICA,
        descripcion = "Requiere autorización institucional y licencia de uso, reproducción, digitalización y puntuación.",
        rangoEdad = "Por definir",
        instrucciones = "",
        minutosEstimados = 0,
        estado = EstadoInstrumento.PENDIENTE_AUTORIZACION,
        calculaPuntaje = false,
        preguntas = emptyList()
    )

    val todos: List<Instrumento> = listOf(habitosEstudio, comprensionLectora, climaEscolar, estandarizada)

    fun buscar(id: String): Instrumento? = todos.firstOrNull { it.id == id }
}
