package utils

import mu.KotlinLogging

private val logger = KotlinLogging.logger{}

fun parseNull(cadena: String): String {
    logger.info("Formateando campos vacíos.")
    var aux = ""
    if (cadena.isEmpty()) {
        aux = "null"
    }
    return aux
}

fun parseDouble(cadena: String): Double {
    logger.info("Formateando decimales.")
    return cadena.replace(",", ".").toDouble()
}