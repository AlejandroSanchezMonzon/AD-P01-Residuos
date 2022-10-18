/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */

package controllers

import jetbrains.datalore.base.values.Color
import mappers.toContenedor
import mappers.toDTO
import mappers.toResiduo
import models.Bitacora
import models.Contenedor
import models.Residuo
import models.TipoOpcion
import mu.KotlinLogging
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.*
import org.jetbrains.kotlinx.dataframe.io.html
import org.jetbrains.letsPlot.Stat.identity
import org.jetbrains.letsPlot.export.ggsave
import org.jetbrains.letsPlot.geom.geomBar
import org.jetbrains.letsPlot.geom.geomPoint
import org.jetbrains.letsPlot.intern.Plot
import org.jetbrains.letsPlot.label.labs
import org.jetbrains.letsPlot.letsPlot
import services.*
import utils.dateFormatter
import utils.parseDistrito
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDateTime
import java.util.*
import kotlin.io.path.exists
import kotlin.system.measureTimeMillis


class ProcesamientoDatos {
    private val logger = KotlinLogging.logger {}

    private val storageCSV = StorageCSV()
    private val storageJSON = StorageJSON()
    private val storageXML = StorageXML()
    private val serviceHTML = ServiceHTML()
    private val serviceCSS = ServiceCSS()

    /**
     * Este método es el encargado de ejecutar la opción "PARSER" del programa. Leyendo de los CSV indicados que encontrará en el Path Origen la información,
     * procesando la misma, y sacandola en formato JSON, XML y CSV de nuevo al Path Destino.
     *
     * @param pathOrigen Es la ruta contenedora de la cual leeremos los ficheros CSV.
     * @param pathDestino Es la ruta en la cual escribiremos los nuevos ficheros.
     *
     * @exception Exception En caso de que el path Destino u Origen no existan, que el fichero a leer no este presente, o situaciones parecidas,
     * el programa lanza una excpeción que es controlada por el propio método.
     *
     * @return  Unit, es un método que sirve de llamada a otros métodos.
     */
    fun opcionParser(pathOrigen: String, pathDestino: String) {
        logger.info("Ejecutando opción parser.")
        var success = true
        var ejecutionTime = 0L

        try {
            ejecutionTime = measureTimeMillis {
                logger.info("Escribiendo archivos sobre residuos.")
                val listaResiduosDTO = storageCSV.readResiduo(pathOrigen)
                storageCSV.writeResiduo(pathDestino, listaResiduosDTO)
                storageJSON.writeResiduo(pathDestino, listaResiduosDTO)
                storageXML.writeResiduo(pathDestino, listaResiduosDTO)

                logger.info("Escribiendo archivos sobre contenedores.")
                val listaContenedoresDTO = storageCSV.readContenedor(pathOrigen)
                storageCSV.writeContenedor(pathDestino, listaContenedoresDTO)
                storageJSON.writeContenedor(pathDestino, listaContenedoresDTO)
                storageXML.writeContenedor(pathDestino, listaContenedoresDTO)
            }
        } catch(e: Exception) {
            success = false


            when(e){
                is IllegalArgumentException -> logger.error(e.message)
            }

        }
        logger.info("Creando archivo bitácora.")
        val bitacora = Bitacora(UUID.randomUUID(), LocalDateTime.now(), TipoOpcion.PARSER, success, ejecutionTime)
        storageXML.writeBitacora(pathDestino, bitacora.toDTO())
    }

    /**
     * Este método ejecuta la opción "RESUMEN" del programa. Tras leer los CSV, JSON o XML contenidos en el Path Origen,
     * realiza una serie de consultas y crea un documento HTML con la información registrada.
     *
     * @param pathOrigen Ruta de la carpeta contenedora de los archivos CSV, XML o JSON que leeremos para realizar las consultas.
     * @param pathDestino Ruta a la carpeta en la cual guardaremos el fichero HTML y las subcarpetas necesarias para generar el mismo.
     *
     * @exception Exception En caso de que el path Destino u Origen no existan, que el fichero a leer no este presente, o situaciones parecidas,
     * el programa lanza una excpeción que es controlada por el propio método.
     *
     * @return  Unit, es un método que sirve de llamada a otros métodos.
     */
    fun opcionResumen(pathOrigen: String, pathDestino: String) {
        logger.info("Ejecutando opción resumen global.")
        var listaResiduos : DataFrame<Residuo> = DataFrame.empty() as DataFrame<Residuo>

        var listaContenedores : DataFrame<Contenedor> = DataFrame.empty() as DataFrame<Contenedor>

        if (
            File(pathOrigen + File.separator + "modelo_residuos_2021.csv").exists() &&
            File(pathOrigen + File.separator + "contenedores_varios.csv").exists() ) {
            listaResiduos = storageCSV
                .readResiduo(pathOrigen)
                .map { it.toResiduo() }
                .toDataFrame()

            listaContenedores = storageCSV
                .readContenedor(pathOrigen)
                .map { it.toContenedor() }
                .toDataFrame()

        }else if(
            File(pathOrigen + File.separator + "residuos_resultado_parser.csv").exists() &&
            File(pathOrigen + File.separator + "contenedores_resultado_parser.csv").exists() ) {
            listaResiduos = storageCSV
                .readResiduo(pathOrigen + File.separator + "residuos_resultado_parser.csv")
                .map { it.toResiduo() }
                .toDataFrame()

            listaContenedores = storageCSV
                .readContenedor(pathOrigen + File.separator + "contenedores_resultado_parser.csv")
                .map { it.toContenedor() }
                .toDataFrame()

        }else if(
            File(pathOrigen + File.separator + "residuos_resultado_parser.json").exists() &&
            File(pathOrigen + File.separator + "contenedores_resultado_parser.json").exists() ) {
            listaResiduos = storageJSON
                .readResiduo(pathOrigen)
                .map { it.toResiduo() }
                .toDataFrame()

            listaContenedores = storageJSON
                .readContenedor(pathOrigen)
                .map { it.toContenedor() }
                .toDataFrame()

        }else if(
            File(pathOrigen + File.separator + "residuos_resultado_parser.xml").exists() &&
            File(pathOrigen + File.separator + "contenedores_resultado_parser.xml").exists() ) {
            listaResiduos = storageXML
                .readResiduo(pathOrigen)
                .map { it.toResiduo() }
                .toDataFrame()

            listaContenedores = storageXML
                .readContenedor(pathOrigen)
                .map { it.toContenedor() }
                .toDataFrame()

        }else{
            logger.error("Extensión de fichero no válida para la lectura.")
        }



        var success = true
        var ejecutionTime = 0L
        val html: String

        try {
            logger.info("Haciendo consultas.")
            ejecutionTime = measureTimeMillis {
                inicializar(listaResiduos, listaContenedores)
                numeroContenedoresPorTipoPorDistrito(listaContenedores)
                mediaContenedoresPorTipoPorDistrito(listaContenedores)
                graficoTotalContenedoresDistrito(listaContenedores, pathDestino)
                mediaToneladasAnualesPorTipoResiuoPorDistrito(listaResiduos)
                graficoMediaToneladasDistrito(listaResiduos, pathDestino)
                estadisticasToneladasAnualesPorTipoPorDistrito(listaResiduos)
                cantidadResiduosAnualPorDistrito(listaResiduos)
                cantidadResiduoPorTipoPorDistrito(listaResiduos)
            }

            logger.info("Escribiendo html.")
            html = """
                <DOCTYPE html>
                    <html lang="es-ES">
                        <head>
                            <meta charset="utf-8">
                            <title>Resumen de recogidas de basura y reciclaje de Madrid.</title>

                            <link rel="stylesheet" type="text/css" href="./style.css">
                        </head>

                        <body>
                        <hr>

                            <h1>Resumen de recogidas de basura y reciclaje de Madrid</h1>
                            <p>Fecha de generación: ${dateFormatter(LocalDateTime.now())}</p>
                            <p>Autores: Alejandro Sánchez Monzón - Mireya Sánchez Pinzón</p>

                        <hr>

                            <div>
                                <ul>
                                    <li>Número de contenedores de cada tipo que hay en cada distrito: 
                                        <p>${numeroContenedoresPorTipoPorDistrito(listaContenedores)}</p>
                                    </li>
                                    
                                    <li>Media de contenedores de cada tipo que hay en cada distrito: 
                                        <p>${mediaContenedoresPorTipoPorDistrito(listaContenedores)}</p>
                                    </li>
                                    
                                    <li>Gráfico con el total de contenedores por distrito:
                                        <p><img title="Gráfico con el total de contenedores por distrito" src="./images/contenedores_distritos.png"></img></p>
                                    </li>
                                    
                                    <li>Media de toneladas anuales de recogidas por cada tipo de basura agrupadas por distrito: 
                                        <p>${mediaToneladasAnualesPorTipoResiuoPorDistrito(listaResiduos)}</p>
                                    </li>
                                    <li>Gráfico de media de toneladas mensuales de recogida de basura por distrito:
                                        <p><img title="Gráfico de media de toneladas mensuales de recogida de basura por distrito" src="./images/media_toneladas_distrito.png"></img></p>
                                    </li>
                                    
                                    <li>Máximo, mínimo , media y desviación de toneladas anuales de recogidas por cada tipo de basura agrupadas por distrito: 
                                        <p>${estadisticasToneladasAnualesPorTipoPorDistrito(listaResiduos)}</p>
                                    </li>
                                    
                                    <li>Suma de todo lo recogido en un año por distrito: 
                                        <p>${cantidadResiduosAnualPorDistrito(listaResiduos)}</p>
                                    </li>
                                    
                                    <li>Por cada distrito obtener para cada tipo de residuo la cantidad recogida: 
                                        <p>${cantidadResiduoPorTipoPorDistrito(listaResiduos)}</p>
                                    </li>
                                </ul>
                            </div>
                            <div>
                                <p>Tiempo de generación en milisegundos: $ejecutionTime</p>
                            </div>

                            <hr>
                        </body>
                    </html>
                </DOCTYPE>
            """.trimIndent()

            logger.info("Creando archivo html.")
            serviceCSS.writeCSS(pathDestino)
            serviceHTML.writeHTML(pathDestino, html)
        } catch(e: Exception) {
            success = false

            when(e){
                is IOException -> logger.error {"Path incorrecto."}
            }
        }

        logger.info("Creando archivo bitácora.")
        val bitacora = Bitacora(UUID.randomUUID(), LocalDateTime.now(), TipoOpcion.RESUMEN_GLOBAL, success, ejecutionTime)
        storageXML.writeBitacora(pathDestino, bitacora.toDTO())

    }

    /**
     * Este método ejecuta la opción "RESUMEN DISTRITO" del programa. Tras leer los CSV, JSON o XML contenidos en el Path Origen,
     * realiza una serie de consultas y crea un documento HTML con la información registrada.
     *
     * @param pathOrigen Ruta de la carpeta contenedora de los archivos CSV, XML o JSON que leeremos para realizar las consultas de dicho distrito.
     * @param pathDestino Ruta a la carpeta en la cual guardaremos el fichero HTML y las subcarpetas necesarias para generar el mismo.
     *
     * @exception Exception En caso de que el path Destino u Origen no existan, que el fichero a leer no este presente, o situaciones parecidas,
     * el programa lanza una excpeción que es controlada por el propio método.
     *
     * @return  Unit, es un método que sirve de llamada a otros métodos.
     */
    fun opcionResumenDistrito(distrito: String, pathOrigen: String, pathDestino: String) {
        logger.info("Ejecutando opción resumen distrito concreto.")
        var listaResiduos : DataFrame<Residuo> = DataFrame.empty() as DataFrame<Residuo>

        var listaContenedores : DataFrame<Contenedor> = DataFrame.empty() as DataFrame<Contenedor>

        if (
            File(pathOrigen + File.separator + "modelo_residuos_2021.csv").exists() &&
            File(pathOrigen + File.separator + "contenedores_varios.csv").exists() ) {
            listaResiduos = storageCSV
                .readResiduo(pathOrigen)
                .map { it.toResiduo() }
                .toDataFrame()

            listaContenedores = storageCSV
                .readContenedor(pathOrigen)
                .map { it.toContenedor() }
                .toDataFrame()

        }else if(
            File(pathOrigen + File.separator + "residuos_resultado_parser.csv").exists() &&
            File(pathOrigen + File.separator + "contenedores_resultado_parser.csv").exists() ) {
            listaResiduos = storageCSV
                .readResiduo(pathOrigen + File.separator + "residuos_resultado_parser.csv")
                .map { it.toResiduo() }
                .toDataFrame()

            listaContenedores = storageCSV
                .readContenedor(pathOrigen + File.separator + "contenedores_resultado_parser.csv")
                .map { it.toContenedor() }
                .toDataFrame()

        }else if(
            File(pathOrigen + File.separator + "residuos_resultado_parser.json").exists() &&
            File(pathOrigen + File.separator + "contenedores_resultado_parser.json").exists() ) {
            listaResiduos = storageJSON
                .readResiduo(pathOrigen)
                .map { it.toResiduo() }
                .toDataFrame()

            listaContenedores = storageJSON
                .readContenedor(pathOrigen)
                .map { it.toContenedor() }
                .toDataFrame()

        }else if(
            File(pathOrigen + File.separator + "residuos_resultado_parser.xml").exists() &&
            File(pathOrigen + File.separator + "contenedores_resultado_parser.xml").exists() ) {
            listaResiduos = storageXML
                .readResiduo(pathOrigen)
                .map { it.toResiduo() }
                .toDataFrame()

            listaContenedores = storageXML
                .readContenedor(pathOrigen)
                .map { it.toContenedor() }
                .toDataFrame()

        }else{
            logger.error("Extensión de fichero no válida para la lectura.")
        }

        var success = true
        var ejecutionTime = 0L
        val html: String

        try {
            logger.info("Haciendo consultas.")
            ejecutionTime = measureTimeMillis {
                inicializar(listaResiduos, listaContenedores)
                numeroContenedoresPorTipoEnDistrito(listaContenedores, distrito)
                cantidadToneladasPorResiduoEnDistrito(listaResiduos, distrito)
                graficoTotalToneladasResiduoDistrito(listaResiduos, distrito, pathDestino)
                estadisticasMensualesPorTipoEnDistrito(listaResiduos, distrito)
                graficoMaxMinMediaPorMeses(listaResiduos, distrito, pathDestino)
            }

            logger.info("Escribiendo html.")
            html = """
                <DOCTYPE html>
                    <html lang="es-ES">
                        <head>
                            <meta charset="utf-8">
                            <title> Resumen de recogidas de basura y reciclaje de $distrito</title>

                            <link rel="stylesheet" type="text/css" href="./style.css">
                        </head>

                        <body>
                        <hr>

                            <h1>Resumen de recogidas de basura y reciclaje de $distrito</h1>
                            <p>Fecha de generación: ${dateFormatter(LocalDateTime.now())}</p>
                            <p>Autores: Alejandro Sánchez Monzón - Mireya Sánchez Pinzón</p>

                        <hr>

                            <div>
                                <ul>
                                    <li>Número de contenedores de cada tipo que hay en este distrito: 
                                        <p>${numeroContenedoresPorTipoEnDistrito(listaContenedores, distrito)}</p>
                                    </li>
                                    
                                    <li>Total de toneladas recogidas en ese distrito por residuo: 
                                        <p>${cantidadToneladasPorResiduoEnDistrito(listaResiduos, distrito)}</p>
                                    </li>
                                    
                                    <li>Gráfico con el total de toneladas por residuo en ese distrito:
                                        <p><img title="Gráfico con el total de toneladas por residuo en ese distrito" src="./images/toneladas_tipo_$distrito.png"></img></p>
                                    </li>
                                    
                                    <li>Máximo, mínimo , media y desviación por mes por residuo en dicho distrito: 
                                        <p>${estadisticasMensualesPorTipoEnDistrito(listaResiduos, distrito)}</p>
                                    </li>
                                    <li>Gráfica del máximo, mínimo y media por meses en dicho distrito
                                        <p><img title="Gráfica del máximo, mínimo y media por meses en dicho distrito" src="./images/media_min_max_mensual_$distrito.png"></img></p>
                                    </li>
                                </ul>
                            </div>
                            <div>
                                <p>Tiempo de generación en milisegundos: $ejecutionTime</p>
                            </div>

                            <hr>
                        </body>
                    </html>
                </DOCTYPE>
            """.trimIndent()

            logger.info("Creando archivo html.")
            serviceCSS.writeCSS(pathDestino)
            serviceHTML.writeHTMLDistrito(pathDestino, html, distrito)

        } catch(e: Exception) {
            success = false

            when(e){
                is IOException -> logger.error {"Path incorrecto."}
            }
        }

        logger.info("Creando archivo bitácora.")
        val bitacora = Bitacora(UUID.randomUUID(), LocalDateTime.now(), TipoOpcion.RESUMEN_CIUDAD, success, ejecutionTime)
        storageXML.writeBitacora(pathDestino, bitacora.toDTO())

    }

    /**
     * Esta función se ocupa de inicializar los dataframes que serán necesarios para la realización de las consultas.
     * Hemos utilizado .toDataFrame() para agrupar en uno mismo todos los datos, y no tener que segmentar las mismas. Ya que nos parecía más cómodo y "clean code".
     *
     * @param listaResiduos Dataframe de Residuos, utilizado para trabajar con las consultas del mismo.
     * @param listaContenedores Dataframe de Contenedores, utilizado para trabajar con las consultas del mismo.
     *
     * @return Unit, es un método que sirve como inicialización.
     */
    private fun inicializar(listaResiduos: DataFrame<Residuo>, listaContenedores: DataFrame<Contenedor>) {
        logger.info("Inicializando DataFrames.")
        listaResiduos.cast<Residuo>()
        listaContenedores.cast<Contenedor>()
    }

    /**
     * Este método se ocupa de la consulta de el número de contenedores de cada tipo que hay en cada distrito.
     *
     * @param listaContenedores Dataframe necesario para la realización de dicha consulta.
     *
     * @return La consulta indicada, la cual luego será pasada como un valor al HTML.
     */
    private fun numeroContenedoresPorTipoPorDistrito(listaContenedores: DataFrame<Contenedor>): String {
        logger.info("Consultando el número de contenedores de cada tipo que hay en cada distrito.")
        return listaContenedores
            .groupBy("distrito", "tipo")
            .aggregate {
                sum("cantidad") into "Total"
            }
            .sortBy("distrito").html()
    }


    /**
     * Este método se ocupa de la consulta de la media de contenedores de cada tipo que hay en cada distrito.
     *
     * @param listaContenedores Dataframe necesario para la realización de dicha consulta.
     *
     * @return La consulta indicada, la cual luego será pasada como un valor al HTML.
     */
    private fun mediaContenedoresPorTipoPorDistrito(listaContenedores: DataFrame<Contenedor>): String {
        logger.info("Consultando media de contenedores de cada tipo que hay en cada distrito.")
        return listaContenedores
            .groupBy("distrito", "tipo")
            .aggregate {
                mean("cantidad") into "Media"
            }
            .sortBy("distrito").html()
    }

    /**
     * Este método se ocupa de crear el gráfico con el total de contenedores por distrito.
     *
     * @param listaContenedores Dataframe necesario para la realización de dicha consulta.
     *
     * @return No devuelve un lista, sino que llama a un método llamado "ggsave()" que genera una imagen, la cual contiene la gráfica.
     */
    private fun graficoTotalContenedoresDistrito(listaContenedores: DataFrame<Contenedor>, pathDestino: String) {
        logger.info("Creando gráfico con el total de contenedores por distrito. ")
        val res = listaContenedores
            .groupBy("distrito", "tipo")
            .aggregate {
                sum("cantidad") into "TotalContenedores"
            }
            .toMap()

        val fig: Plot = letsPlot(data = res) + geomBar(
            stat = identity,
            alpha = 0.8,
            fill = Color.BLACK,
            color = Color.ORANGE
        ) {
            x = "distrito"
            y = "TotalContenedores"
        } + labs(
            x = "Distrito",
            y = "Total de contenedores",
            title = "Total de contenedores por distrito."
        )

        val path = pathDestino + File.separator + "images"
        if (!Paths.get(path).exists()) {
            Files.createDirectory(Paths.get(pathDestino + File.separator + "images" + File.separator))
        }

        ggsave(fig, path = path + File.separator, filename = "contenedores_distritos.png")
    }

    /**
     * Este método se ocupa de la consulta de media de toneladas anuales de recogidas por cada tipo de basura agrupadas por distrito.
     *
     * @param listaResiduos Dataframe necesario para la realización de dicha consulta.
     *
     * @return La consulta indicada, la cual luego será pasada como un valor al HTML.
     */
    private fun mediaToneladasAnualesPorTipoResiuoPorDistrito(listaResiduos: DataFrame<Residuo>): String {
        logger.info("Consultando media de toneladas anuales de recogidas por cada tipo de basura agrupadas por distrito.")
        return listaResiduos
            .groupBy("nombreDistrito", "tipo", "anio")
            .aggregate {
                mean("toneladas") into "Media"
            }
            .sortBy("nombreDistrito").html()
    }

    /**
     * Este método se ocupa de crear el gráfico de media de toneladas mensuales de recogida de basura por distrito.
     *
     * @param listaResiduos Dataframe necesario para la realización de dicha consulta.
     *
     * @return No devuelve un lista, sino que llama a un método llamado "ggsave()" que genera una imagen, la cual contiene la gráfica.
     */
    private fun graficoMediaToneladasDistrito(listaResiduos: DataFrame<Residuo>, pathDestino: String) {
        logger.info("Creando gráfico de media de toneladas mensuales de recogida de basura por distrito.")
        val res = listaResiduos
            .groupBy("nombreDistrito", "mes")
            .aggregate {
                mean("toneladas") into "media"
            }
            .toMap()

        val fig: Plot = letsPlot(data = res) + geomPoint(
            stat = identity,
            alpha = 0.8,
            fill = Color.BLACK,
            color = Color.BLACK
        ) {
            x = "nombreDistrito"
            y = "media"
        } + labs(
            x = "Distrito",
            y = "Media de basura recogida.",
            title = "Media de toneladas mensuales de recogida de basura por distrito."
        )

        val path = pathDestino + File.separator + "images"
        if (!Paths.get(path).exists()) {
            Files.createDirectory(Paths.get(pathDestino + File.separator + "images" + File.separator))
        }

        ggsave(fig, path = path + File.separator, filename = "media_toneladas_distrito.png")
    }

    /**
     * Este método se ocupa de la consulta de máximo, mínimo, media y desviación de toneladas anuales de recogidas por cada tipo de basura agrupadas por distrito
     *
     * @param listaResiduos Dataframe necesario para la realización de dicha consulta.
     *
     * @return La consulta indicada, la cual luego será pasada como un valor al HTML.
     */
    private fun estadisticasToneladasAnualesPorTipoPorDistrito(listaResiduos: DataFrame<Residuo>): String {
        logger.info("Consultando máximo, mínimo, media y desviación de toneladas anuales de recogidas por cada tipo de basura agrupadas por distrito.")
        return listaResiduos
            .groupBy("nombreDistrito", "tipo", "anio")
            .aggregate {
                mean("toneladas") into "Media"
                min("toneladas") into "Mínimo"
                max("toneladas") into "Máximo"
                std("toneladas").toString() into "Desviación"
            }
            .sortBy("nombreDistrito").html()
    }

    /**
     * Este método se ocupa de la consulta de suma de to do lo recogido en un año por distrito.
     *
     * @param listaResiduos Dataframe necesario para la realización de dicha consulta.
     *
     * @return La consulta indicada, la cual luego será pasada como un valor al HTML.
     */
    private fun cantidadResiduosAnualPorDistrito(listaResiduos: DataFrame<Residuo>): String {
        logger.info("Consultando suma de to do lo recogido en un año por distrito.")
        return listaResiduos
            .filter { it["anio"] == 2021 }
            .groupBy("nombreDistrito")
            .aggregate {
                sum("toneladas") into "TotalAnual"
            }
            .sortBy("nombreDistrito").html()
    }

    /**
     * Este método se ocupa de la consulta de, por cada distrito, obtener para cada tipo de residuo la cantidad recogida.
     *
     * @param listaResiduos Dataframe necesario para la realización de dicha consulta.
     *
     * @return La consulta indicada, la cual luego será pasada como un valor al HTML.
     */
    private fun cantidadResiduoPorTipoPorDistrito(listaResiduos: DataFrame<Residuo>): String {
        logger.info("Consultando por cada distrito obtener para cada tipo de residuo la cantidad recogida.")
        return listaResiduos
            .groupBy("nombreDistrito", "tipo")
            .aggregate {
                sum("toneladas") into "TotalResiduo"
            }
            .sortBy("nombreDistrito").html()
    }

    /**
     * Este método se ocupa de la consulta del número de contenedores de cada tipo que hay en dicho distrito.
     *
     * @param listaContenedores Dataframe necesario para la realización de dicha consulta.
     *
     * @return La consulta indicada, la cual luego será pasada como un valor al HTML.
     */
    private fun numeroContenedoresPorTipoEnDistrito(listaContenedores: DataFrame<Contenedor>, distrito: String): String {
        logger.info("Consultando número de contenedores de cada tipo que hay en $distrito")

        return listaContenedores
            .filter { it["distrito"] == parseDistrito(distrito)}
            .groupBy("distrito", "tipo")
            .aggregate {
                sum("cantidad") into "NumeroContenedores"
            }
            .sortBy("distrito").html()
    }

    /**
     * Este método se ocupa de la consulta del total de toneladas recogidas en dicho distrito por residuos.
     *
     * @param listaResiduos Dataframe necesario para la realización de dicha consulta.
     *
     * @return La consulta indicada, la cual luego será pasada como un valor al HTML.
     */
    private fun cantidadToneladasPorResiduoEnDistrito(listaResiduos: DataFrame<Residuo>, distrito: String): String {
        logger.info("Consultando total de toneladas recogidas en $distrito por residuo.")
        return listaResiduos
            .filter { it["nombreDistrito"] == distrito }
            .groupBy("tipo")
            .aggregate {
                sum("toneladas") into "Toneladas"
            }
            .html()
    }

    /**
     * Este método se ocupa de crear el gráfico con el total de toneladas por residuo en dicho distrito.
     *
     * @param listaResiduos Dataframe necesario para la realización de dicha consulta.
     *
     * @return No devuelve un lista, sino que llama a un método llamado "ggsave()" que genera una imagen, la cual contiene la gráfica.
     */
    private fun graficoTotalToneladasResiduoDistrito(listaResiduos: DataFrame<Residuo>, distrito: String, pathDestino: String) {
        logger.info("Creando gráfico con el total de toneladas por residuo en $distrito.")
        val res = listaResiduos
            .filter { it["nombreDistrito"] == distrito }
            .groupBy("tipo", "toneladas")
            .aggregate {
                count() into "total"
            }
            .toMap()

        val fig: Plot = letsPlot(data = res) + geomPoint(
            stat = identity,
            alpha = 0.8,
            fill = Color.BLACK,
            color = Color.BLACK,
        ) {
            x = "tipo"
            y = "toneladas"
        } + labs(
            x = "Tipo de residuo",
            y = "Toneladas",
            title = "Total de toneladas por residuo en $distrito"
        )

        val path = pathDestino + File.separator + "images"
        if (!Paths.get(path).exists()) {
            Files.createDirectory(Paths.get(pathDestino + File.separator + "images" + File.separator))
        }

        ggsave(fig, path = path + File.separator, filename = "toneladas_tipo_$distrito.png")
    }

    /**
     * Este método se ocupa de la consulta del máximo, mínimo , media y desviación por mes por residuo en dicho distrito.
     *
     * @param listaResiduos Dataframe necesario para la realización de dicha consulta.
     *
     * @return La consulta indicada, la cual luego será pasada como un valor al HTML.
     */
    private fun estadisticasMensualesPorTipoEnDistrito(listaResiduos: DataFrame<Residuo>, distrito: String): String {
        logger.info("Consultando máximo, mínimo , media y desviación por mes por residuo en $distrito.")
        return listaResiduos
            .filter { it["nombreDistrito"] == distrito }
            .groupBy("nombreDistrito", "tipo", "anio")
            .aggregate {
                mean("toneladas") into "Media"
                min("toneladas") into "Mínimo"
                max("toneladas") into "Máximo"
                std("toneladas").toString() into "Desviación"
            }
            .html()
    }

    /**
     * Este método se ocupa de crear el gráfico del máximo, mínimo y media por meses en dicho distrito.
     *
     * @param listaResiduos Dataframe necesario para la realización de dicha consulta.
     *
     * @return No devuelve un lista, sino que llama a un método llamado "ggsave()" que genera una imagen, la cual contiene la gráfica.
     */
    private fun graficoMaxMinMediaPorMeses(listaResiduos: DataFrame<Residuo>, distrito: String, pathDestino: String) {
        logger.info("Creando gráfica del máximo, mínimo y media por meses en $distrito.")
        val res = listaResiduos.filter { it["nombreDistrito"] == distrito }
            .groupBy("nombreDistrito", "mes")
            .aggregate {
                max("toneladas") into "Máximo"
                min("toneladas") into "Mínimo"
                mean("toneladas") into "Media"
            }
            .toMap()

        val fig: Plot = letsPlot(data = res) + geomBar(
            stat = identity,
            alpha = 0.8,
            fill = Color.GREEN,
            color = Color.WHITE
        ) {
            x = "mes"
            y = "Máximo"
        } + geomBar(
            stat = identity,
            alpha = 0.8,
            fill = Color.LIGHT_YELLOW,
            color = Color.WHITE
        ) {
            x = "mes"
            y = "Media"
        } + geomBar(
            stat = identity,
            alpha = 0.8,
            fill = Color.RED,
            color = Color.WHITE
        ) {
            x = "mes"
            y = "Mínimo"
        } + labs(
            x = "Mes",
            y = "Total",
            title = "Máximo, mínimo y media por meses."
        )

        val path = pathDestino + File.separator + "images"
        if (!Paths.get(path).exists()) {
            Files.createDirectory(Paths.get(pathDestino + File.separator + "images" + File.separator))
        }

        ggsave(fig, path = path + File.separator, filename = "media_min_max_mensual_$distrito.png")
    }
}