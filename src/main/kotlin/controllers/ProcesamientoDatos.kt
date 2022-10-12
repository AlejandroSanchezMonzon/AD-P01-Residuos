package controllers

import jetbrains.datalore.base.values.Color
import mappers.toContenedor
import mappers.toDTO
import mappers.toResiduo
import models.Bitacora
import models.Contenedor
import models.Residuo
import models.TipoOpcion
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.*
import org.jetbrains.letsPlot.Stat.identity
import org.jetbrains.letsPlot.export.ggsave
import org.jetbrains.letsPlot.geom.geomBar
import org.jetbrains.letsPlot.geom.geomPoint
import org.jetbrains.letsPlot.intern.Plot
import org.jetbrains.letsPlot.label.labs
import org.jetbrains.letsPlot.letsPlot
import services.*
import java.io.File
import java.nio.file.Paths
import java.time.LocalDateTime
import java.util.*
import kotlin.system.measureTimeMillis


class ProcesamientoDatos {
    private val storageCSV = StorageCSV()
    private val storageJSON = StorageJSON()
    private val storageXML = StorageXML()
    private val serviceHTML = ServiceHTML()
    private val serviceCSS = ServiceCSS()

    fun opcionParser(pathOrigen: String, pathDestino: String) {
        var success = true
        var ejecutionTime = 0L

        try {
            ejecutionTime = measureTimeMillis {
                val listaResiduosDTO = storageCSV.readResiduo(pathOrigen)
                storageCSV.writeResiduo(pathDestino, listaResiduosDTO)
                storageJSON.writeResiduo(pathDestino, listaResiduosDTO)
                storageXML.writeResiduo(pathDestino, listaResiduosDTO)

                val listaContenedoresDTO = storageCSV.readContenedor(pathOrigen)
                storageCSV.writeContenedor(pathDestino, listaContenedoresDTO)
                storageJSON.writeContenedor(pathDestino, listaContenedoresDTO)
                storageXML.writeContenedor(pathDestino, listaContenedoresDTO)
            }
        } catch(e: Exception) {
            success = false
        }

        val bitacora = Bitacora(UUID.randomUUID(), LocalDateTime.now(), TipoOpcion.PARSER, success, ejecutionTime)
        storageXML.writeBitacora(pathDestino, bitacora.toDTO())
    }

    fun opcionResumen(pathOrigen: String, pathDestino: String) {
        val listaResiduos = storageCSV
            .readResiduo(pathOrigen)
            .map { it.toResiduo() }
            .toDataFrame()
        val listaContenedores = storageCSV
            .readContenedor(pathOrigen)
            .map { it.toContenedor() }
            .toDataFrame()

        var success = true
        var ejecutionTime = 0L
        val html: String
        val letsPlot = Paths.get(System.getProperty("user.dir") + File.separator + "lets-plot-images")

        try {
            ejecutionTime = measureTimeMillis {
                inicializar(listaResiduos, listaContenedores)
                numeroContenedoresPorTipoPorDistrito(listaContenedores)
                mediaContenedoresPorTipoPorDistrito(listaContenedores)
                graficoTotalContenedoresDistrito(listaContenedores)
                mediaToneladasAnualesPorTipoResiuoPorDistrito(listaResiduos)
                graficoMediaToneladasDistrito(listaResiduos)
                estadisticasToneladasAnualesPorTipoPorDistrito(listaResiduos)
                cantidadResiduosAnualPorDistrito(listaResiduos)
                cantidadResiduoPorTipoPorDistrito(listaResiduos)
            }

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

                            <h1>Resumen de recogidas de basura y reciclaje de </h1>
                            <p>Fecha de generación: ${LocalDateTime.now()}</p>
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
                                    
                                    <li>
                                        <img title="Gráfico con el total de contenedores por distrito" src="./lets-plot-images/contenedores_distritos.png"></img>
                                    </li>
                                    
                                    <li>Media de toneladas anuales de recogidas por cada tipo de basura agrupadas por distrito: 
                                        <p>${mediaToneladasAnualesPorTipoResiuoPorDistrito(listaResiduos)}</p>
                                    </li>
                                    <li>
                                        <img title=" Gráfico de media de toneladas mensuales de recogida de basura por distrito" src="./lets-plot-images/media_toneladas_distrito.png"></img>
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

            serviceCSS.writeCSS(pathDestino)
            serviceHTML.writeHTML(pathDestino, html)
        }catch(e: Exception) {
            success = false
        }

        val bitacora = Bitacora(UUID.randomUUID(), LocalDateTime.now(), TipoOpcion.RESUMEN_GLOBAL, success, ejecutionTime)
        storageXML.writeBitacora(pathDestino, bitacora.toDTO())

    }

    fun opcionResumenDistrito(distrito: String, pathOrigen: String, pathDestino: String) {
        val listaResiduos = storageCSV
            .readResiduo(pathOrigen)
            .map { it.toResiduo() }
            .toDataFrame()
        val listaContenedores = storageCSV
            .readContenedor(pathOrigen)
            .map { it.toContenedor() }
            .toDataFrame()

        var success = true
        var ejecutionTime = 0L
        val html: String
        val letsPlot = Paths.get(System.getProperty("user.dir") + File.separator + "lets-plot-images")

        try {
            ejecutionTime = measureTimeMillis {
                inicializar(listaResiduos, listaContenedores)
                numeroContenedoresPorTipoEnDistrito(listaContenedores, distrito)
                cantidadToneladasPorResiduoEnDistrito(listaResiduos, distrito)
                graficoTotalToneladasResiduoDistrito(listaResiduos, distrito)
                estadisticasMensualesPorTipoEnDistrito(listaResiduos, distrito)
                graficoMaxMinMediaPorMeses(listaResiduos, distrito)
            }

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
                            <p>Fecha de generación: ${LocalDateTime.now()}</p>
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
                                    
                                    <li>
                                        <img title="Gráfico con el total de toneladas por residuo en ese distrito:" src="./lets-plot-images/contenedores_distritos.png"></img>
                                    </li>
                                    
                                    <li>Máximo, mínimo , media y desviación por mes por residuo en dicho distrito: 
                                        <p>${estadisticasMensualesPorTipoEnDistrito(listaResiduos, distrito)}</p>
                                    </li>
                                    <li>
                                        <img title="Gráfica del máximo, mínimo y media por meses en dicho distrito." src="./lets-plot-images/media_toneladas_distrito.png"></img>
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

            serviceCSS.writeCSS(pathDestino)
            serviceHTML.writeHTMLDistrito(pathDestino, html, distrito)

        }catch(e: Exception) {
            success = false
        }

        val bitacora = Bitacora(UUID.randomUUID(), LocalDateTime.now(), TipoOpcion.RESUMEN_CIUDAD, success, ejecutionTime)
        storageXML.writeBitacora(pathDestino, bitacora.toDTO())

    }

    private fun inicializar(listaResiduos: DataFrame<Residuo>, listaContenedores: DataFrame<Contenedor>) {
        listaResiduos.cast<Residuo>()
        listaContenedores.cast<Contenedor>()
    }

    // Número de contenedores de cada tipo que hay en cada distrito.
    private fun numeroContenedoresPorTipoPorDistrito(listaContenedores: DataFrame<Contenedor>): DataFrame<Contenedor> {
        return listaContenedores
            .groupBy("distrito", "tipo")
            .count()
            .sortBy("distrito")
    }

    //Media de contenedores de cada tipo que hay en cada distrito.
//TODO: Preguntar a Jose Luís cómo es esto a nivel matemático.
    private fun mediaContenedoresPorTipoPorDistrito(listaContenedores: DataFrame<Contenedor>): DataFrame<Contenedor> {
        return listaContenedores
            .groupBy("distrito", "tipo")
            .aggregate {
                count()
            }
            .sortBy("distrito")
    }

    //Gráfico con el total de contenedores por distrito.
    private fun graficoTotalContenedoresDistrito(listaContenedores: DataFrame<Contenedor>) {
        val res = listaContenedores
            .groupBy("distrito", "tipo")
            .aggregate {
                count() into "TotalContenedores"
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

        ggsave(fig, "contenedores_distritos.png")
    }

    //Media de toneladas anuales de recogidas por cada tipo de basura agrupadas por distrito.
    private fun mediaToneladasAnualesPorTipoResiuoPorDistrito(listaResiduos: DataFrame<Residuo>): DataFrame<Residuo> {
        return listaResiduos
            .groupBy("nombreDistrito", "tipo", "anio")
            .aggregate {
                mean("toneladas") into "Media"
            }
            .sortBy("nombreDistrito")
    }

    //Gráfico de media de toneladas mensuales de recogida de basura por distrito.
    private fun graficoMediaToneladasDistrito(listaResiduos: DataFrame<Residuo>) {
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

        ggsave(fig, "media_toneladas_distrito.png")
    }

    //Máximo, mínimo , media y desviación de toneladas anuales de recogidas por cada tipo de basura agrupadas por distrito.
    private fun estadisticasToneladasAnualesPorTipoPorDistrito(listaResiduos: DataFrame<Residuo>): DataFrame<Residuo> {
        return listaResiduos
            .groupBy("nombreDistrito", "tipo", "anio")
            .aggregate {
                mean("toneladas") into "Media"
                min("toneladas") into "Mínimo"
                max("toneladas") into "Máximo"
                std("toneladas") into "Desviación"
            }
            .sortBy("nombreDistrito")
    }

    //Suma de todo lo recogido en un año por distrito.
    private fun cantidadResiduosAnualPorDistrito(listaResiduos: DataFrame<Residuo>): DataFrame<Residuo> {
        return listaResiduos
            .filter { it["anio"] == 2021 }
            .groupBy("nombreDistrito")
            .aggregate {
                sum("toneladas") into "TotalAnual"
            }
            .sortBy("nombreDistrito")
    }

    //Por cada distrito obtener para cada tipo de residuo la cantidad recogida.
    private fun cantidadResiduoPorTipoPorDistrito(listaResiduos: DataFrame<Residuo>): DataFrame<Residuo> {
        return listaResiduos
            .groupBy("nombreDistrito", "tipo")
            .aggregate {
                sum("toneladas") into "TotalResiduo"
            }
            .sortBy("nombreDistrito")
    }


    //EN DISTRITO CONCRETO
//Número de contenedores de cada tipo que hay en este distrito.
    private fun numeroContenedoresPorTipoEnDistrito(listaContenedores: DataFrame<Contenedor>, distrito: String): DataFrame<Contenedor> {
        return listaContenedores
            .filter { it["distrito"] == distrito }
            .groupBy("tipo")
            .aggregate {
                count() into "NúmeroContenedores"
            }
    }

    //Total de toneladas recogidas en ese distrito por residuo.
    private fun cantidadToneladasPorResiduoEnDistrito(listaResiduos: DataFrame<Residuo>, distrito: String): DataFrame<Residuo> {
        return listaResiduos
            .filter { it["nombreDistrito"] == distrito }
            .groupBy("tipo")
            .aggregate {
                sum("toneladas") into "Toneladas"
            }
    }

    //Gráfico con el total de toneladas por residuo en ese distrito.
    private fun graficoTotalToneladasResiduoDistrito(listaResiduos: DataFrame<Residuo>, distrito: String) {
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

        ggsave(fig, "toneladas_tipo_$distrito.png")
    }

    //Máximo, mínimo , media y desviación por mes por residuo en dicho distrito.
//TODO: Mirar desviación
    private fun estadisticasMensualesPorTipoEnDistrito(listaResiduos: DataFrame<Residuo>, distrito: String): DataFrame<Residuo> {
        return listaResiduos
            .filter { it["nombreDistrito"] == distrito }
            .groupBy("nombreDistrito", "tipo", "mes")
            .aggregate {
                mean("toneladas") into "Media"
                min("toneladas") into "Mínimo"
                max("toneladas") into "Máximo"
                std("toneladas") into "Desviación"
            }
    }

    //Gráfica del máximo, mínimo y media por meses en dicho distrito.
    private fun graficoMaxMinMediaPorMeses(listaResiduos: DataFrame<Residuo>, distrito: String) {
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

        ggsave(fig, "media_min_max_mensual_$distrito.png")
    }
}