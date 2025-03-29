package com.example.domain.entities.users

import com.example.domain.ClientCountry
import com.example.domain.Role
import com.example.domain.abstracts.User
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
@SerialName("ForeignClient")
data class ForeignClient(
    @SerialName("foreign_client_fio") override val FIO: String,
    @SerialName("f_client_passportSeries") val passportSeries: String,
    @SerialName("f_client_passportNumber") val passportNumber: Int,
    @SerialName("f_client_phone") override val phone: String,
    @SerialName("f_client_email") override var email: String,
    @SerialName("f_client_password") override val password: String,
    val country: ClientCountry,
    @SerialName("f_client_bankUBN") @Contextual override val bankUBN: UUID = UUID.randomUUID(),
    @SerialName("f_client_userId") @Contextual override val userId: UUID = UUID.randomUUID(),
) : User(FIO, phone, email, password, bankUBN, userId, role = Role.FOREIGN_CLIENT)