package services

import dto.ContenedorDTO
import dto.ResiduoDTO
import mu.KotlinLogging

private val logger = KotlinLogging.logger{}

interface IStorage<T> {
    fun writeResiduo(residuos: List<T>)
    fun writeContenedor(contenedores: List<T>)

}