/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */

package services

import dto.BitacoraDTO
import dto.ContenedorDTO
import dto.ResiduoDTO
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import mu.KotlinLogging
import nl.adaptivity.xmlutil.serialization.XML
import java.io.File

private val logger = KotlinLogging.logger {}

class StorageXML {
    /**
     * Método que escribe, mediante una lista de DTOs de Residuos, un fichero en formato XML con la información de cada uno de ellos.
     *
     * @param directorio Ruta contenedora en la cual nosotros guardaremos nuestro fichero XML.
     * @param residuosDTO Lista de DTOs de la cual sacaremos, para cada uno, la información la cual escribiremos en el XML.
     *
     * @return Unit, no devolveremos nada, pero llamamos a los métodos para la creación y escritura del XML.
     */
    fun writeResiduo(directorio: String, residuosDTO: List<ResiduoDTO>) {
        logger.info("Escribiendo XML.")
        val ficheroResiduos = File(directorio + File.separator + "residuos_resultado_parser.xml")
        val xml = XML { indent = 4 }
        ficheroResiduos.writeText(xml.encodeToString(residuosDTO))
    }


    /**
     * Método que lee un fichero en formato XML con la información de cada uno de ellos.
     *
     * @param directorio Ruta contenedora del archivo XML.
     *
     * @return residuoDTO Lista de DTOs de la información la cual leeremos en el XML.
     */
    fun readResiduo(directorio: String): List<ResiduoDTO> {
        logger.info("Leyendo XML.")
        val xml = XML { indent = 4 }
        val ficheroResiduo = File(directorio + File.separator + "residuos_resultado_parser.xml")

        return xml.decodeFromString(ficheroResiduo.readText())
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
        val xml = XML { indent = 4 }
        ficheroContenedor.writeText(xml.encodeToString(contenedorDTO))
    }


    /**
     * Método que lee un fichero en formato XML con la información de cada uno de ellos.
     *
     * @param directorio Ruta contenedora del archivo XML.
     *
     * @return contenedorDTO Lista de DTOs de la información la cual leeremos en el XML.
     */
    fun readContenedor(directorio: String): List<ContenedorDTO> {
        logger.info("Leyendo XML.")
        val xml = XML { indent = 4 }
        val ficheroContenedor = File(directorio + File.separator + "contenedores_resultado_parser.xml")

        return xml.decodeFromString(ficheroContenedor.readText())
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
        val xml = XML { indent = 4 }

        if (ficheroBitacora.exists()) {
            val lista = readBitacora(directorio)
            lista.add(bitacoraDTO)
            ficheroBitacora.writeText(xml.encodeToString(lista))

        } else {
            ficheroBitacora.writeText(xml.encodeToString(listOf(bitacoraDTO)))
        }
    }

    fun readBitacora(directorio: String): MutableList<BitacoraDTO> {
        logger.info("Leyendo XML.")
        val ficheroBitacora = File(directorio + File.separator + "bitacora.xml")
        val xml = XML { indent = 4 }
        //exists
        return xml.decodeFromString(ficheroBitacora.readText())
    }
}