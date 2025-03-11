package com.example.domain.entities

import com.example.domain.TransactionType
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.Instant
import java.util.UUID

@Serializable
data class Transaction(
    @Contextual val senderAccountId: UUID,
    val amount: Double,
    val type: TransactionType,
    @Contextual val bankUBN: UUID,
    @Contextual val receiverAccountId: UUID?,
    @Contextual val transactionId: UUID = UUID.randomUUID(),
)