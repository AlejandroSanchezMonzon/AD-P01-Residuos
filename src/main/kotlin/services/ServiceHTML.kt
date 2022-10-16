package services

import javafx.scene.effect.Light.Distant
import mu.KotlinLogging
import java.io.File

private val logger = KotlinLogging.logger{}

class ServiceHTML {
    fun writeHTML(directorio: String, codigoHTML: String) {
        logger.info("Escribiendo HTML.")
        val ficheroHTML = File(directorio + File.separator + "resumen.html")
        ficheroHTML.writeText(codigoHTML)
    }

    fun writeHTMLDistrito(directorio: String, codigoHTML: String, distrito: String) {
        logger.info("Escribiendo HTML.")
        val ficheroHTML = File(directorio + File.separator + "resumen_$distrito.html")
        ficheroHTML.writeText(codigoHTML)
    }
}