/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */

package services

import javafx.scene.effect.Light.Distant
import mu.KotlinLogging
import java.io.File

private val logger = KotlinLogging.logger{}

class ServiceHTML {
    /**
     * Método que se encarga de escribir el fichero .html en la caerpeta indicada en el método relacionado con la opción RESUMEN.
     *
     * @param directorio La ruta de destino en la cual guardaremos el fichero creado.
     * @param codigoHTML Es el código del HTML en formato String, para introducirlo en el método writeText().
     *
     * @return Unit, no devuelve ningun valor, sino que hacemos una llamada al método que se encarga de crear el fichero HTML.
     */
    fun writeHTML(directorio: String, codigoHTML: String) {
        logger.info("Escribiendo HTML.")
        val ficheroHTML = File(directorio + File.separator + "resumen.html")
        ficheroHTML.writeText(codigoHTML)
    }

    /**
     * Método que se encarga de escribir el fichero .html en la caerpeta indicada en el método relacionado con la opción RESUMEN DISTRITO.
     *
     * @param directorio La ruta de destino en la cual guardaremos el fichero creado.
     * @param codigoHTML Es el código del HTML en formato String, para introducirlo en el método writeText().
     * @param distrito Es el distrito del cual haremos las consultas y enfocaremos el HTML.
     *
     * @return Unit, no devuelve ningun valor, sino que hacemos una llamada al método que se encarga de crear el fichero HTML.
     */
    fun writeHTMLDistrito(directorio: String, codigoHTML: String, distrito: String) {
        logger.info("Escribiendo HTML.")
        val ficheroHTML = File(directorio + File.separator + "resumen_$distrito.html")
        ficheroHTML.writeText(codigoHTML)
    }
}