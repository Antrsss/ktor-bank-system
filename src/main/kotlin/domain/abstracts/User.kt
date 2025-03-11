package com.example.domain.abstracts

import com.example.domain.Role


abstract class User (
    val FIO: String,
    val phone: String,
    val email: String,
    val password: String,
    val role: Role
)