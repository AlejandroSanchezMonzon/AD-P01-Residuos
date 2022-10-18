/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */

package utils

import mu.KotlinLogging
import java.io.File

private val logger = KotlinLogging.logger {}

/**
 * Función que analiza la extensión del fichero del archivo determinado, comprobando si este es un JSON, CSV o XML.
 *
 * @param pathOrigen Ruta del fichero del cual analizaremos la extensión.
 *
 * @return  True si el valor de la extensión es aceptado (CSV, JSON o XML) y false si es al contrario.
 */
fun validateFileExtension(pathOrigen: String): Boolean {
    logger.info("Comprobando extensiones.")
    return pathOrigen.endsWith(".csv") ||
            pathOrigen.endsWith(".json") ||
            pathOrigen.endsWith(".xml")
}

/**
 * Función que comprueba si la ruta (Path) pasada por parámetros existe o no.
 *
 * @param path Valor de tipo String que se relaciona con la ruta que queremos analizar.
 *
 * @return True si la ruta existe, false si no es existente.
 */
fun validatePath(path: String): Boolean {
    logger.info("Comprobando ruta.")
    return File(path).exists()
}

/**
 * Comprueba que el número de columnas del archivo de residuos es igual a 7.
 *
 * @param cabecera Cadena de texto que incluye el nombre de las columnas separadas por punto y coma.
 *
 * @return True si el tamaño de la cabecera es el adecuado, false si no.
 */
fun longitudCabeceraResiduos(cabecera: String): Boolean {
    return cabecera.split(";").size == 7
}

/**
 * Comprueba que el número de columnas del archivo de contenedores es igual a 16.
 *
 * @param cabecera Cadena de texto que incluye el nombre de las columnas separadas por punto y coma.
 *
 * @return True si el tamaño de la cabecera es el adecuado, false si no.
 */
fun longitudCabeceraContenedores(cabecera: String): Boolean {
    return cabecera.split(";").size == 16
}

/**
 * En base a la cabecera obtiene el nombre de las comunas.
 *
 * @param cabecera Cadena de texto que incluye el nombre de las columnas separadas por punto y coma.
 *
 * @return Lista que contiene el nombre de cada columna en una posición.
 */
fun separacionColumnas(cabecera: String): List<String> {
    return cabecera.split(";")
}
