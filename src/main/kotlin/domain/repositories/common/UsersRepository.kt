package com.example.domain.repositories.common

import com.example.domain.abstracts.User
import java.util.*

interface UserRepository<T : User> {
    suspend fun getUserByEmail(email: String): T?
    suspend fun getUserByPhone(phone: String): T?
    suspend fun getUsersByBank(bankUBN: UUID): List<T>
}