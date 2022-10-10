package services

import dto.ContenedorDTO
import dto.ResiduoDTO

interface IStorage<T> {
    fun writeResiduo(residuos: List<T>)
    fun writeContenedor(contenedores: List<T>)

}