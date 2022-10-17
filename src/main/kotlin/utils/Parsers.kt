/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */

package utils

import mu.KotlinLogging

private val logger = KotlinLogging.logger{}

/**
 * Función que recibe el valor de tipo String de un campo, analiza si este es un campo vacío, y si lo es, lo parsea a un String con valor "null".
 *
 * @param s Valor de tipo String del campo determinado.
 *
 * @return aux, un valor auxiliar que devuvle "null" si el campo examinado está vacío (en blanco).
 */
 
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