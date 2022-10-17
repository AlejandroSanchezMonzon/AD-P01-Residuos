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

private val logger = KotlinLogging.logger{}

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

//TODO: En la documentación explicar por qué no nos hace falta este método

/**
 * Este método se ocupa de convertir el objeto creado (Contenedor) en un objeto de tipos simples (DTO) para poder
 * pasar la información del mismo a ficheros de una forma más sencilla, evitando los tipos complejos.
 *
 * @return ContenedorDTO, un objeto DTO de tipos simples (String, por ejemplo).
 */
fun Contenedor.toDTO(): ContenedorDTO {
    logger.info("Mapeando Contenedor a DTO.")
    return ContenedorDTO(
        codigo = this.codigo,
        tipo = this.tipo.toString(),
        modelo = this.modelo,
        descripcion = this.descripcion,
        cantidad = this.cantidad,
        lote = this.lote,
        distrito = this.distrito,
        barrio = this.barrio,
        via =this.via.toString(),
        nombre = this.nombre,
        numero = this.numero,
        coordenadaX = this.coordenadaX,
        coordenadaY = this.coordenadaY,
        longitud = this.longitud,
        latitud = this.latitud,
        direccion = this.direccion,

    )
}