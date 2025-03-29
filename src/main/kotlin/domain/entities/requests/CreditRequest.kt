package com.example.domain.entities.requests

import com.example.domain.LoanPeriod
import com.example.domain.RequestStatus
import com.example.domain.abstracts.Request
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
@SerialName("CreditRequest")
data class CreditRequest(
    @Contextual @SerialName("credit_request_bankUBN") override val bankUBN: UUID,
    @Contextual val applicantId: UUID,
    val amount: Double,
    val period: LoanPeriod,
    val individualRate: Double? = null,
    @SerialName("credit_request_status") override var requestStatus: RequestStatus = RequestStatus.PENDING,
    @Contextual @SerialName("credit_request_id") override val requestId: UUID = UUID.randomUUID(),
) : Request(bankUBN, requestStatus, requestId)