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
fun parseNull(s: String): String {
    logger.info("Formateando campos vacíos.")
    var aux = ""
    if (s.isEmpty()) {
        aux = "null"
    }
    return aux
}

/**
 * Función que recibe un campo y parsea el valor, cambiando el carácter "," a "." y lo castea a Double.
 *
 * @param s El valor en tipo String del campo a parsear.
 *
 * @return El valor de dicho campo ya formateado, cambiando "," por ".".
 */
fun parseDouble(s: String): Double {
    logger.info("Formateando decimales.")
    return s.replace(",", ".").toDouble()
}