package mappers

import dto.ContenedorDTO
import models.Contenedor
import models.TipoContenedor
import models.TipoVia

fun ContenedorDTO.toContenedor(): Contenedor {
    return Contenedor(
        codigo = this.codigo,
        contenedor = TipoContenedor.from(this.contenedor),
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

fun Contenedor.toDTO(): ContenedorDTO {
    return ContenedorDTO(
        codigo = this.codigo,
        contenedor = this.contenedor.toString(),
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