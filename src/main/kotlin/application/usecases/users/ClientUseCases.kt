package com.example.application.usecases.users

import com.example.application.usecases.base.*
import com.example.domain.entities.users.Client
import com.example.domain.repositories.base.CRUDRepository
import com.example.domain.repositories.common.UsersRepository

class CreateClientUseCase(
    repository: UsersRepository<Client>
) : CreateUseCase<Client>(repository)

class GetClientUseCase(
    repository: UsersRepository<Client>
) : GetUseCase<Client>(repository)

class GetClientByEmailUseCase(
    repository: UsersRepository<Client>
) : GetUserByEmailUseCase<Client>(repository)

class GetClientsByBankUseCase(
    repository: UsersRepository<Client>
) : GetUsersByBankUseCase<Client>(repository)

class UpdateClientEmailUseCase(
    repository: UsersRepository<Client>
) : UpdateUserEmailUseCase<Client>(repository)

class DeleteClientUseCase(
    repository: UsersRepository<Client>
) : DeleteUseCase<Client>(repository)