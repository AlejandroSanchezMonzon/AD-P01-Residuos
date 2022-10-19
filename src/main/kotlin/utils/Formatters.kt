/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */

package utils

import mu.KotlinLogging
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

private val logger = KotlinLogging.logger{}

/**
 * Función de formateo de fechas. Recoge la fecha por parámetors en formato LocalDateTime y la formatea al lugar que deseamos, en este caso, ESPAÑA.
 *
 * @param date Valor de tipo LocalDateTime el cual vamos a formatear.
 *
 * @return Un string resultado de formatear el LocalDateTime que pasamos por parámetros.
 */
fun dateFormatter(date: LocalDateTime): String {
    logger.info("Formateando fecha a la zona horaria de España.")
    return date.format(
        DateTimeFormatter
            .ofLocalizedDate(FormatStyle.FULL)
            .withLocale(Locale("es", "ES")))
}