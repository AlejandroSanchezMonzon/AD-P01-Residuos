package utils

import java.io.File

fun validateFileExtension(pathOrigen: String): Boolean {
    return pathOrigen.endsWith(".csv") || pathOrigen.endsWith(".json") || pathOrigen.endsWith(".xml")
}

fun validatePath(path: String): Boolean {
    return File(path).exists()
}