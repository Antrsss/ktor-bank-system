package com.example.domain.repositories

import com.example.domain.abstracts.User
import java.util.*

interface UserRepository {
    fun createUser(user: User)
    fun getUserById(userId: UUID): User?
    fun getUserByEmail(email: String): User?
    fun updateUser(user: User)
    fun deleteUser(userId: UUID)
}