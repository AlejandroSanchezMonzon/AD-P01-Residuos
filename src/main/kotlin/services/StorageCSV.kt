/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */

package services

import dto.ContenedorDTO
import dto.ResiduoDTO
import mu.KotlinLogging
import utils.parseDouble
import utils.parseNull
import utils.validateFileExtension
import java.io.File

private val logger = KotlinLogging.logger{}

class StorageCSV {
    /**
     * Método que se encarga de leer el csv "modelo_residuos_2021.csv", el cual contiene la informción del modelo Residuo.
     * Esta función se ocupa de analizar dicha información y procesarla detenidamente.
     *
     * @param directorio Es la ruta contenedora del fichero CSV. Este método se ocupara de buscar en el mismo el fichero que necesitamos para trabajar con él.
     *
     * @throws IllegalArgumentException Excepción que el método lanzará si el fichero no existe, o si la ruta es incorrecta.
     *
     * @return Unit, este método no devuelve ningún valor, sino que se ocupa de leer y procesar el fichero CSV.
     */
    fun readResiduo(directorio: String): List<ResiduoDTO>{
        logger.info("Leyendo CSV.")
        val ficheroResiduo = File(directorio + File.separator + "modelo_residuos_2021.csv")
        if(ficheroResiduo.exists() && validateFileExtension(ficheroResiduo.toString())){
            return ficheroResiduo.readLines().drop(1)
                .map{it.split(";")}
                .map{campo ->
                    ResiduoDTO(
                        anio = campo[0].toInt(),
                        mes = campo[1],
                        lote = campo[2].toInt(),
                        tipo = campo[3],
                        distrito = campo[4].toInt(),
                        nombreDistrito = campo[5],
                        toneladas = parseDouble(campo[6]),
                    )
                }
        } else {
            throw IllegalArgumentException("El fichero ${ficheroResiduo.absolutePath} no existe");
        }
    }

    /**
     * Función que se ocupa de escribir en formato CSV la información de la lista de DTOs de Residuos procesados en
     * la ruta contenedora que nosotros que especifiquemos.
     *
     * @param directorio Ruta contenedora en la cual nosotros guardaremos nuestro fichero CSV.
     * @param residuos Lista de DTOs de la cual sacaremos, para cada uno, la información la cual escribiremos en el CSV.
     *
     * @return Unit, no devolveremos nada, pero llamamos a los métodos para la creación y escritura del CSV.
     */
     fun writeResiduo(directorio: String, residuos: List<ResiduoDTO>) {
        logger.info("Escribiendo CSV.")
        val ficheroResiduo = File(directorio + File.separator + "residuos_resultado_parser.csv")
        ficheroResiduo.writeText("anio;mes;lote;residuo;distrito;nombreDistrito;toneladas")
        residuos.forEach {
            ficheroResiduo.appendText("\n${it.anio};${it.mes};${it.lote};${it.tipo};${it.distrito};${it.nombreDistrito};${it.toneladas}")
        }
    }

    /**
     * Método que se encarga de leer el csv "contenedores_varios.csv", el cual contiene la informción del modelo Contenedor.
     * Esta función se ocupa de analizar dicha información y procesarla detenidamente.
     *
     * @param directorio Es la ruta contenedora del fichero CSV. Este método se ocupara de buscar en el mismo el fichero que necesitamos para trabajar con él.
     *
     * @throws IllegalArgumentException Excepción que el método lanzará si el fichero no existe, o si la ruta es incorrecta.
     *
     * @return Unit, este método no devuelve ningún valor, sino que se ocupa de leer y procesar el fichero CSV.
     */
    fun readContenedor(directorio: String): List<ContenedorDTO>{
        logger.info("Leyendo CSV.")
        val ficheroContenedor = File(directorio + File.separator + "contenedores_varios.csv")
        if(ficheroContenedor.exists() && validateFileExtension(ficheroContenedor.toString())){
            return ficheroContenedor.readLines().drop(1)
                .map{it.split(";")}
                .map{campo ->
                    ContenedorDTO(
                        codigo = campo[0].toInt(),
                        tipo = campo[1],
                        modelo = campo[2],
                        descripcion = campo[3],
                        cantidad = campo[4].toInt(),
                        lote = campo[5].toInt(),
                        distrito = campo[6],
                        barrio = parseNull(campo[7]),
                        via = campo[8],
                        nombre = campo[9],
                        numero = campo[10].toIntOrNull(),
                        coordenadaX = parseDouble(campo[11]),
                        coordenadaY = parseDouble(campo[12]),
                        longitud = campo[13],
                        latitud = campo[14],
                        direccion = campo[15]
                        )
                }
        } else {
            throw IllegalArgumentException("El fichero ${ficheroContenedor.absolutePath} no existe");
        }
    }

    /**
     * Función que se ocupa de escribir en formato CSV la información de la lista de DTOs de Contenedores procesados en
     * la ruta contenedora que nosotros que especifiquemos.
     *
     * @param directorio Ruta contenedora en la cual nosotros guardaremos nuestro fichero CSV.
     * @param contenedores Lista de DTOs de la cual sacaremos, para cada uno, la información la cual escribiremos en el CSV.
     *
     * @return Unit, no devolveremos nada, pero llamamos a los métodos para la creación y escritura del CSV.
     */
    fun writeContenedor(directorio: String, contenedores: List<ContenedorDTO>) {
        logger.info("Escribiendo CSV.")
        val ficheroContenedor = File(directorio + File.separator + "contenedores_resultado_parser.csv")
        //TODO: tiene que tener la misma cabecera o podemos cambiar los nombres de las columnas
        ficheroContenedor.writeText("codigo;contenedor;modelo;descripcion;cantidad;lote;distrito;barrio;via;nombre;numero;coordenadaX;coordenadaY;longitud;latitud;direccion")
        contenedores.forEach {
            ficheroContenedor.appendText("\n${it.codigo};${it.tipo};${it.modelo};${it.descripcion};${it.cantidad};${it.lote};${it.distrito};${it.barrio};${it.via};${it.nombre};${it.numero};${it.coordenadaX};${it.coordenadaY};${it.longitud};${it.latitud}${it.direccion}")
        }
    }


}