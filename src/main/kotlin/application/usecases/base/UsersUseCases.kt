package com.example.application.usecases.base

import com.example.domain.abstracts.User
import com.example.domain.repositories.common.UserRepository
import java.util.UUID

abstract class CreateUserUseCase<T : User>(
    private val repository: UserRepository<T>,
) {
    suspend fun execute(user: T): T? {
        return repository.createUser(user)
    }
}

abstract class GetUserUseCase<T : User>(
    private val repository: UserRepository<T>
) {
    suspend fun execute(userId: UUID): T? {
        return repository.getUser(userId)
    }
}

abstract class GetUserByEmailUseCase<T : User>(
    private val repository: UserRepository<T>,
) {
    suspend fun execute(email: String): T? {
        return repository.getUserByEmail(email)
    }
}

abstract class GetUserByPhoneUseCase<T : User>(
    private val repository: UserRepository<T>,
) {
    suspend fun execute(phone: String): T? {
        return repository.getUserByPhone(phone)
    }
}

abstract class GetUsersByBankUseCase<T : User>(
    private val repository: UserRepository<T>,
) {
    suspend fun execute(bankUBN: UUID): List<T> {
        return repository.getUsersByBank(bankUBN)
    }
}

abstract class UpdateUserUseCase<T : User>(
    private val repository: UserRepository<T>,
) {
    suspend fun execute(user: T) {
        return repository.updateUser(user)
    }
}

abstract class DeleteUserUseCase<T : User>(
    private val repository: UserRepository<T>,
) {
    suspend fun execute(userId: UUID): Boolean {
        return repository.deleteUser(userId)
    }
}