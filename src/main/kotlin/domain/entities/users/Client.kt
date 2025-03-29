package com.example.domain.entities.users

import com.example.domain.Role
import com.example.domain.abstracts.User
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
@SerialName("Client")
data class Client(
    @SerialName("client_fio") override val FIO: String,
    @SerialName("client_passportSeries") val passportSeries: String,
    @SerialName("client_passportNumber") val passportNumber: Int,
    @SerialName("client_phone") override val phone: String,
    @SerialName("client_email") override var email: String,
    @SerialName("client_password") override val password: String,
    @SerialName("client_bankUBN") @Contextual override val bankUBN: UUID = UUID.randomUUID(),
    @SerialName("client_userId") @Contextual override val userId: UUID = UUID.randomUUID(),
) : User(FIO,  phone, email, password, bankUBN, userId, Role.CLIENT)
