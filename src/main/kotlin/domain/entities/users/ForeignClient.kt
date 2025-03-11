package com.example.domain.entities.users

import com.example.domain.Role
import com.example.domain.abstracts.User
import java.util.*

class ForeignClient(
    FIO: String,
    val passportSeries: String,
    val passportNumber: Int,
    phone: String,
    email: String,
    password: String,
    val country: String,
) : User(FIO, phone, email, password, role = Role.CLIENT) {

    val foreignClientID: UUID = UUID.randomUUID()
}