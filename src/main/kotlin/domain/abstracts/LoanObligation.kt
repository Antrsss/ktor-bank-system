package com.example.domain.abstracts

import com.example.domain.LoanObligationStatus
import com.example.domain.LoanPeriod
import com.example.domain.LoanType
import com.example.domain.entities.loan_obligations.Credit
import com.example.domain.entities.loan_obligations.DeferredPayment
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import java.util.UUID

val loanObligationModule = SerializersModule {
    polymorphic(LoanObligation::class) {
        subclass(Credit::class)
        subclass(DeferredPayment::class)
    }
}

@Serializable
abstract class LoanObligation(
    @Contextual open val ownerId: UUID,
    @Contextual open val bankUBN: UUID,
    open val amount: Double,
    open val period: LoanPeriod,
    open val individualRate: Double?,
    open val loanType: LoanType,
    open var status: LoanObligationStatus = LoanObligationStatus.IN_PROCESS,
    open var payedAmount: Double = 0.0,
    @Contextual open val loanId: UUID = UUID.randomUUID(),
) {
    /*abstract val moneyPerMonth: Double

    private var _payedMoney: Double = 0.0
    var payedMoney: Double
    */
}