package services

import dto.ContenedorDTO
import dto.ResiduoDTO
import kotlinx.serialization.encodeToString
import nl.adaptivity.xmlutil.serialization.XML
import java.io.File

class StorageXML {
    fun writeResiduo(directorio: String, residuosDTO: List<ResiduoDTO>) {
        val ficheroResiduos = File(directorio + File.separator + "residuos_resultado_parser.xml")
        val xml = XML {indent = 4}
        ficheroResiduos.writeText(xml.encodeToString(residuosDTO))
    }

    fun writeContenedor(directorio: String, contenedorDTO: List<ContenedorDTO>) {
        val ficheroContenedor = File(directorio + File.separator + "contenedores_resultado_parser.xml")
        val xml = XML {indent = 4}
        ficheroContenedor.writeText(xml.encodeToString(contenedorDTO))
    }
}