package models

data class Contenedor(
    val codigo: Int,
    val contenedor: TipoContenedor,
    val modelo: String,
    val descripcion: String,
    val cantidad: Int,
    val lote: Int,
    val distrito: String,
    val barrio: String,
    val via: TipoVia,
    val nombre: String,
    val numero: Int,
    val coordenadaX: Double,
    val coordenadaY: Double,
    val longitud: String,
    val latitud: String,
    val direccion: String

) {
}

enum class TipoContenedor{
    ORANICA,
    RESTO,
    ENVASES,
    VIDRIO_PAPEL,
    CARTON
}

enum class TipoVia{
    CALLE,
    AVENIDA,
    PASEO,
    RONDA,
    PLAZA,
    GLORIETA,
    COLONIA,
    CARRETERA,
    BULEVAR,
    CAMINO,
    PARTICULAR,
    TRAVESIA,
    PASAJE,
    CALLEJON,
    CUESTA,
    COSTANILLA,
    AUTOVIA,
    CARRERA

}