package com.example.domain.abstracts

import com.example.UUIDSerializer
import com.example.domain.LoanObligationStatus
import com.example.domain.LoanPeriod
import com.example.domain.LoanType
import com.example.domain.entities.loan_obligations.Credit
import com.example.domain.entities.loan_obligations.DeferredPayment
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import java.util.UUID

val loanObligationModule = SerializersModule {
    polymorphic(LoanObligation::class) {
        subclass(Credit::class)
        subclass(DeferredPayment::class)
    }
    polymorphic(Request::class) {

    }
    contextual(UUIDSerializer)
}

@Serializable
abstract class LoanObligation(
    @Contextual open val ownerId: UUID,
    @Contextual open val bankUBN: UUID,
    open val amount: Double,
    open val period: LoanPeriod,
    open val individualRate: Double?,
    open var status: LoanObligationStatus,
    open var payedAmount: Double,
    @Contextual open val loanId: UUID,
)