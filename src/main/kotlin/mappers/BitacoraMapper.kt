package mappers

import dto.BitacoraDTO
import models.Bitacora
import mu.KotlinLogging

private val logger = KotlinLogging.logger{}

fun Bitacora.toDTO(): BitacoraDTO {
    logger.info("Mapeando Bitacora a DTO.")
    return BitacoraDTO(
        id = this.id.toString(),
        instante = this.instante.toString(),
        tipo = this.tipo.toString(),
        exito = this.exito,
        tiempoEjecucion = this.tiempoEjecucion
    )
}