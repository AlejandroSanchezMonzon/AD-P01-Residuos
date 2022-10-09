package controllers

import mappers.toContenedor
import mappers.toResiduo
import models.Contenedor
import models.Residuo
import org.jetbrains.kotlinx.dataframe.api.*
import services.ServiceCSV

val servicioCSV = ServiceCSV()

val listaResiduos = servicioCSV.readCSVResiduo().map { it.toResiduo() }.toDataFrame()
val listaContenedores = servicioCSV.readCSVContenedor().map { it.toContenedor() }.toDataFrame()

fun inicializar() {
    listaResiduos.cast<Residuo>()
    listaContenedores.cast<Contenedor>()
}
//EN MADRID
// Número de contenedores de cada tipo que hay en cada distrito.
fun numeroContenedoresPorTipoPorDistrito() {
    listaContenedores
        .groupBy("distrito", "tipo")
        .count()
        .sortBy("distrito")
        .print()
}
//Media de contenedores de cada tipo que hay en cada distrito.
//Gráfico con el total de contenedores por distrito.
//Media de toneladas anuales de recogidas por cada tipo de basura agrupadas por distrito.
fun mediaToneladasAnualesPorTipoResiuoPorDistrito(){
    listaResiduos
        .groupBy("nombreDistrito", "tipo", "anio")
        .aggregate{
            mean("toneladas") into "Media"
        }
        .sortBy("nombreDistrito")
        .print()
}
//Gráfico de media de toneladas mensuales de recogida de basura por distrito.
//Máximo, mínimo , media y desviación de toneladas anuales de recogidas por cada tipo de basura agrupadas por distrito.
fun estadisticasToneladasAnualesPorTipoPorDistrito(){
    listaResiduos
        .groupBy("nombreDistrito", "tipo", "anio")
        .aggregate {
            mean("toneladas") into "Media"
            min("toneladas") into "Mínimo"
            max("toneladas")into "Máximo"
            std("toneladas") into "Desviación"
        }
        .sortBy("nombreDistrito")
        .print()
}

//Suma de todo lo recogido en un año por distrito.
fun cantidadResiduosAnualPorDistrito(){
    listaResiduos
        .filter { it["anio"] == 2021}
        .groupBy("nombreDistrito")
        .aggregate{
            sum("toneladas") into "TotalAnual"
        }
        .sortBy("nombreDistrito")
        .print()
}
//Por cada distrito obtener para cada tipo de residuo la cantidad recogida.
fun cantidadResiduoPorTipoPorDistrito(){
    listaResiduos
        .groupBy("nombreDistrito", "tipo")
        .aggregate{
            sum("toneladas") into "TotalResiduo"
        }
        .sortBy("nombreDistrito")
        .print()
}
//Tiempo de generación del mismo en milisegundos.

//EN DISTRITO CONCRETO
//Número de contenedores de cada tipo que hay en este distrito.
fun numeroContenedoresPorTipoEnDistrito(distrito: String) {
    listaContenedores
        .filter { it["distrito"] == distrito }
        .groupBy("tipo")
        .aggregate{
            count() into "NúmeroContenedores"
        }
        .print()
}
//Total de toneladas recogidas en ese distrito por residuo.
fun cantidadToneladasPorResiduoEnDistrito(distrito: String) {
    listaResiduos
        .filter { it["nombreDistrito"] == distrito }
        .groupBy("tipo")
        .aggregate{
            sum("toneladas") into "Toneladas"
        }
        .print()
}
//Gráfico con el total de toneladas por residuo en ese distrito.

//Máximo, mínimo , media y desviación por mes por residuo en dicho distrito.
//TODO: Mirar desviación
fun estadisticasMensualesPorTipoEnDistrito(distrito: String){
    listaResiduos
        .filter { it["nombreDistrito"] == distrito }
        .groupBy("nombreDistrito", "tipo", "mes")
        .aggregate{
            mean("toneladas") into "Media"
            min("toneladas") into "Mínimo"
            max("toneladas")into "Máximo"
            std("toneladas") into "Desviación"
        }
        .print()
}

//Gráfica del máximo, mínimo y media por meses en dicho distrito.
//Tiempo de generación del mismo en milisegundos.



