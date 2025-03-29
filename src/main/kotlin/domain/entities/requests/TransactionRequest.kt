package com.example.domain.entities.requests

import com.example.domain.RequestStatus
import com.example.domain.TransactionType
import com.example.domain.abstracts.Request
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
@SerialName("TransactionRequest")
data class TransactionRequest(
    @Contextual @SerialName("transaction_request_bankUBN") override val bankUBN: UUID,
    @Contextual val applicantId: UUID,
    val amount: Double,
    val transactionType: TransactionType,
    @Contextual val recipientId: UUID,
    @SerialName("transact_request_status") override var requestStatus: RequestStatus = RequestStatus.PENDING,
    @Contextual @SerialName("transact_request_id") override val requestId: UUID = UUID.randomUUID(),
) : Request(bankUBN, requestStatus, requestId)