package com.example.application.usecases.base

import com.example.domain.repositories.base.CRUDRepository
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

abstract class DeleteUseCase<T>(
    private val crudRepository: CRUDRepository<T>
) {
    suspend fun execute(entityId: UUID): Boolean {
        return crudRepository.delete(entityId)
    }
}