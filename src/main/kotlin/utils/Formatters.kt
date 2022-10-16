package utils

import mu.KotlinLogging
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

private val logger = KotlinLogging.logger{}

fun dateFormatter(date: LocalDateTime):String{
    logger.info("Formateando fecha a la zona horaria de España.")
    return date.format(
        DateTimeFormatter.ofLocalizedDate
        (FormatStyle.FULL).withLocale(Locale("es", "ES")))
}