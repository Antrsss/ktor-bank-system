package com.example.application.facades.users

import com.example.application.facades.base.UsersFacade
import com.example.application.usecases.base.*
import com.example.domain.entities.users.Manager

class ManagerFacade(
    createUserUseCase: CreateUseCase<Manager>,
    getUserUseCase: GetUseCase<Manager>,
    getUserByEmailUseCase: GetUserByEmailUseCase<Manager>,
    getUsersByBankUseCase: GetUsersByBankUseCase<Manager>,
    updateUserEmailUseCase: UpdateUserEmailUseCase<Manager>,
    deleteUserUseCase: DeleteUseCase<Manager>

) : UsersFacade<Manager>(
    createUserUseCase,
    getUserUseCase,
    getUserByEmailUseCase,
    getUsersByBankUseCase,
    updateUserEmailUseCase,
    deleteUserUseCase
)