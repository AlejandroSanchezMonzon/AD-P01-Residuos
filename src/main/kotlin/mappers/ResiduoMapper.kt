package mappers

import dto.ResiduoDTO
import models.Residuo
import models.TipoResiduo
import mu.KotlinLogging

private val logger = KotlinLogging.logger{}

fun ResiduoDTO.toResiduo(): Residuo {
    logger.info("Mapeando DTO a Residuo.")
    return Residuo (
        anio = this.anio,
        mes = this.mes,
        lote = this.lote,
        tipo = TipoResiduo.from(this.tipo),
        distrito = this.distrito,
        nombreDistrito = this.nombreDistrito,
        toneladas = this.toneladas
    )
}

fun Residuo.toDTO(): ResiduoDTO {
    logger.info("Mapeando Residuo a DTO.")
    return ResiduoDTO(
        anio = this.anio,
        mes = this.mes,
        lote = this.lote,
        tipo = this.tipo.tipo,
        distrito = this.distrito,
        nombreDistrito = this.nombreDistrito,
        toneladas = this.toneladas
    )
}