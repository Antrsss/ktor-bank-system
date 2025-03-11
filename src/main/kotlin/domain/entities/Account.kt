package com.example.domain.entities

import com.example.domain.AccountStatus
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Account(
    val accountName: String,
    @Contextual val ownerId: UUID,
    @Contextual val bankUBN: UUID,
    val balance: Double = 0.0,
    val status: AccountStatus = AccountStatus.ACTIVE,
    @Contextual val accountId: UUID = UUID.randomUUID(),
)