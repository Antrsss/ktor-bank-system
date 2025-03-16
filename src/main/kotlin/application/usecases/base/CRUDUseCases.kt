package com.example.application.usecases

import com.example.domain.repositories.common.CRUDRepository
import java.util.*

abstract class CreateUseCase<T>(
    private val crudRepository: CRUDRepository<T>
) {
    suspend fun execute(entity: T) {
        return crudRepository.create(entity)
    }
}

abstract class GetUseCase<T>(
    private val crudRepository: CRUDRepository<T>
) {
    suspend fun execute(entityId: UUID): T? {
        return crudRepository.get(entityId)
    }
}

abstract class GetAllUseCase<T>(
    private val crudRepository: CRUDRepository<T>
) {
    suspend fun execute(): List<T> {
        return crudRepository.getAll()
    }
}

abstract class UpdateUseCase<T>(
    private val crudRepository: CRUDRepository<T>
) {
    suspend fun execute(entity: T): T {
        return crudRepository.update(entity)
    }
}

abstract class DeleteUseCase<T>(
    private val crudRepository: CRUDRepository<T>
) {
    suspend fun execute(entityId: UUID): Boolean {
        return crudRepository.delete(entityId)
    }
}