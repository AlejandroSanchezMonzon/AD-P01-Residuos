/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */

package mappers

import dto.ResiduoDTO
import models.Residuo
import models.TipoResiduo
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * Este método se ocupa de convertir el objeto creado (ResiduoDTO) en un objeto de tipos complejos para poder
 * almacenar la información del mismo en objetos de variables con tipos que no sean unicamente simples..
 *
 * @return Residuo, un objeto de tipos complejos (LocalDate, por ejemplo).
 */
fun ResiduoDTO.toResiduo(): Residuo {
    logger.info("Mapeando DTO a Residuo.")
    return Residuo(
        anio = this.anio,
        mes = this.mes,
        lote = this.lote,
        tipo = TipoResiduo.from(this.tipo),
        distrito = this.distrito,
        nombreDistrito = this.nombreDistrito,
        toneladas = this.toneladas
    )
}
