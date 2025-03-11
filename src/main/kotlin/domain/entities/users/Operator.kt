package com.example.domain.entities.users

import com.example.domain.Role
import com.example.domain.abstracts.User
import com.example.domain.entities.requests.SalaryProjectRequest
import com.example.domain.entities.requests.TransactionRequest

class Operator(
    FIO: String,
    phone: String,
    email: String,
    password: String
) : User(FIO, phone, email, password, Role.OPERATOR) {
}