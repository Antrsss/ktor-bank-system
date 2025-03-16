package com.example.application.facades.users

import com.example.application.usecases.users.*
import com.example.domain.abstracts.User
import java.util.*

abstract class UserFacade<T : User>(
    private val createUserUseCase: CreateUserUseCase<T>,
    private val getUserUseCase: GetUserUseCase<T>,
    private val getUserByEmailUseCase: GetUserByEmailUseCase<T>,
    private val getUserByPhoneUseCase: GetUserByPhoneUseCase<T>,
    private val getUsersByBankUseCase: GetUsersByBankUseCase<T>,
    private val updateUserUseCase: UpdateUserUseCase<T>,
    private val deleteUserUseCase: DeleteUserUseCase<T>
) {
    suspend fun createUser(user: T): T? {
        return createUserUseCase.execute(user)
    }

    suspend fun getUser(userId: UUID): T? {
        return getUserUseCase.execute(userId)
    }

    suspend fun getUserByEmail(email: String): T? {
        return getUserByEmailUseCase.execute(email)
    }

    suspend fun getUserByPhone(phone: String): T? {
        return getUserByPhoneUseCase.execute(phone)
    }

    suspend fun getUsersByBank(bankUBN: UUID): List<T> {
        return getUsersByBankUseCase.execute(bankUBN)
    }

    suspend fun updateUser(user: T) {
        return updateUserUseCase.execute(user)
    }

    suspend fun deleteUser(userId: UUID): Boolean {
        return deleteUserUseCase.execute(userId)
    }
}