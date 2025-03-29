package com.example.domain.repositories.common

import com.example.domain.repositories.base.CRUDRepository
import java.util.*

interface RequestRepository<T>: CRUDRepository<T> {
    suspend fun getRequestsByBank(bankUBN: UUID): List<T>
}