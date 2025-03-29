package com.example.domain.entities.loan_obligations

import com.example.domain.LoanStatus
import com.example.domain.LoanPeriod
import com.example.domain.abstracts.Loan
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID
/*
// принимает то, что нужно валидировать
interface CreditOutside {
    isEmailUnique(string email) (bool, error)
}

interface CreditValidator {
    fun validate();
}

//принимает реализацию interface CreditOutside
class FullValidationPolicy : CreditValidator {
    bool CreditValidator()
}

//post запрсы
class ZeroValidationPolicy : CreditValidator {

}*/

@Serializable
@SerialName("Credit")
data class Credit(
    @Contextual @SerialName("credit_owner_id") override val ownerId: UUID,
    @Contextual @SerialName("credit_bank_ubn") override val bankUBN: UUID,
    @SerialName("credit_amount") override val amount: Double,
    @SerialName("credit_period") override val period: LoanPeriod,
    @SerialName("credit_individual_rate") override val individualRate: Double?,
    @SerialName("credit_status") override var status: LoanStatus = LoanStatus.IN_PROCESS,
    @SerialName("credit_payed_amount") override var payedAmount: Double = 0.0,
    @Contextual @SerialName("credit_loan_id") override val loanId: UUID = UUID.randomUUID(),
) : Loan(ownerId, bankUBN, amount, period, individualRate, status, payedAmount, loanId)
