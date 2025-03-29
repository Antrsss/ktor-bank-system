package com.example.domain.repositories.base

import java.util.*

interface CRUDRepository<T> {
    suspend fun create(entity: T)
    suspend fun get(id: UUID): T?
    suspend fun update(entity: T): T
    suspend fun delete(entityId: UUID): Boolean
}