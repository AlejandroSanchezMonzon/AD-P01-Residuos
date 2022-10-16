package services

import dto.IAlmacenable
import mu.KotlinLogging

private val logger = KotlinLogging.logger{}

interface IStorageXML: IStorage<IAlmacenable> {
}