package com.example.application.facades.users

import com.example.application.facades.base.UsersFacade
import com.example.application.usecases.base.*
import com.example.domain.entities.users.Client

class ClientFacade(
    createUserUseCase: CreateUseCase<Client>,
    getUserUseCase: GetUseCase<Client>,
    getUserByEmailUseCase: GetUserByEmailUseCase<Client>,
    getUsersByBankUseCase: GetUsersByBankUseCase<Client>,
    updateUserEmailUseCase: UpdateUserEmailUseCase<Client>,
    deleteUserUseCase: DeleteUseCase<Client>

) : UsersFacade<Client>(
    createUserUseCase,
    getUserUseCase,
    getUserByEmailUseCase,
    getUsersByBankUseCase,
    updateUserEmailUseCase,
    deleteUserUseCase
)