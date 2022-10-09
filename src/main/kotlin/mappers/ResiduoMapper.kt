package mappers

import dto.ResiduoDTO
import models.Residuo
import models.TipoResiduo

fun ResiduoDTO.toResiduo(): Residuo {
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