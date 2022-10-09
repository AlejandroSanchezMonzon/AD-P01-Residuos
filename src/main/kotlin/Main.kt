import controllers.*

import services.ServiceCSV
import services.ServiceJSON
import services.ServiceXML


fun main(args: Array<String>) {
    val serviceCSV = ServiceCSV()
    val serviceJSON = ServiceJSON()
    val serviceXML = ServiceXML()

    serviceXML.writeXMLContendores(serviceCSV.readCSVContenedor())
    serviceXML.writeXMLResiduos(serviceCSV.readCSVResiduo())

    serviceJSON.writeJSONContenedor(serviceCSV.readCSVContenedor())
    serviceJSON.writeJSONResiduo(serviceCSV.readCSVResiduo())

    inicializar()
    println(estadisticasMensualesPorTipoEnDistrito("Centro"))

}