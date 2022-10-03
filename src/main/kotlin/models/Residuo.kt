package models

data class Residuo(
    //TODO: optimización tipo dato
    val anio: Int,
    val mes: String,
    val lote: Int,
    val residuo: TipoResiduo,
    val distrito: Int,
    val nombreDistrito: String,
    val toneladas: Double

) {

}
enum class TipoResiduo{
    RESTO,
    ENVASES,
    VIDRIO,
    ORGANICA,
    PAPEL_CARTON,
    PUNTOS_LIMPIOS,
    CARTON_COMERCIAL,
    VIDRIO_COMERCIAL,
    PILAS,
    ANIMALES_MUERTOS,
    RCD,
    CONTENEDORES_ROPA,
    RESIDUOS_DEPOSITADOS_MIGAS_CALIENTES,
    //Tipos añadidos
    CLINICOS,
    CAMA_CABALLO
}