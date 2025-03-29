package com.example.application.facades.users

import com.example.application.facades.base.UsersFacade
import com.example.application.usecases.base.*
import com.example.domain.entities.users.OutsideSpecialist

class OutsideSpecialistFacade(
    createUserUseCase: CreateUseCase<OutsideSpecialist>,
    getUserUseCase: GetUseCase<OutsideSpecialist>,
    getUserByEmailUseCase: GetUserByEmailUseCase<OutsideSpecialist>,
    getUsersByBankUseCase: GetUsersByBankUseCase<OutsideSpecialist>,
    updateUserEmailUseCase: UpdateUserEmailUseCase<OutsideSpecialist>,
    deleteUserUseCase: DeleteUseCase<OutsideSpecialist>

) : UsersFacade<OutsideSpecialist>(
    createUserUseCase,
    getUserUseCase,
    getUserByEmailUseCase,
    getUsersByBankUseCase,
    updateUserEmailUseCase,
    deleteUserUseCase
)