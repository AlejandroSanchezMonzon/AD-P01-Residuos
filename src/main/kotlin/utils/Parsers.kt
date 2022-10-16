package utils

import mu.KotlinLogging

private val logger = KotlinLogging.logger{}

fun parseNull(s: String): String {
    logger.info("Formateando campos vacíos.")
    var aux = ""
    if (s.isEmpty()) {
        aux = "null"
    }
    return aux
}

fun parseDouble(s: String): Double {
    logger.info("Formateando decimales.")
    return s.replace(",", ".").toDouble()
}