package com.example.application.usecases.loan_obligation

import com.example.domain.LoanObligationStatus
import com.example.domain.entities.loan_obligations.Credit
import com.example.usecases.*
import java.util.*

/*
class CreditFacade(
    private val createCreditUseCase: CreateCreditUseCase,
    private val getCreditUseCase: GetCreditUseCase,
    private val getCreditsByOwner: GetCreditsByOwnerId,
    private val getCreditsByBankUseCase: GetCreditsByBankUseCase,
    private val updateCreditUseCase: UpdateCreditUseCase,
    private val deleteCreditUseCase: DeleteCreditUseCase
) {
    suspend fun createCredit(credit: Credit) {
        createCreditUseCase.execute(credit)
    }

    suspend fun getCreditById(creditId: UUID): Credit? {
        return getCreditUseCase.execute(creditId)
    }

    suspend fun getCreditsByOwner(ownerId: UUID): List<Credit> {
        return getCreditsByOwner.execute(ownerId)
    }

    suspend fun getCreditsByBank(bankUBN: UUID): List<Credit> {
        return getCreditsByBankUseCase.execute(bankUBN)
    }

    suspend fun updateCreditStatus(creditId: UUID, newStatus: LoanObligationStatus) {
        updateCreditUseCase.execute(creditId, newStatus)
    }

    suspend fun payForCredit(creditId: UUID, amountToPay: Double) {
        updateCreditUseCase.execute(creditId, amountToPay)
    }

    suspend fun deleteCredit(creditId: UUID): Boolean {
        return deleteCreditUseCase.execute(creditId)
    }
}*/
