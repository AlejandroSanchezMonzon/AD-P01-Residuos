import controllers.ProcesamientoDatos
import utils.validateFileExtension
import utils.validatePath

fun main(args: Array<String>){
    //TODO: Implementar path que no existan.
    val procesamientoDatos = ProcesamientoDatos();
    procesamientoDatos
        .opcionParser(
            "/Users/mireyasanchez/Documents/AccesoADatos/Proyectos/AD-P01-Residuos/data",
            "/Users/mireyasanchez/Documents/AccesoADatos/Proyectos/AD-P01-Residuos/data/output")
}

fun checkArgs(args: Array<String>) {
    if (args.size < 2 || args.size > 4) {
        //TODO: añadir logger indicando que los argumentos no son válidos.
        println("Argumentos inválidos.")
    }

    val numberArgs = args.size

    if (args[0].uppercase() == "PARSER" && numberArgs == 3) {
        val pathOrigen = args[1]
        val pathDestino = args[2]

        if (validateFileExtension(pathOrigen)) {
            //TODO: añadir lo que hace la opción PARSER.
        } else {
            //TODO: Añadir logger indicando que el path no es válido.
            println("El archivo no es válido.")
        }
    } else if (args[0].uppercase() == "RESUMEN" && numberArgs == 3) {
        val pathOrigen = args[1]
        val pathDestino = args[2]

        if (validateFileExtension(pathOrigen) && validatePath(pathOrigen) && validatePath(pathDestino)) {
            //TODO: añadir lo que hace la opción RESUMEN.
        } else {
            //TODO: Añadir logger indicando que el path o el archivo no es válido.
            println("El path o el archivo no es válido.")
        }

        //TODO: controlar que args[1] es un distrito.
    } else if (args[0].uppercase() == "RESUMEN" && numberArgs == 4) {
        val pathOrigen = args[2]
        val pathDestino = args[3]

        if (validateFileExtension(pathOrigen) && validatePath(pathOrigen) && validatePath(pathDestino)) {
            //TODO: añadir lo que hace la opción RESUMEN - DISTRITO.
        } else {
            //TODO: Añadir logger indicando que el path o el archivo no es válido.
            println("El path o el archivo no es válido.")
        }
    } else {
        //TODO: Añadir logger indicando que los argumentos no son válidos.
    }
}