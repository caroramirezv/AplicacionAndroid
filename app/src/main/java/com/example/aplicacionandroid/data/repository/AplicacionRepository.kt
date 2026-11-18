package com.example.aplicacionandroid.data.repository

import com.example.aplicacionandroid.data.remote.AccionRequest
import com.example.aplicacionandroid.data.remote.CodigoRequest
import com.example.aplicacionandroid.data.remote.CrearAplicacionRequest
import com.example.aplicacionandroid.data.remote.FinalizarRequest
import com.example.aplicacionandroid.data.remote.IdRequest
import com.example.aplicacionandroid.data.remote.ObservacionRequest
import com.example.aplicacionandroid.data.remote.RespuestaRequest
import com.example.aplicacionandroid.data.remote.SupabaseApi
import com.example.aplicacionandroid.data.remote.SupabaseClient
import com.example.aplicacionandroid.data.remote.aModelo
import com.example.aplicacionandroid.data.remote.estadoAplicacion
import com.example.aplicacionandroid.data.remote.isoAMillis
import com.example.aplicacionandroid.model.Aplicacion
import com.example.aplicacionandroid.model.EstadoAplicacion
import com.example.aplicacionandroid.model.Estudiante
import com.example.aplicacionandroid.model.Instrumento
import com.example.aplicacionandroid.model.RegistroActividad
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

sealed interface ResultadoCodigo {
    data class Valido(val aplicacion: Aplicacion, val instrumento: Instrumento) : ResultadoCodigo
    data object NoExiste : ResultadoCodigo
    data object Expirado : ResultadoCodigo
    data object YaUsado : ResultadoCodigo
}

private const val SELECT_INSTRUMENTO = "*,preguntas(numero,texto,alternativas,correcta)"
private const val SELECT_APLICACION = "*,respuestas(pregunta_numero,alternativa)"

class AplicacionRepository private constructor(private val api: SupabaseApi) {

    private val _aplicaciones = MutableStateFlow<List<Aplicacion>>(emptyList())
    val aplicaciones: StateFlow<List<Aplicacion>> = _aplicaciones.asStateFlow()

    private val _registro = MutableStateFlow<List<RegistroActividad>>(emptyList())
    val registro: StateFlow<List<RegistroActividad>> = _registro.asStateFlow()

    private val _instrumentos = MutableStateFlow<List<Instrumento>>(emptyList())
    val instrumentos: StateFlow<List<Instrumento>> = _instrumentos.asStateFlow()

    fun obtener(id: Int): Aplicacion? = _aplicaciones.value.firstOrNull { it.id == id }

    fun buscarInstrumento(id: String): Instrumento? = _instrumentos.value.firstOrNull { it.id == id }

    fun limpiarCache() {
        _aplicaciones.value = emptyList()
        _registro.value = emptyList()
        _instrumentos.value = emptyList()
    }

    private fun modificar(id: Int, cambio: (Aplicacion) -> Aplicacion) {
        _aplicaciones.update { lista -> lista.map { if (it.id == id) cambio(it) else it } }
    }

    suspend fun cargarInstrumentos(): Result<Unit> = llamar(api) {
        _instrumentos.value = api.instrumentos(SELECT_INSTRUMENTO, "orden.asc", "numero.asc").map { it.aModelo() }
    }

    suspend fun cargarAplicaciones(): Result<Unit> = llamar(api) {
        _aplicaciones.value = api.aplicaciones(SELECT_APLICACION, "fecha_creacion.desc").map { it.aModelo() }
    }

    suspend fun cargarRegistro(): Result<Unit> = llamar(api) {
        _registro.value = api.registro("fecha,usuario,accion", "fecha.desc", 100)
            .map { RegistroActividad(isoAMillis(it.fecha), it.usuario, it.accion) }
    }

    suspend fun registrar(accion: String): Result<Unit> = llamar(api) {
        api.registrarActividad(AccionRequest(accion))
    }

    suspend fun crear(estudiante: Estudiante, instrumento: Instrumento): Result<Aplicacion> = llamar(api) {
        val nueva = api.crearAplicacion(
            CrearAplicacionRequest(estudiante.idEstudiante, estudiante.curso, instrumento.id)
        ).first().aModelo()
        _aplicaciones.update { listOf(nueva) + it }
        nueva
    }

    suspend fun regenerarCodigo(id: Int): Result<Unit> = llamar(api) {
        val fila = api.regenerarCodigo(IdRequest(id.toLong())).first()
        modificar(id) { it.copy(codigo = fila.codigo, codigoGeneradoEn = isoAMillis(fila.codigoGeneradoEn)) }
    }

    suspend fun guardarObservacion(id: Int, texto: String): Result<Unit> = llamar(api) {
        api.guardarObservacion(ObservacionRequest(id.toLong(), texto))
        modificar(id) { it.copy(observacion = texto.trim()) }
    }

    suspend fun marcarRevisada(id: Int): Result<Unit> = llamar(api) {
        api.marcarRevisada(IdRequest(id.toLong()))
        modificar(id) { it.copy(estado = EstadoAplicacion.REVISADA) }
    }

    suspend fun validarCodigo(codigo: String): Result<ResultadoCodigo> = llamar(api) {
        val respuesta = api.validarCodigo(CodigoRequest(codigo.trim()))
        val datos = respuesta.aplicacion
        val instrumento = respuesta.instrumento
        when {
            respuesta.resultado == "EXPIRADO" -> ResultadoCodigo.Expirado
            respuesta.resultado == "YA_USADO" -> ResultadoCodigo.YaUsado
            respuesta.resultado != "VALIDO" || datos == null || instrumento == null -> ResultadoCodigo.NoExiste
            else -> ResultadoCodigo.Valido(
                aplicacion = Aplicacion(
                    id = datos.id.toInt(),
                    estudiante = Estudiante(datos.idEstudiante, datos.curso),
                    instrumentoId = instrumento.id,
                    codigo = codigo.trim().uppercase(),
                    codigoGeneradoEn = 0L,
                    estado = estadoAplicacion(datos.estado),
                    fechaCreacion = 0L,
                    respuestas = respuesta.respuestas.orEmpty().mapKeys { it.key.toInt() },
                    creadaPor = ""
                ),
                instrumento = instrumento.aModelo()
            )
        }
    }

    suspend fun iniciar(codigo: String): Result<Unit> = llamar(api) {
        api.iniciar(CodigoRequest(codigo))
    }

    suspend fun guardarRespuesta(codigo: String, preguntaId: Int, alternativa: Int): Result<Unit> = llamar(api) {
        api.guardarRespuesta(RespuestaRequest(codigo, preguntaId, alternativa))
    }

    suspend fun finalizar(codigo: String, respuestas: Map<Int, Int>): Result<Unit> = llamar(api) {
        api.finalizar(FinalizarRequest(codigo, respuestas.mapKeys { it.key.toString() }))
    }

    companion object {
        const val LONGITUD_CODIGO = 6
        const val VIGENCIA_CODIGO_MS = 2L * 60 * 60 * 1000

        val instancia: AplicacionRepository by lazy { AplicacionRepository(SupabaseClient.api) }
    }
}
