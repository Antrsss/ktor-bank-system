package com.example.application.facades.users

import com.example.application.facades.base.UsersFacade
import com.example.application.usecases.base.*
import com.example.domain.entities.users.ForeignClient

class ForeignClientFacade(
    createUserUseCase: CreateUseCase<ForeignClient>,
    getUserUseCase: GetUseCase<ForeignClient>,
    getUserByEmailUseCase: GetUserByEmailUseCase<ForeignClient>,
    getUsersByBankUseCase: GetUsersByBankUseCase<ForeignClient>,
    updateUserEmailUseCase: UpdateUserEmailUseCase<ForeignClient>,
    deleteUserUseCase: DeleteUseCase<ForeignClient>

) : UsersFacade<ForeignClient>(
    createUserUseCase,
    getUserUseCase,
    getUserByEmailUseCase,
    getUsersByBankUseCase,
    updateUserEmailUseCase,
    deleteUserUseCase
)