package services

import models.Residuo

interface IStorageCSV: IStorage<Residuo> {
    fun read(): List<Residuo>;
}