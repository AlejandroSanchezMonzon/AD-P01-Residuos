package services

import dto.ContenedorDTO
import dto.ResiduoDTO
import java.io.File

class ServiceCSV {
    val directorio = System.getProperty("user.dir") + File.separator + "data"

    fun readCSVResiduo(): List<ResiduoDTO>{
        val ficheroResiduo = File(directorio + File.separator + "modelo_residuos_2021.csv")
        if(ficheroResiduo.exists()){
            return ficheroResiduo.readLines().drop(1)
                .map{it.split(";")}
                .map{campo ->
                    ResiduoDTO(
                        anio = campo[0].toInt(),
                        mes = campo[1],
                        lote = campo[2].toInt(),
                        residuo = campo[3],
                        distrito = campo[4].toInt(),
                        nombreDistrito = campo[5],
                        //TODO: cuidado con las comas del double
                        toneladas = campo[6].toDouble(),

                    )
                }
        }else{
            throw IllegalArgumentException("El fichero ${ficheroResiduo.absolutePath} no existe");
        }
    }

    fun writeCSVResiduo(residuos: List<ResiduoDTO>) {
        val ficheroResiduo = File(directorio + File.separator + "resultado_residuos.csv")
        ficheroResiduo.writeText("anio;mes;lote;residuo;distrito;nombreDistrito;toneladas")
        residuos.forEach {
            ficheroResiduo.appendText("\n${it.anio};${it.mes};${it.lote};${it.residuo};${it.distrito};${it.nombreDistrito};${it.toneladas}")
        }
    }

    fun readCSVContenedor(): List<ContenedorDTO>{
        val ficheroContenedor = File(directorio + File.separator + "contenedores_varios.csv")
        if(ficheroContenedor.exists()){
            return ficheroContenedor.readLines().drop(1)
                .map{it.split(";")}
                .map{campo ->
                    ContenedorDTO(
                        codigo = campo[0].toInt(),
                        contenedor = campo[1],
                        modelo = campo[2],
                        descripcion = campo[3],
                        cantidad = campo[4].toInt(),
                        lote = campo[5].toInt(),
                        distrito = campo[6],
                        barrio = campo[7],
                        via = campo[8],
                        nombre = campo[9],
                        numero = campo[10].toInt(),
                        coordenadaX = campo[11].toDouble(),
                        coordenadaY = campo[12].toDouble(),
                        longitud = campo[13],
                        latitud = campo[14],
                        direccion = campo[15]

                        )
                }
        }else{
            throw IllegalArgumentException("El fichero ${ficheroContenedor.absolutePath} no existe");
        }
    }

    fun writeCSVContenedor(contenedores: List<ContenedorDTO>) {
        val ficheroContenedor = File(directorio + File.separator + "resultado_contendores.csv")
        //TODO: tiene que tener la misma cabecera o podemos cambiar los nombres de las columnas
        ficheroContenedor.writeText("codigo;contenedor;modelo;descripcion;cantidad;lote;distrito;barrio;via;nombre;numero;coordenadaX,coordenadaY;longitud;latitud;direccion")
        contenedores.forEach {
            ficheroContenedor.appendText("\n${it.codigo};${it.contenedor};${it.modelo};${it.descripcion};${it.cantidad};${it.lote};${it.distrito};${it.barrio};${it.via};${it.nombre};${it.numero};${it.coordenadaX};${it.coordenadaY};${it.longitud};${it.latitud}${it.direccion}")
        }
    }
}