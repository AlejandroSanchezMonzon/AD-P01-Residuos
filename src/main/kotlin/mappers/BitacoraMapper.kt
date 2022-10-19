/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */

package mappers

import dto.BitacoraDTO
import models.Bitacora
import mu.KotlinLogging

private val logger = KotlinLogging.logger{}

/**
 * Este método se ocupa de convertir el objeto creado (Bitacora) en un objeto de tipos simples (DTO) para poder
 * pasar la información del mismo a ficheros de una forma más sencilla, evitando los tipos complejos.
 *
 * @return BitacoraDTO, un objeto DTO de tipos simples (String, por ejemplo).
 */
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