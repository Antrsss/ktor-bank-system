package com.example.application.usecases.base

import com.example.domain.repositories.base.ImmutableRepository
import java.util.*

abstract class ImmutableCreateUseCase<T>(
    private val immutableRepository: ImmutableRepository<T>
) {
    suspend fun execute(entity: T) {
        return immutableRepository.create(entity)
    }
}

abstract class ImmutableGetUseCase<T>(
    private val immutableRepository: ImmutableRepository<T>
) {
    suspend fun execute(entityId: UUID): T? {
        return immutableRepository.get(entityId)
    }
}

abstract class ImmutableDeleteUseCase<T>(
    private val immutableRepository: ImmutableRepository<T>
) {
    suspend fun execute(entityId: UUID): Boolean {
        return immutableRepository.delete(entityId)
    }
}