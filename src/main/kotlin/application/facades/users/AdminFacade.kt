package com.example.application.facades.users

import com.example.application.facades.base.UsersFacade
import com.example.application.usecases.base.*
import com.example.domain.entities.users.Admin

class AdminFacade(
    createUserUseCase: CreateUseCase<Admin>,
    getUserUseCase: GetUseCase<Admin>,
    getUserByEmailUseCase: GetUserByEmailUseCase<Admin>,
    getUsersByBankUseCase: GetUsersByBankUseCase<Admin>,
    updateUserEmailUseCase: UpdateUserEmailUseCase<Admin>,
    deleteUserUseCase: DeleteUseCase<Admin>

) : UsersFacade<Admin>(
    createUserUseCase,
    getUserUseCase,
    getUserByEmailUseCase,
    getUsersByBankUseCase,
    updateUserEmailUseCase,
    deleteUserUseCase
)