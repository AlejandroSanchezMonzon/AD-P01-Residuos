package services

import dto.ContenedorDTO
import dto.ResiduoDTO
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class StorageJSON {
    fun writeResiduo(directorio: String, residuoDTO: List<ResiduoDTO>) {
        val ficheroResiduo = File(directorio + File.separator + "residuos_resultado_parser.json")
        val json = Json { prettyPrint = true }
        ficheroResiduo.writeText(json.encodeToString(residuoDTO))
    }

    fun writeContenedor(directorio: String, contenedorDTO: List<ContenedorDTO>) {
        val ficheroContenedor = File(directorio + File.separator + "contenedores_resultado_parser.json")
        val json = Json { prettyPrint = true }
        ficheroContenedor.writeText(json.encodeToString(contenedorDTO))
    }

}