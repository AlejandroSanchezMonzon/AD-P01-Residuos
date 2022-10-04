package services

import dto.ContenedorDTO
import dto.ResiduoDTO
import kotlinx.serialization.encodeToString
import nl.adaptivity.xmlutil.serialization.XML
import java.io.File

class ServiceXML {
    val directorio = System.getProperty("user.dir") + File.separator + "data"

    fun writeXMLResiduos(residuosDTO: List<ResiduoDTO>) {
        val ficheroResiduos = File(directorio + File.separator + "modelo_residuos_2021.csv")
        val xml = XML {indent = 4}
        ficheroResiduos.writeText(xml.encodeToString(residuosDTO))
    }

    fun writeXMLContendores(contenedorDTO: List<ContenedorDTO>) {
        val ficheroContenedor = File(directorio + File.separator + "contenedores_varios.csv")
        val xml = XML {indent = 4}
        ficheroContenedor.writeText(xml.encodeToString(contenedorDTO))
    }
}