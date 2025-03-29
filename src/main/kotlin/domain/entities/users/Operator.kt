package com.example.domain.entities.users

import com.example.domain.Role
import com.example.domain.abstracts.User
import com.example.domain.entities.requests.SalaryProjectRequest
import com.example.domain.entities.requests.TransactionRequest
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
@SerialName("Operator")
data class Operator(
    @SerialName("operator_fio") override val FIO: String,
    @SerialName("operator_phone") override val phone: String,
    @SerialName("operator_email") override var email: String,
    @SerialName("operator_password") override val password: String,
    @SerialName("operator_bankUBN") @Contextual override val bankUBN: UUID = UUID.randomUUID(),
    @SerialName("operator_userId") @Contextual override val userId: UUID = UUID.randomUUID(),
) : User(FIO, phone, email, password, bankUBN, userId, Role.OPERATOR)