package services

import mu.KotlinLogging
import java.io.File

private val logger = KotlinLogging.logger{}

class ServiceCSS {
    fun writeCSS(directorio: String) {
        logger.info("Escribiendo CSS.")
        val codigoCSS = """
            body {
                background-color: lightblue;
                font-family: Arial, serif;
                text-align: center;
            }

            h1 {
                color: darkcyan;
            }

            hr {
                border: 1px solid black;
                background-color: black;
            }

            div {
                background-color: white;
                margin: 50px;
                padding: 10px;
                border: 1px solid black;
                border-radius: 25px;
            }

            p {
                font-weight: bold;
            }
        """.trimIndent()

        val ficheroHTML = File(directorio + File.separator + "style.css")
        ficheroHTML.writeText(codigoCSS)
    }
}