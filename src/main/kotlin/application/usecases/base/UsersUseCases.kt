package com.example.application.usecases.base

import com.example.domain.abstracts.User
import com.example.domain.repositories.base.CRUDRepository
import com.example.domain.repositories.common.UsersRepository
import java.util.UUID

abstract class GetUserByEmailUseCase<T : User>(
    private val repository: UsersRepository<T>,
) {
    suspend fun execute(email: String): T? {
        return repository.getUserByEmail(email)
    }
}

abstract class GetUsersByBankUseCase<T : User>(
    private val repository: UsersRepository<T>,
) {
    suspend fun execute(bankUBN: UUID): List<T> {
        return repository.getUsersByBank(bankUBN)
    }
}

abstract class UpdateUserEmailUseCase<T : User>(
    private val crudRepository: CRUDRepository<T>,
) {
    suspend fun execute(userId: UUID, newEmail: String): T? {
        val user = crudRepository.get(userId)
            ?: return null

        user.email = newEmail
        return crudRepository.update(user)
    }
}
