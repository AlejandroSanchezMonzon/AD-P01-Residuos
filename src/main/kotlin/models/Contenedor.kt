package models

data class Contenedor(
    val codigo: Int,
    val contenedor: TipoContenedor,
    val modelo: String,
    val descripcion: String,
    val cantidad: Int,
    val lote: Int,
    val distrito: String,
    val barrio: String?,
    val via: TipoVia,
    val nombre: String,
    val numero: Int?,
    val coordenadaX: Double,
    val coordenadaY: Double,
    val longitud: String,
    val latitud: String,
    val direccion: String

) {
    override fun toString(): String {
        return "Contenedor(codigo=$codigo, contenedor=$contenedor," +
                " modelo='$modelo', descripcion='$descripcion'," +
                " cantidad=$cantidad, lote=$lote, distrito='$distrito'," +
                " barrio='$barrio', via=$via, nombre='$nombre', numero=$numero," +
                " coordenadaX=$coordenadaX, coordenadaY=$coordenadaY," +
                " longitud='$longitud', latitud='$latitud', direccion='$direccion')"
    }
}

enum class TipoContenedor(val tipo: String) {
    ORGANICA("ORGANICA"),
    RESTO("RESTO"),
    ENVASES("ENVASES"),
    VIDRIO("VIDRIO"),
    PAPEL_CARTON("PAPEL-CARTON");

    companion object {
        fun from(contenedor: String): TipoContenedor {
            return when (contenedor.uppercase()) {
                "ORGANICA" -> ORGANICA
                "RESTO" -> RESTO
                "ENVASES" -> ENVASES
                "VIDRIO" -> VIDRIO
                "PAPEL-CARTON" -> PAPEL_CARTON
                else -> throw IllegalArgumentException("Tipo de contenedor no reconocido")
            }
        }
    }
}

enum class TipoVia(val tipo: String) {
    CALLE("CALLE"),
    AVENIDA("AVENIDA"),
    PASEO("PASEO"),
    RONDA("RONDA"),
    PLAZA("PLAZA"),
    GLORIETA("GLORIETA"),
    COLONIA("COLONIA"),
    CARRETERA("CARRETERA"),
    BULEVAR("BULEVAR"),
    CAMINO("CAMINO"),
    PARTICULAR("PARTICULAR"),
    //TODO Algunos enum vienen con "TRAVESÍA" con tilde.
    TRAVESIA("TRAVESIA"),
    PASAJE("PASAJE"),
    //TODO Algunos enum vienen con "CALLEJÓN" con tilde.
    CALLEJON("CALLEJON"),
    CUESTA("CUESTA"),
    COSTANILLA("COSTANILLA"),
    AUTOVIA("AUTOVIA"),
    CARRERA("CARRERA");

    companion object {
        fun from(via: String): TipoVia {
            return when (via.uppercase()) {
                "CALLE" -> CALLE
                "AVENIDA" -> AVENIDA
                "PASEO" -> PASEO
                "RONDA" -> RONDA
                "PLAZA" -> PLAZA
                "GLORIETA" -> GLORIETA
                "COLONIA" -> COLONIA
                "CARRETERA" -> CARRETERA
                "BULEVAR" -> BULEVAR
                "CAMINO" -> CAMINO
                "PARTICULAR" -> PARTICULAR
                "TRAVESIA" -> TRAVESIA
                "PASAJE" -> PASAJE
                "CALLEJON" -> CALLEJON
                "CUESTA" -> CUESTA
                "COSTANILLA" -> COSTANILLA
                "AUTOVIA" -> AUTOVIA
                "CARRERA" -> CARRERA
                else -> throw IllegalArgumentException("Tipo de via no reconocida")
            }
        }
    }
}