package com.example.domain.entities.requests

import com.example.domain.LoanPeriod
import com.example.domain.RequestStatus
import com.example.domain.entities.Account
import java.util.UUID

data class CreditRequest(
    val applicantID: UUID,
    val amount: Double,
    val period: LoanPeriod,
    val individualRate: Double? = null,
    val status: RequestStatus = RequestStatus.PENDING,
)