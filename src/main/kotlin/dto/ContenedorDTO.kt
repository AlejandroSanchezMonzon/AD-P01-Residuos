package dto

import kotlinx.serialization.Serializable

@Serializable
data class ContenedorDTO (
    val codigo: Int,
    val contenedor: String,
    val modelo: String,
    val descripcion: String,
    val cantidad: Int,
    val lote: Int,
    val distrito: String,
    val barrio: String,
    val via: String,
    val nombre: String,
    val numero: Int,
    val coordenadaX: Double,
    val coordenadaY: Double,
    val longitud: String,
    val latitud: String,
    val direccion: String
){

}