package com.example.application.usecases.users

import com.example.application.usecases.base.*
import com.example.domain.entities.users.ForeignClient
import com.example.domain.repositories.base.CRUDRepository
import com.example.domain.repositories.common.UsersRepository

class CreateForeignClientUseCase(
    repository: UsersRepository<ForeignClient>
) : CreateUseCase<ForeignClient>(repository)

class GetForeignClientUseCase(
    repository: UsersRepository<ForeignClient>
) : GetUseCase<ForeignClient>(repository)

class GetForeignClientByEmailUseCase(
    repository: UsersRepository<ForeignClient>
) : GetUserByEmailUseCase<ForeignClient>(repository)

class GetForeignClientsByBankUseCase(
    repository: UsersRepository<ForeignClient>
) : GetUsersByBankUseCase<ForeignClient>(repository)

class UpdateForeignClientEmailUseCase(
    repository: UsersRepository<ForeignClient>
) : UpdateUserEmailUseCase<ForeignClient>(repository)

class DeleteForeignClientUseCase(
    repository: UsersRepository<ForeignClient>
) : DeleteUseCase<ForeignClient>(repository)