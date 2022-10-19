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
 * @param cadena Valor de tipo String del campo determinado.
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

/**
 * Función que recibe el valor del campo como un String y actualiza las "," a "." para poder hacer el parser a Double.
 *
 * @param cadena Valor del campo que deseamos parsear como tipo String.
 *
 * @return El valor del campo formateado y parseado a Double con el método toDouble().
 */
fun parseDouble(cadena: String): Double {
    logger.info("Formateando decimales.")
    return cadena.replace(",", ".").toDouble()
}

/**
 * Función que recibe el distrito del argumento que nosotros introducimos junto al JAR y lo parsea a la variable Distrito de Contenedores,
 * la cual no tienen tildes en sus nombres.
 *
 * @param distrito El distrito que introducimos por parámetros.
 *
 * @return aux, la variable resultado de dicho parseo (quitar las tildes).
 */
fun parseDistrito(distrito: String): String {
    var aux = ""

    if (distrito.equals("Chamberí")) {
        aux = distrito.replace("í", "i")
    }
    if (distrito.uppercase().equals("Tetuán")) {
        aux = distrito.replace("á", "a")
    }
    if (distrito.uppercase().equals("Chamartín")) {
        aux = distrito.replace("í", "i")
    }
    if (distrito.uppercase().equals("Vicálvaro")) {
        aux = distrito.replace(distrito[4].toString(), "a")
    }

    return aux.uppercase()
}