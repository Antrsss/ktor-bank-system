package com.example.application.facades.users

import com.example.application.facades.base.UsersFacade
import com.example.application.usecases.base.*
import com.example.domain.entities.users.Operator

class OperatorFacade(
    createUserUseCase: CreateUseCase<Operator>,
    getUserUseCase: GetUseCase<Operator>,
    getUserByEmailUseCase: GetUserByEmailUseCase<Operator>,
    getUsersByBankUseCase: GetUsersByBankUseCase<Operator>,
    updateUserEmailUseCase: UpdateUserEmailUseCase<Operator>,
    deleteUserUseCase: DeleteUseCase<Operator>

) : UsersFacade<Operator>(
    createUserUseCase,
    getUserUseCase,
    getUserByEmailUseCase,
    getUsersByBankUseCase,
    updateUserEmailUseCase,
    deleteUserUseCase
)