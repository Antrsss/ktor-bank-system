package com.example.use_cases

import com.example.domain.abstracts.User
import com.example.domain.repositories.UserRepository

class RegisterUserUseCase(
    private val userRepository: UserRepository
) {
    fun execute(user: User) {
        userRepository.createUser(user)
    }
}

class AuthenticateUserUseCase(
    private val userRepository: UserRepository
) {
    fun execute(email: String, password: String): User? {
        val user = userRepository.getUserByEmail(email)
        return if (user?.password == password) user else null
    }
}