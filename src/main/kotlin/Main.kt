import controllers.*

import services.ServiceCSV
import services.ServiceJSON
import services.ServiceXML


fun main(args: Array<String>) {
    val serviceCSV = ServiceCSV()
    val serviceJSON = ServiceJSON()
    val serviceXML = ServiceXML()

    inicializar()

    serviceXML.writeXMLContendores(serviceCSV.readCSVContenedor())
    serviceXML.writeXMLResiduos(serviceCSV.readCSVResiduo())

    serviceJSON.writeJSONContenedor(serviceCSV.readCSVContenedor())
    serviceJSON.writeJSONResiduo(serviceCSV.readCSVResiduo())

    println(mediaContenedoresPorTipoPorDistrito())

}