package com.example.domain.repositories.common

import com.example.domain.abstracts.User
import com.example.domain.repositories.base.CRUDRepository
import java.util.*

interface UsersRepository<T : User>: CRUDRepository<T> {
    suspend fun getUserByEmail(email: String): T?
    suspend fun getUsersByBank(bankUBN: UUID): List<T>
}