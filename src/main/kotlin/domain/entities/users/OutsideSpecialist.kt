package com.example.domain.entities.users

import com.example.domain.Role
import com.example.domain.abstracts.User
import java.util.*

class OutsideSpecialist(
    FIO: String,
    val enterpriseID: UUID,
    phone: String,
    email: String,
    password: String
) : User(FIO, phone, email, password, Role.OPERATOR) {
}