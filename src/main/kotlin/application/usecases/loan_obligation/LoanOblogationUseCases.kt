package com.example.application.usecases.loan_obligation

import com.example.domain.LoanObligationStatus
import com.example.domain.abstracts.LoanObligation
import com.example.domain.repositories.LoanObligationRepository
import java.util.*

class CreateLoanUseCase(
    private val loanObligationRepository: LoanObligationRepository
) {
    suspend fun execute(loan: LoanObligation) {
        loanObligationRepository.createLoan(loan)
    }
}

class GetLoanByIdUseCase(
    private val loanObligationRepository: LoanObligationRepository
) {
    suspend fun execute(loanId: UUID): LoanObligation? {
        return loanObligationRepository.getLoanById(loanId)
    }
}

class GetLoanByOwnerUseCase(
    private val loanObligationRepository: LoanObligationRepository
) {
    suspend fun execute(ownerId: UUID): List<LoanObligation> {
        return loanObligationRepository.getLoansByOwner(ownerId)
    }
}

class GetLoansByBankUseCase(
    private val loanObligationRepository: LoanObligationRepository
) {
    suspend fun execute(bankUBN: UUID): List<LoanObligation> {
        return loanObligationRepository.getLoansByBank(bankUBN)
    }
}

class UpdateLoanUseCase(
    private val loanObligationRepository: LoanObligationRepository
) {
    suspend fun execute(loanId: UUID, newStatus: LoanObligationStatus) {
        val loan = loanObligationRepository.getLoanById(loanId)
            ?: throw IllegalArgumentException("Loan not found")

        loan.status = newStatus
        loanObligationRepository.updateLoan(loan)
    }

    suspend fun execute(loanId: UUID, payedAmount: Double) {
        val loan = loanObligationRepository.getLoanById(loanId)
            ?: throw IllegalArgumentException("Loan not found")

        loan.payedAmount = payedAmount
        loanObligationRepository.updateLoan(loan)
    }
}

class DeleteLoanUseCase(
    private val loanObligationRepository: LoanObligationRepository
) {
    suspend fun execute(loanId: UUID): Boolean {
        return loanObligationRepository.deleteLoan(loanId)
    }
}
