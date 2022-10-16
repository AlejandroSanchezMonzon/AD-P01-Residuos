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
    fun writeResiduo(directorio: String, residuosDTO: List<ResiduoDTO>) {
        logger.info("Escribiendo XML.")
        val ficheroResiduos = File(directorio + File.separator + "residuos_resultado_parser.xml")
        val xml = XML {indent = 4}
        ficheroResiduos.writeText(xml.encodeToString(residuosDTO))
    }

    fun writeContenedor(directorio: String, contenedorDTO: List<ContenedorDTO>) {
        logger.info("Escribiendo XML.")
        val ficheroContenedor = File(directorio + File.separator + "contenedores_resultado_parser.xml")
        val xml = XML {indent = 4}
        ficheroContenedor.writeText(xml.encodeToString(contenedorDTO))
    }

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