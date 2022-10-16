package mappers

import dto.ContenedorDTO
import models.Contenedor
import models.TipoContenedor
import models.TipoVia
import mu.KotlinLogging

private val logger = KotlinLogging.logger{}
fun ContenedorDTO.toContenedor(): Contenedor {
    logger.info("Mapeando DTO a Contendor.")
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