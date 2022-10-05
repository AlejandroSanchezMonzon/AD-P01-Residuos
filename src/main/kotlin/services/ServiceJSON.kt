package services

import dto.ContenedorDTO
import dto.ResiduoDTO
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class ServiceJSON {
    val directorio = System.getProperty("user.dir") + File.separator + "data"

    fun writeJSONResiduo(residuoDTO: List<ResiduoDTO>) {
        val ficheroResiduo = File(directorio + File.separator + "output" + File.separator + "modelo_residuos_2021.json")
        val json = Json { prettyPrint = true }
        ficheroResiduo.writeText(json.encodeToString(residuoDTO))
    }

    fun writeJSONContenedor(contenedorDTO: List<ContenedorDTO>) {
        val ficheroContenedor = File(directorio + File.separator + "output" + File.separator + "contenedores_varios.json")
        val json = Json { prettyPrint = true }
        ficheroContenedor.writeText(json.encodeToString(contenedorDTO))
    }

}