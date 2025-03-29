package com.example.application.usecases.users

import com.example.application.usecases.base.*
import com.example.domain.entities.users.Manager
import com.example.domain.repositories.base.CRUDRepository
import com.example.domain.repositories.common.UsersRepository

class CreateManagerUseCase(
    repository: UsersRepository<Manager>
) : CreateUseCase<Manager>(repository)

class GetManagerUseCase(
    repository: UsersRepository<Manager>
) : GetUseCase<Manager>(repository)

class GetManagerByEmailUseCase(
    repository: UsersRepository<Manager>
) : GetUserByEmailUseCase<Manager>(repository)

class GetManagersByBankUseCase(
    repository: UsersRepository<Manager>
) : GetUsersByBankUseCase<Manager>(repository)

class UpdateManagerEmailUseCase(
    repository: UsersRepository<Manager>
) : UpdateUserEmailUseCase<Manager>(repository)

class DeleteManagerUseCase(
    repository: UsersRepository<Manager>
) : DeleteUseCase<Manager>(repository)