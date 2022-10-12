package services

import javafx.scene.effect.Light.Distant
import java.io.File

class ServiceHTML {
    fun writeHTML(directorio: String, codigoHTML: String) {
        val ficheroHTML = File(directorio + File.separator + "resumen.html")
        ficheroHTML.writeText(codigoHTML)
    }

    fun writeHTMLDistrito(directorio: String, codigoHTML: String, distrito: String) {
        val ficheroHTML = File(directorio + File.separator + "resumen_$distrito.html")
        ficheroHTML.writeText(codigoHTML)
    }
}