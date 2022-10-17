/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */

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
        /**
         * Función que parsea los Enums del objeto Bitacora. Indicando su equivalencia al valor de tipo String.
         *
         * @param opcion El enum en formato String equivalente al valor de la clase Enum.
         *
         * @throws IllegalArgumentException Excepción que el método lanzará si el String que trata de parsear no eixste, o el tipo no existe.
         *
         * @return TipoOpcion, un enum que variará dependiendo del valor de dicha variable.
         */
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

