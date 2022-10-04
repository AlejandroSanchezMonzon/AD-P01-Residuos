package services

interface IStorage<T> {
    fun write(entity: List<T>);

}