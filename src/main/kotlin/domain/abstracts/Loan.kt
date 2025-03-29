package com.example.domain.abstracts

import com.example.domain.LoanStatus
import com.example.domain.LoanPeriod
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
abstract class Loan(
    @Contextual open val ownerId: UUID,
    @Contextual open val bankUBN: UUID,
    open val amount: Double,
    open val period: LoanPeriod,
    open val individualRate: Double?,
    open var status: LoanStatus,
    open var payedAmount: Double,
    @Contextual open val loanId: UUID,
)