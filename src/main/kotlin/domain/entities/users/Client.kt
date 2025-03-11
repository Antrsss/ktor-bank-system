package com.example.domain.entities.users

import com.example.domain.Role
import com.example.domain.abstracts.User
import java.util.*

class Client(
    FIO: String,
    val passportSeries: String,
    val passportNumber: Int,
    phone: String,
    email: String,
    password: String
) : User(FIO,  phone, email, password, Role.CLIENT) {

    val clientID: UUID = UUID.randomUUID()
}
