/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */

package mappers

import dto.ContenedorDTO
import models.Contenedor
import models.TipoContenedor
import models.TipoVia
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * Este método se ocupa de convertir el objeto creado (ContenedorDTO) en un objeto de tipos complejos para poder
 * almacenar la información del mismo en objetos de variables con tipos que no sean unicamente simples..
 *
 * @return Contenedor, un objeto de tipos complejos (LocalDate, por ejemplo).
 */
fun ContenedorDTO.toContenedor(): Contenedor {
    logger.info("Mapeando DTO a Contenedor.")
    return Contenedor(
        codigo = this.codigo,
        tipo = TipoContenedor.from(this.tipo),
        modelo = this.modelo,
        descripcion = this.descripcion,
        cantidad = this.cantidad,
        lote = this.lote,
        distrito = this.distrito,
        barrio = this.barrio,
        via = TipoVia.from(this.via),
        nombre = this.nombre,
        numero = this.numero,
        coordenadaX = this.coordenadaX,
        coordenadaY = this.coordenadaY,
        longitud = this.longitud,
        latitud = this.latitud,
        direccion = this.direccion,
    )
}


