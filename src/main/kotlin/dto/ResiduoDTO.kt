package dto

import kotlinx.serialization.Serializable

@Serializable
data class ResiduoDTO (
    var anio: Int,
    var mes: String,
    var lote: Int,
    var residuo: String,
    var distrito: Int,
    var nombreDistrito: String,
    var toneladas: Double
)