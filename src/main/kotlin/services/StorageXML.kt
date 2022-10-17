/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */

package services

import dto.BitacoraDTO
import dto.ContenedorDTO
import dto.ResiduoDTO
import kotlinx.serialization.encodeToString
import mu.KotlinLogging
import nl.adaptivity.xmlutil.serialization.XML
import java.io.File

private val logger = KotlinLogging.logger{}

class StorageXML {
    /**
     * Método que escribe, mediante una lista de DTOs de Residuos, un fichero en formato XML con la información de cada uno de ellos.
     *
     * @param directorio Ruta contenedora en la cual nosotros guardaremos nuestro fichero XML.
     * @param residuosDTO Lista de DTOs de la cual sacaremos, para cada uno, la información la cual escribiremos en el XML.
     *
     * @return Unit, no devolveremos nada, pero llamamos a los métodos para la creación y escritura del XML.
     */
     
    //TODO: implementar lectura
    fun writeResiduo(directorio: String, residuosDTO: List<ResiduoDTO>) {
        logger.info("Escribiendo XML.")
        val ficheroResiduos = File(directorio + File.separator + "residuos_resultado_parser.xml")
        val xml = XML {indent = 4}
        ficheroResiduos.writeText(xml.encodeToString(residuosDTO))
    }

    /**
     * Método que escribe, mediante una lista de DTOs de Contenedores, un fichero en formato XML con la información de cada uno de ellos.
     *
     * @param directorio Ruta contenedora en la cual nosotros guardaremos nuestro fichero XML.
     * @param contenedorDTO Lista de DTOs de la cual sacaremos, para cada uno, la información la cual escribiremos en el XML.
     *
     * @return Unit, no devolveremos nada, pero llamamos a los métodos para la creación y escritura del XML.
     */
    fun writeContenedor(directorio: String, contenedorDTO: List<ContenedorDTO>) {
        logger.info("Escribiendo XML.")
        val ficheroContenedor = File(directorio + File.separator + "contenedores_resultado_parser.xml")
        val xml = XML {indent = 4}
        ficheroContenedor.writeText(xml.encodeToString(contenedorDTO))
    }

    /**
     * Método que escribe, mediante un DTO de Bitacora, un fichero en formato XML con la información de cada uno de ellos.
     *
     * @param directorio Ruta contenedora en la cual nosotros guardaremos nuestro fichero XML.
     * @param bitacoraDTO DTO del cual sacaremos la información la cual escribiremos en el XML.
     *
     * @return Unit, no devolveremos nada, pero llamamos a los métodos para la creación y escritura del XML.
     */
    fun writeBitacora(directorio: String, bitacoraDTO: BitacoraDTO) {
        logger.info("Escribiendo XML.")
        val ficheroBitacora = File(directorio + File.separator + "bitacora.xml")
        if (ficheroBitacora.exists()) {
            val xml = XML { indent = 4 }
            ficheroBitacora.appendText("\n")
            ficheroBitacora.appendText(xml.encodeToString(bitacoraDTO))
        } else {
            val xml = XML { indent = 4 }
            ficheroBitacora.writeText(xml.encodeToString(bitacoraDTO))
        }
    }
}