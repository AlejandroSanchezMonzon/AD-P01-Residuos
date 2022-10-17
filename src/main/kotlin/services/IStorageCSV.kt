/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */

package services

import dto.ContenedorDTO
import dto.IAlmacenable
import dto.ResiduoDTO
import mu.KotlinLogging

private val logger = KotlinLogging.logger{}

interface IStorageCSV: IStorage<IAlmacenable> {
    fun readResiduo(): List<ResiduoDTO>
    fun readContenedor(): List<ContenedorDTO>

}