package com.example.application.usecases.loan_obligation

import com.example.domain.LoanObligationStatus
import com.example.domain.abstracts.LoanObligation
import java.util.UUID

class LoanObligationFacade(
    private val createLoanUseCase: CreateLoanUseCase,
    private val getLoanByIdUseCase: GetLoanByIdUseCase,
    private val getLoansByOwnerUseCase: GetLoanByOwnerUseCase,
    private val getLoansByBankUseCase: GetLoansByBankUseCase,
    private val updateLoanUseCase: UpdateLoanUseCase,
    private val deleteLoanUseCase: DeleteLoanUseCase
) {
    suspend fun createLoan(loan: LoanObligation) {
        createLoanUseCase.execute(loan)
    }

    suspend fun getLoanById(loanId: UUID): LoanObligation? {
        return getLoanByIdUseCase.execute(loanId)
    }

    suspend fun getLoansByOwner(ownerId: UUID): List<LoanObligation> {
        return getLoansByOwnerUseCase.execute(ownerId)
    }

    suspend fun getLoansByBank(bankUBN: UUID): List<LoanObligation> {
        return getLoansByBankUseCase.execute(bankUBN)
    }

    suspend fun updateLoanStatus(loanId: UUID, newStatus: LoanObligationStatus) {
        updateLoanUseCase.execute(loanId, newStatus)
    }

    suspend fun payForLoan(loanId: UUID, amountToPay: Double) {
        updateLoanUseCase.execute(loanId, amountToPay)
    }

    suspend fun deleteLoan(loanId: UUID): Boolean {
        return deleteLoanUseCase.execute(loanId)
    }
}
