package com.example.aplicacionandroid.data.repository

import com.example.aplicacionandroid.model.Aplicacion
import com.example.aplicacionandroid.model.EstadoAplicacion
import com.example.aplicacionandroid.model.Estudiante
import com.example.aplicacionandroid.model.Instrumento
import com.example.aplicacionandroid.model.RegistroActividad
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.concurrent.atomic.AtomicInteger

sealed interface ResultadoCodigo {
    data class Valido(val aplicacion: Aplicacion) : ResultadoCodigo
    data object NoExiste : ResultadoCodigo
    data object Expirado : ResultadoCodigo
    data object YaUsado : ResultadoCodigo
}

/**
 * Almacén en memoria compartido por el perfil psicólogo y el perfil estudiante.
 * Room se reserva solo para el login, por lo que estos datos duran mientras la app esté abierta.
 */
class AplicacionRepository private constructor() {

    private val _aplicaciones = MutableStateFlow<List<Aplicacion>>(emptyList())
    val aplicaciones: StateFlow<List<Aplicacion>> = _aplicaciones.asStateFlow()

    private val _registro = MutableStateFlow<List<RegistroActividad>>(emptyList())
    val registro: StateFlow<List<RegistroActividad>> = _registro.asStateFlow()

    private val contadorId = AtomicInteger(0)

    fun registrar(usuario: String, accion: String) {
        val nuevo = RegistroActividad(System.currentTimeMillis(), usuario, accion)
        _registro.update { listOf(nuevo) + it }
    }

    fun obtener(id: Int): Aplicacion? = _aplicaciones.value.firstOrNull { it.id == id }

    private fun modificar(id: Int, cambio: (Aplicacion) -> Aplicacion) {
        _aplicaciones.update { lista -> lista.map { if (it.id == id) cambio(it) else it } }
    }

    private fun generarCodigoUnico(): String {
        val existentes = _aplicaciones.value.map { it.codigo }.toSet()
        while (true) {
            val codigo = (1..LONGITUD_CODIGO).map { ALFABETO.random() }.joinToString("")
            if (codigo !in existentes) return codigo
        }
    }

    // ---------- Psicólogo ----------

    fun crear(estudiante: Estudiante, instrumento: Instrumento, usuario: String): Aplicacion {
        val ahora = System.currentTimeMillis()
        val app = Aplicacion(
            id = contadorId.incrementAndGet(),
            estudiante = estudiante,
            instrumentoId = instrumento.id,
            codigo = generarCodigoUnico(),
            codigoGeneradoEn = ahora,
            fechaCreacion = ahora,
            creadaPor = usuario
        )
        _aplicaciones.update { listOf(app) + it }
        registrar(usuario, "Creó la aplicación #${app.id} (${instrumento.nombre}) para ${estudiante.idEstudiante}")
        return app
    }

    fun regenerarCodigo(id: Int, usuario: String) {
        val codigo = generarCodigoUnico()
        modificar(id) { it.copy(codigo = codigo, codigoGeneradoEn = System.currentTimeMillis()) }
        registrar(usuario, "Generó un nuevo código para la aplicación #$id")
    }

    fun guardarObservacion(id: Int, texto: String, usuario: String) {
        modificar(id) { it.copy(observacion = texto.trim()) }
        registrar(usuario, "Guardó una observación en la aplicación #$id")
    }

    fun marcarRevisada(id: Int, usuario: String) {
        modificar(id) { if (it.estado == EstadoAplicacion.FINALIZADA) it.copy(estado = EstadoAplicacion.REVISADA) else it }
        registrar(usuario, "Marcó como revisada la aplicación #$id")
    }

    // ---------- Estudiante ----------

    fun validarCodigo(codigo: String): ResultadoCodigo {
        val app = _aplicaciones.value.firstOrNull { it.codigo.equals(codigo.trim(), ignoreCase = true) }
            ?: return ResultadoCodigo.NoExiste
        if (!app.codigoActivo) return ResultadoCodigo.YaUsado
        if (System.currentTimeMillis() - app.codigoGeneradoEn > VIGENCIA_CODIGO_MS) return ResultadoCodigo.Expirado
        return ResultadoCodigo.Valido(app)
    }

    fun iniciar(id: Int, usuario: String) {
        val app = obtener(id) ?: return
        if (app.estado != EstadoAplicacion.PENDIENTE) return
        modificar(id) { it.copy(estado = EstadoAplicacion.EN_DESARROLLO, fechaInicio = System.currentTimeMillis()) }
        registrar(usuario, "Inició la aplicación #$id")
    }

    fun guardarRespuesta(id: Int, preguntaId: Int, alternativa: Int) {
        modificar(id) { it.copy(respuestas = it.respuestas + (preguntaId to alternativa)) }
    }

    fun finalizar(id: Int, puntaje: Int?, usuario: String) {
        modificar(id) {
            it.copy(estado = EstadoAplicacion.FINALIZADA, fechaFin = System.currentTimeMillis(), puntaje = puntaje)
        }
        registrar(usuario, "Finalizó la aplicación #$id")
    }

    companion object {
        const val LONGITUD_CODIGO = 6
        const val VIGENCIA_CODIGO_MS = 2L * 60 * 60 * 1000 // 2 horas
        // Sin caracteres ambiguos (0/O, 1/I)
        private const val ALFABETO = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789"

        val instancia: AplicacionRepository by lazy { AplicacionRepository() }
    }
}
