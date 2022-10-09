package dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement

@Serializable
@SerialName("Contenedor")
data class ContenedorDTO (
    @XmlElement(true)
    val codigo: Int,

    @XmlElement(true)
    val tipo: String,

    @XmlElement(true)
    val modelo: String,

    @XmlElement(true)
    val descripcion: String,

    @XmlElement(true)
    val cantidad: Int,

    @XmlElement(true)
    val lote: Int,

    @XmlElement(true)
    val distrito: String,

    @XmlElement(true)
    val barrio: String?,

    @XmlElement(true)
    val via: String,

    @XmlElement(true)
    val nombre: String,

    @XmlElement(true)
    val numero: Int?,

    @XmlElement(true)
    val coordenadaX: Double,

    @XmlElement(true)
    val coordenadaY: Double,

    @XmlElement(true)
    val longitud: String,

    @XmlElement(true)
    val latitud: String,
    
    @XmlElement(true)
    val direccion: String
){

}