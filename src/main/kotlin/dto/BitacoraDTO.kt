package dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import mu.KotlinLogging
import nl.adaptivity.xmlutil.serialization.XmlElement
import java.time.LocalDateTime

private val logger = KotlinLogging.logger{}

@Serializable
@SerialName("Bitacora")
data class BitacoraDTO (
    @XmlElement(true)
    val id: String,

    @XmlElement(true)
    val instante: String,

    @XmlElement(true)
    val tipo: String,

    @XmlElement(true)
    val exito: Boolean,

    @XmlElement(true)
    val tiempoEjecucion: Long
)