package models

import mu.KotlinLogging
import java.time.LocalDateTime
import java.util.*

private val logger = KotlinLogging.logger{}

//TODO: encerrarlas en una lista
data class Bitacora(
    val id: UUID,
    val instante: LocalDateTime,
    val tipo: TipoOpcion,
    val exito: Boolean,
    val tiempoEjecucion: Long
)

enum class TipoOpcion {
    PARSER,
    RESUMEN_GLOBAL,
    RESUMEN_CIUDAD;

    companion object {
        fun from(opcion: String): TipoOpcion{
            return when (opcion.uppercase()) {
                "PARSER" -> PARSER
                "RESUMEN GLOBAL" -> RESUMEN_GLOBAL
                "RESUMEN CIUDAD" -> RESUMEN_CIUDAD
                else -> throw IllegalArgumentException("Opción no reconocida.")
            }
        }
    }
}

