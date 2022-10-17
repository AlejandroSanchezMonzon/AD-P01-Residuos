/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */

package services

import dto.IAlmacenable
import mu.KotlinLogging

private val logger = KotlinLogging.logger{}

interface IStorageJSON: IStorage<IAlmacenable> {
}