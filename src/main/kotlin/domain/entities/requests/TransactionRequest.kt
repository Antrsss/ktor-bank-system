package com.example.domain.entities.requests

import com.example.domain.RequestStatus
import com.example.domain.TransactionType
import java.util.UUID

data class TransactionRequest(
    val senderID: UUID,
    val amount: Double,
    val type: TransactionType,
    val recipientID: UUID,
    val status: RequestStatus = RequestStatus.PENDING,
)