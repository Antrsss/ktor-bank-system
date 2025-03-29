package com.example.domain.entities.users

import com.example.domain.Role
import com.example.domain.abstracts.User
import com.example.domain.entities.Transaction
import com.example.domain.entities.requests.*
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
@SerialName("Manager")
data class Manager(
    @SerialName("manager_fio") override val FIO: String,
    @SerialName("manager_phone")override val phone: String,
    @SerialName("manager_email") override var email: String,
    @SerialName("manager_password") override val password: String,
    @SerialName("manager_bankUBN") @Contextual override val bankUBN: UUID = UUID.randomUUID(),
    @SerialName("manager_userId") @Contextual override val userId: UUID = UUID.randomUUID(),
) : User(FIO, phone, email, password, bankUBN, userId, Role.MANAGER)