package com.example.domain.entities.users

import com.example.domain.Role
import com.example.domain.abstracts.User
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
@SerialName("OutsideSpecialist")
data class OutsideSpecialist(
    @SerialName("outsideSpecialist_fio") override val FIO: String,
    @SerialName("outsideSpecialist_enterpriseId") @Contextual val enterpriseId: UUID,
    @SerialName("outsideSpecialist_phone") override val phone: String,
    @SerialName("outsideSpecialist_email") override var email: String,
    @SerialName("outsideSpecialist_password") override val password: String,
    @SerialName("outsideSpecialist_bankUBN") @Contextual override val bankUBN: UUID = UUID.randomUUID(),
    @SerialName("outsideSpecialist_userId") @Contextual override val userId: UUID = UUID.randomUUID(),
) : User(FIO, phone, email, password, bankUBN, userId, Role.OUTSIDE_SPECIALIST)