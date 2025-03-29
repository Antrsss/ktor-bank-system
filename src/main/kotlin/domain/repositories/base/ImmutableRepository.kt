package com.example.domain.repositories.base

import java.util.*

interface ImmutableRepository<T> {
    suspend fun create(entity: T)
    suspend fun get(id: UUID): T?
    suspend fun delete(id: UUID): Boolean
}