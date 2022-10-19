/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */

package services

import dto.ContenedorDTO
import dto.ResiduoDTO
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import mu.KotlinLogging
import java.io.File

private val logger = KotlinLogging.logger {}

class StorageJSON {
    /**
     * Método que escribe, mediante una lista de DTOs de Residuos, un fichero en formato JSON con la información de cada uno de ellos.
     *
     * @param directorio Ruta contenedora en la cual nosotros guardaremos nuestro fichero JSON.
     * @param residuoDTO Lista de DTOs de la cual sacaremos, para cada uno, la información la cual escribiremos en el JSON.
     *
     * @return Unit, no devolveremos nada, pero llamamos a los métodos para la creación y escritura del JSON.
     */

    fun writeResiduo(directorio: String, residuoDTO: List<ResiduoDTO>) {
        logger.info("Escribiendo JSON.")
        val ficheroResiduo = File(directorio + File.separator + "residuos_resultado_parser.json")
        val json = Json { prettyPrint = true }
        ficheroResiduo.writeText(json.encodeToString(residuoDTO))
    }


    /**
     * Método que lee un fichero en formato JSON con la información de cada uno de ellos.
     *
     * @param directorio Ruta contenedora de nuestro fichero JSON.
     *
     * @return residuoDTO Lista de DTOs de la cual sacaremos, para cada uno, la información la cual leeremos en el JSON.
     */
    fun readResiduo(directorio: String): List<ResiduoDTO> {
        logger.info("Leyendo JSON.")
        val ficheroResiduo = File(directorio + File.separator + "residuos_resultado_parser.json")
        return Json.decodeFromString(ficheroResiduo.readText())
    }

    /**
     * Método que escribe, mediante una lista de DTOs de Contenedores, un fichero en formato JSON con la información de cada uno de ellos.
     *
     * @param directorio Ruta contenedora en la cual nosotros guardaremos nuestro fichero JSON.
     * @param contenedorDTO Lista de DTOs de la cual sacaremos, para cada uno, la información la cual escribiremos en el JSON.
     *
     * @return Unit, no devolveremos nada, pero llamamos a los métodos para la creación y escritura del JSON.
     */
    fun writeContenedor(directorio: String, contenedorDTO: List<ContenedorDTO>) {
        logger.info("Escribiendo JSON.")
        val ficheroContenedor = File(directorio + File.separator + "contenedores_resultado_parser.json")
        val json = Json { prettyPrint = true }
        ficheroContenedor.writeText(json.encodeToString(contenedorDTO))
    }

    /**
     * Método que lee un fichero en formato JSON con la información de cada uno de ellos.
     *
     * @param directorio Ruta contenedora de nuestro fichero JSON.
     *
     * @return contenedorDTO Lista de DTOs de la cual sacaremos, para cada uno, la información la cual leeremos en el JSON.
     */
    fun readContenedor(directorio: String): List<ContenedorDTO> {
        logger.info("Leyendo JSON.")
        val ficheroContenedor = File(directorio + File.separator + "contenedores_resultado_parser.json")

        return Json.decodeFromString(ficheroContenedor.readText())
    }

}