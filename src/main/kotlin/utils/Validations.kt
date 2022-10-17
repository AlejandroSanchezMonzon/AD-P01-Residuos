/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */

package utils

import mu.KotlinLogging
import java.io.File

private val logger = KotlinLogging.logger{}

/**
 * Función que analiza la extensión del fichero del archivo determinado, comprobando si este es un JSON, CSV o XML.
 *
 * @param pathOrigen Ruta del fichero del cual analizaremos la extensión.
 *
 * @return  True si el valor de la extensión es aceptado (CSV, JSON o XML) y false si es al contrario.
 */
fun validateFileExtension(pathOrigen: String): Boolean {
    logger.info("Comprobando extensiones.")
    return pathOrigen.endsWith(".csv") || pathOrigen.endsWith(".json") || pathOrigen.endsWith(".xml")
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