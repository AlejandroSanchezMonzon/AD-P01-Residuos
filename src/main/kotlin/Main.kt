/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */

import controllers.ProcesamientoDatos
import mu.KotlinLogging
import utils.validatePath

private val logger = KotlinLogging.logger{}

    fun main(args: Array<String>) {
        logger.info("Iniciando programa...")
        //checkArgs(args)

        val procesamientoDatos = ProcesamientoDatos()
        procesamientoDatos.opcionResumen("src/main/resources", "data/output")
    }

    /**
    * Función principal del método main. Este se ocupa de comprobar de que los argumentos que pasaremos en el archivo .JAR
     * son aceptados en base a su orden, formato y funcionamiento.
     *
     * @param args Los argumentos que pasaremos por parámetros al .JAR
     *
     * @return Unit, no devuelve ningun valor, es un método raíz que sirve para hacer llamadas a otros métodos,
     * en este caso, para construir la estructura de la aplicación.
    */
    fun checkArgs(args: Array<String>) {
        val procesamientoDatos = ProcesamientoDatos()

        if (args.size < 2 || args.size > 4) {
            logger.error("Argumentos no válidos.")
            //TODO: controlar index out of bounds
        }

        val numberArgs = args.size

        if (args[0].uppercase() == "PARSER" && numberArgs == 3) {
            val pathOrigen = args[1]
            val pathDestino = args[2]

            if (validatePath(pathOrigen) && validatePath(pathDestino)) {
                procesamientoDatos.opcionParser(pathOrigen, pathDestino)
                //TODO: preguntar si hay que leer también de JSON y XML.

            } else {
                logger.error("Path no válido.")
            }

        } else if (args[0].uppercase() == "RESUMEN" && numberArgs == 3) {
            val pathOrigen = args[1]
            val pathDestino = args[2]

            if (validatePath(pathOrigen) && validatePath(pathDestino)) {
                procesamientoDatos.opcionResumen(pathOrigen, pathDestino)

            } else {
                logger.error("Path no válido.")
            }

            //TODO: controlar que args[1] es un distrito.
        } else if (args[0].uppercase() == "RESUMEN" && numberArgs == 4) {
            val pathOrigen = args[2]
            val pathDestino = args[3]

            if (validatePath(pathOrigen) && validatePath(pathDestino)) {
                procesamientoDatos.opcionResumenDistrito(args[1], pathOrigen, pathDestino)

            } else {
                logger.error("Path no válido.")
            }

        } else {
            logger.error("Argumentos no válidos.")
        }
    }