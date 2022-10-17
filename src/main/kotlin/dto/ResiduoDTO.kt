/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */

package dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import mu.KotlinLogging
import nl.adaptivity.xmlutil.serialization.XmlElement

@Serializable
@SerialName("Residuo")
data class ResiduoDTO (
    @XmlElement(true)
    val anio: Int,

    @XmlElement(true)
    val mes: String,

    @XmlElement(true)
    val lote: Int,

    @XmlElement(true)
    val tipo: String,

    @XmlElement(true)
    val distrito: Int,

    @XmlElement(true)
    val nombreDistrito: String,

    @XmlElement(true)
    val toneladas: Double
)