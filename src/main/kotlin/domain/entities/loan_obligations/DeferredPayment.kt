package com.example.domain.entities.loan_obligations

import com.example.domain.LoanStatus
import com.example.domain.LoanPeriod
import com.example.domain.abstracts.Loan
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import java.util.*
import kotlinx.serialization.*

@Serializable
@SerialName("DeferredPayment")
data class DeferredPayment(
    @Contextual @SerialName("deferred_owner_id") override val ownerId: UUID,
    @Contextual @SerialName("deferred_bank_ubn") override val bankUBN: UUID,
    @SerialName("deferred_amount") override val amount: Double,
    @SerialName("deferred_period") override val period: LoanPeriod,
    @SerialName("deferred_status") override var status: LoanStatus = LoanStatus.IN_PROCESS,
    @SerialName("deferred_payed_amount") override var payedAmount: Double = 0.0,
    @Contextual @SerialName("deferred_loan_id") override val loanId: UUID = UUID.randomUUID(),
) : Loan(ownerId, bankUBN, amount, period, individualRate = null, status, payedAmount, loanId)