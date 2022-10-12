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
import services.StorageCSV
import services.StorageJSON
import services.StorageXML
import java.io.IOException
import java.time.LocalDateTime
import java.util.*
import kotlin.system.exitProcess
import kotlin.system.measureTimeMillis


class ProcesamientoDatos {
    private val storageCSV = StorageCSV()
    private val storageJSON = StorageJSON()
    private val storageXML = StorageXML()



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
                //TODO: Resumen html Madrid.
            }
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

        try {
            ejecutionTime = measureTimeMillis {
                inicializar(listaResiduos, listaContenedores)
                numeroContenedoresPorTipoEnDistrito(listaContenedores, distrito)
                cantidadToneladasPorResiduoEnDistrito(listaResiduos, distrito)
                graficoTotalToneladasResiduoDistrito(listaResiduos, distrito)
                estadisticasMensualesPorTipoEnDistrito(listaResiduos, distrito)
                graficoMaxMinMediaPorMeses(listaResiduos, distrito)
                //TODO: Resumen html distrito.
            }
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
    private fun numeroContenedoresPorTipoPorDistrito(listaContenedores: DataFrame<Contenedor>) {
        listaContenedores
            .groupBy("distrito", "tipo")
            .count()
            .sortBy("distrito")
            .print()
    }

    //Media de contenedores de cada tipo que hay en cada distrito.
//TODO: Preguntar a Jose Luís cómo es esto a nivel matemático.
    private fun mediaContenedoresPorTipoPorDistrito(listaContenedores: DataFrame<Contenedor>) {
        listaContenedores
            .groupBy("distrito", "tipo")
            .aggregate {
                count()
            }
            .sortBy("distrito")
            .print()
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
    private fun mediaToneladasAnualesPorTipoResiuoPorDistrito(listaResiduos: DataFrame<Residuo>) {
        listaResiduos
            .groupBy("nombreDistrito", "tipo", "anio")
            .aggregate {
                mean("toneladas") into "Media"
            }
            .sortBy("nombreDistrito")
            .print()
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
    private fun estadisticasToneladasAnualesPorTipoPorDistrito(listaResiduos: DataFrame<Residuo>) {
        listaResiduos
            .groupBy("nombreDistrito", "tipo", "anio")
            .aggregate {
                mean("toneladas") into "Media"
                min("toneladas") into "Mínimo"
                max("toneladas") into "Máximo"
                std("toneladas") into "Desviación"
            }
            .sortBy("nombreDistrito")
            .print()
    }

    //Suma de todo lo recogido en un año por distrito.
    private fun cantidadResiduosAnualPorDistrito(listaResiduos: DataFrame<Residuo>) {
        listaResiduos
            .filter { it["anio"] == 2021 }
            .groupBy("nombreDistrito")
            .aggregate {
                sum("toneladas") into "TotalAnual"
            }
            .sortBy("nombreDistrito")
            .print()
    }

    //Por cada distrito obtener para cada tipo de residuo la cantidad recogida.
    private fun cantidadResiduoPorTipoPorDistrito(listaResiduos: DataFrame<Residuo>) {
        listaResiduos
            .groupBy("nombreDistrito", "tipo")
            .aggregate {
                sum("toneladas") into "TotalResiduo"
            }
            .sortBy("nombreDistrito")
            .print()
    }


    //EN DISTRITO CONCRETO
//Número de contenedores de cada tipo que hay en este distrito.
    private fun numeroContenedoresPorTipoEnDistrito(listaContenedores: DataFrame<Contenedor>, distrito: String) {
        listaContenedores
            .filter { it["distrito"] == distrito }
            .groupBy("tipo")
            .aggregate {
                count() into "NúmeroContenedores"
            }
            .print()
    }

    //Total de toneladas recogidas en ese distrito por residuo.
    private fun cantidadToneladasPorResiduoEnDistrito(listaResiduos: DataFrame<Residuo>, distrito: String) {
        listaResiduos
            .filter { it["nombreDistrito"] == distrito }
            .groupBy("tipo")
            .aggregate {
                sum("toneladas") into "Toneladas"
            }
            .print()
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
    private fun estadisticasMensualesPorTipoEnDistrito(listaResiduos: DataFrame<Residuo>, distrito: String) {
        listaResiduos
            .filter { it["nombreDistrito"] == distrito }
            .groupBy("nombreDistrito", "tipo", "mes")
            .aggregate {
                mean("toneladas") into "Media"
                min("toneladas") into "Mínimo"
                max("toneladas") into "Máximo"
                std("toneladas") into "Desviación"
            }
            .print()
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

