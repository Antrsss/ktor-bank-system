package com.example.application.facades.base

import com.example.application.usecases.base.*
import com.example.domain.abstracts.User
import java.util.*

abstract class UsersFacade<T : User>(
    private val createUserUseCase: CreateUseCase<T>,
    private val getUserUseCase: GetUseCase<T>,
    private val getUserByEmailUseCase: GetUserByEmailUseCase<T>,
    private val getUsersByBankUseCase: GetUsersByBankUseCase<T>,
    private val updateUserEmailUseCase: UpdateUserEmailUseCase<T>,
    private val deleteUserUseCase: DeleteUseCase<T>
) {
    suspend fun createUser(user: T) {
        return createUserUseCase.execute(user)
    }

    suspend fun getUser(userId: UUID): T? {
        return getUserUseCase.execute(userId)
    }

    suspend fun getUserByEmail(email: String): T? {
        return getUserByEmailUseCase.execute(email)
    }

    suspend fun getUsersByBank(bankUBN: UUID): List<T> {
        return getUsersByBankUseCase.execute(bankUBN)
    }

    suspend fun updateUserEmail(userId: UUID, newEmail: String): T? {
        return updateUserEmailUseCase.execute(userId, newEmail)
    }

    suspend fun deleteUser(userId: UUID): Boolean {
        return deleteUserUseCase.execute(userId)
    }
}