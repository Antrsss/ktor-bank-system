package com.example.application.usecases.users

import com.example.application.usecases.base.*
import com.example.domain.entities.users.Operator
import com.example.domain.repositories.base.CRUDRepository
import com.example.domain.repositories.common.UsersRepository

class CreateOperatorUseCase(
    repository: UsersRepository<Operator>
) : CreateUseCase<Operator>(repository)

class GetOperatorUseCase(
    repository: UsersRepository<Operator>
) : GetUseCase<Operator>(repository)

class GetOperatorByEmailUseCase(
    repository: UsersRepository<Operator>
) : GetUserByEmailUseCase<Operator>(repository)

class GetOperatorsByBankUseCase(
    repository: UsersRepository<Operator>
) : GetUsersByBankUseCase<Operator>(repository)

class UpdateOperatorEmailUseCase(
    repository: UsersRepository<Operator>
) : UpdateUserEmailUseCase<Operator>(repository)

class DeleteOperatorUseCase(
    repository: UsersRepository<Operator>
) : DeleteUseCase<Operator>(repository)