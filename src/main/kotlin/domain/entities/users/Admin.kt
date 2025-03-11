package com.example.domain.entities.users

import com.example.domain.ActionLog
import com.example.domain.Role
import com.example.domain.abstracts.User

class Admin(
    FIO: String,
    phone: String,
    email: String,
    password: String,
) : User(FIO, phone, email, password, Role.ADMIN) {
}