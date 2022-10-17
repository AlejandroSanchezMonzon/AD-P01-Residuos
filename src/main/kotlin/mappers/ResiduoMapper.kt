/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */

package mappers

import dto.ResiduoDTO
import models.Residuo
import models.TipoResiduo
import mu.KotlinLogging

private val logger = KotlinLogging.logger{}

/**
 * Este método se ocupa de convertir el objeto creado (ResiduoDTO) en un objeto de tipos complejos para poder
 * almacenar la información del mismo en objetos de variables con tipos que no sean unicamente simples..
 *
 * @return Residuo, un objeto de tipos complejos (LocalDate, por ejemplo).
 */
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

/**
 * Este método se ocupa de convertir el objeto creado (Residuo) en un objeto de tipos simples (DTO) para poder
 * pasar la información del mismo a ficheros de una forma más sencilla, evitando los tipos complejos.
 *
 * @return ResiduoDTO, un objeto DTO de tipos simples (String, por ejemplo).
 */
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