package com.example.application.usecases.users

import com.example.application.usecases.base.*
import com.example.domain.entities.users.Admin
import com.example.domain.repositories.common.UsersRepository

class CreateAdminUseCase(
    userRepository: UsersRepository<Admin>
) : CreateUseCase<Admin>(userRepository)

class GetAdminUseCase(
    usersRepository: UsersRepository<Admin>
) : GetUseCase<Admin>(usersRepository)

class GetAdminByEmailUseCase(
    usersRepository: UsersRepository<Admin>
) : GetUserByEmailUseCase<Admin>(usersRepository)

class GetAdminsByBankUseCase(
    usersRepository: UsersRepository<Admin>
) : GetUsersByBankUseCase<Admin>(usersRepository)

class UpdateAdminEmailUseCase(
    usersRepository: UsersRepository<Admin>
) : UpdateUserEmailUseCase<Admin>(usersRepository)

class DeleteAdminUseCase(
    usersRepository: UsersRepository<Admin>
) : DeleteUseCase<Admin>(usersRepository)
