/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */

package models

import dto.IAlmacenable
import mu.KotlinLogging
import org.jetbrains.kotlinx.dataframe.annotations.DataSchema


@DataSchema
data class Residuo(
    //TODO: optimización tipo dato
    val anio: Int,
    val mes: String,
    val lote: Int,
    val tipo: TipoResiduo,
    val distrito: Int,
    val nombreDistrito: String,
    val toneladas: Double

) {
    /**
     * Método que escribe una lista con las variables del objeto Residuo y el valor de cada una de ellas.
     *
     * @return String, la lista escrita con las varables del objeto.
     */
    override fun toString(): String {
        return "Residuo(anio=$anio, " +
                "mes='$mes', " +
                "lote=$lote, " +
                "residuo=$tipo, " +
                "distrito=$distrito, " +
                "nombreDistrito='$nombreDistrito', " +
                "toneladas=$toneladas)"
    }
}

enum class TipoResiduo(val tipo: String) {
    RESTO("RESTO"),
    ENVASES("ENVASES"),
    VIDRIO("VIDRIO"),
    ORGANICA("ORGANICA"),
    PAPEL_CARTON("PAPEL-CARTON"),
    PUNTOS_LIMPIOS("PUNTOS LIMPIOS"),
    CARTON_COMERCIAL("CARTON COMERCIAL"),
    VIDRIO_COMERCIAL("VIDRIO COMERCIAL"),
    PILAS("PILAS"),
    ANIMALES_MUERTOS("ANIMALES MUERTOS"),
    RCD("RCD"),
    CONTENEDORES_ROPA("CONTENEDORES DE ROPA"),
    RESIDUOS_DEPOSITADOS_MIGAS_CALIENTES("RESIDUOS_DEPOSITADOS_MIGAS_CALIENTES"),

    //Tipos añadidos
    CLINICOS("CLINICOS"),
    CAMA_CABALLO("CAMA DE CABALLO");

    companion object {
        /**
         * Función que parsea los Enums del objeto Residuo. Indicando su equivalencia al valor de tipo String.
         *
         * @param tipo El enum en formato String equivalente al valor de la clase Enum.
         *
         * @throws IllegalArgumentException Excepción que el método lanzará si el String que trata de parsear no eixste, o el tipo no existe.
         *
         * @return TipoResiduo, un enum que variará dependiendo del valor de dicha variable.
         */
        fun from(tipo: String): TipoResiduo {
            return when (tipo.uppercase()) {
                "RESTO" -> RESTO
                "ENVASES" -> ENVASES
                "VIDRIO" -> VIDRIO
                "ORGANICA" -> ORGANICA
                "PAPEL-CARTON" -> PAPEL_CARTON
                "PUNTOS LIMPIOS" -> PUNTOS_LIMPIOS
                "CARTON COMERCIAL" -> CARTON_COMERCIAL
                "VIDRIO COMERCIAL" -> VIDRIO_COMERCIAL
                "PILAS" -> PILAS
                "ANIMALES MUERTOS" -> ANIMALES_MUERTOS
                "RCD" -> RCD
                "CONTENEDORES DE ROPA" -> CONTENEDORES_ROPA
                "RESIDUOS_DEPOSITADOS_MIGAS_CALIENTES" -> RESIDUOS_DEPOSITADOS_MIGAS_CALIENTES
                "CLINICOS" -> CLINICOS
                "CAMA DE CABALLO" -> CAMA_CABALLO
                else -> throw IllegalArgumentException("Tipo de residuo no reconocido.")
            }
        }
    }
}