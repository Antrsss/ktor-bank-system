package com.example.application.usecases.users

import com.example.application.usecases.base.*
import com.example.domain.entities.users.OutsideSpecialist
import com.example.domain.repositories.base.CRUDRepository
import com.example.domain.repositories.common.UsersRepository

class CreateOutsideSpecialistUseCase(
    repository: UsersRepository<OutsideSpecialist>
) : CreateUseCase<OutsideSpecialist>(repository)

class GetOutsideSpecialistUseCase(
    repository: UsersRepository<OutsideSpecialist>
) : GetUseCase<OutsideSpecialist>(repository)

class GetOutsideSpecialistByEmailUseCase(
    repository: UsersRepository<OutsideSpecialist>
) : GetUserByEmailUseCase<OutsideSpecialist>(repository)

class GetOutsideSpecialistsByBankUseCase(
    repository: UsersRepository<OutsideSpecialist>
) : GetUsersByBankUseCase<OutsideSpecialist>(repository)

class UpdateOutsideSpecialistEmailUseCase(
    repository: UsersRepository<OutsideSpecialist>
) : UpdateUserEmailUseCase<OutsideSpecialist>(repository)

class DeleteOutsideSpecialistUseCase(
    repository: UsersRepository<OutsideSpecialist>
) : DeleteUseCase<OutsideSpecialist>(repository)