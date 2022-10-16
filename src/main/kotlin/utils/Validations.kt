package utils

import mu.KotlinLogging
import java.io.File

private val logger = KotlinLogging.logger{}

fun validateFileExtension(pathOrigen: String): Boolean {
    logger.info("Comprobando extensiones.")
    return pathOrigen.endsWith(".csv") || pathOrigen.endsWith(".json") || pathOrigen.endsWith(".xml")
}

fun validatePath(path: String): Boolean {
    logger.info("Comprobando ruta.")
    return File(path).exists()
}