package com.example.domain.entities.users

import com.example.domain.ActionLog
import com.example.domain.Role
import com.example.domain.abstracts.User
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
@SerialName("Admin")
data class Admin(
    @SerialName("admin_fio") override val FIO: String,
    @SerialName("admin_phone") override val phone: String,
    @SerialName("admin_email") override var email: String,
    @SerialName("admin_password") override val password: String,
    @SerialName("admin_bankUBN") @Contextual override val bankUBN: UUID = UUID.randomUUID(),
    @SerialName("admin_userId") @Contextual override val userId: UUID = UUID.randomUUID()
) : User(FIO, phone, email, password, bankUBN, userId, Role.ADMIN)