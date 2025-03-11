package com.example.domain.repositories

import com.example.domain.abstracts.LoanObligation
import com.example.domain.entities.loan_obligations.Credit
import com.example.domain.entities.loan_obligations.DeferredPayment
import java.util.*

/*
interface CreditRepository {
    suspend fun createCredit(credit: Credit)
    suspend fun getCreditById(creditId: UUID): Credit?
    suspend fun getCreditsByOwner(ownerId: UUID): List<Credit>
    suspend fun getCreditsByBank(loanId: UUID): List<Credit>
    suspend fun updateCredit(credit: Credit)
    suspend fun deleteCredit(creditId: UUID): Boolean
}

interface DeferredPaymentRepository {
    suspend fun createDeferredPayment(payment: DeferredPayment)
    suspend fun getDeferredPaymentById(paymentId: UUID): DeferredPayment?
    suspend fun getDeferredPaymentsByOwner(ownerId: UUID): List<DeferredPayment>
    suspend fun getDeferredPaymentsByBank(bankUBN: UUID): List<DeferredPayment>
    suspend fun updateDeferredPayment(payment: DeferredPayment)
    suspend fun deleteDeferredPayment(paymentId: UUID): Boolean
}*/

interface LoanObligationRepository {
    suspend fun createLoan(loan: LoanObligation)
    suspend fun getLoanById(loanId: UUID): LoanObligation?
    suspend fun getLoansByOwner(loanId: UUID): List<LoanObligation>
    suspend fun getLoansByBank(bankUBN: UUID): List<LoanObligation>
    suspend fun updateLoan(loan: LoanObligation)
    suspend fun deleteLoan(loanId: UUID): Boolean
}