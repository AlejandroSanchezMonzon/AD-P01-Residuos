package mappers

import dto.BitacoraDTO
import models.Bitacora


fun Bitacora.toDTO(): BitacoraDTO {
    return BitacoraDTO(
        id = this.id.toString(),
        instante = this.instante.toString(),
        tipo = this.tipo.toString(),
        exito = this.exito,
        tiempoEjecucion = this.tiempoEjecucion
    )
}