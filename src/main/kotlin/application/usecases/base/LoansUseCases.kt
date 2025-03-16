package com.example.application.usecases.loan_obligation

import com.example.domain.LoanStatus
import com.example.domain.abstracts.Loan
import com.example.domain.repositories.common.CRUDRepository
import com.example.domain.repositories.LoanRepository
import java.util.*

abstract class UpdateLoanUseCase<T : Loan>(
    private val crudRepository: CRUDRepository<T>
) {
    suspend fun execute(loanId: UUID, newStatus: LoanStatus): T? {
        val loan = crudRepository.get(loanId)
            ?: return null

        loan.status = newStatus
        return crudRepository.update(loan)
    }

    suspend fun execute(loanId: UUID, amountToPay: Double): T? {
        val loan = crudRepository.get(loanId)
            ?: return null

        loan.payedAmount += amountToPay
        return crudRepository.update(loan)
    }
}



abstract class GetLoansByOwnerUseCase<T : Loan>(
    private val loanRepository: LoanRepository<T>
) {
    suspend fun execute(ownerId: UUID): List<T> {
        return loanRepository.getLoansByOwner(ownerId)
    }
}

abstract class GetLoansByBankUseCase<T : Loan>(
    private val loanRepository: LoanRepository<T>
) {
    suspend fun execute(bankUBN: UUID): List<T> {
        return loanRepository.getLoansByBank(bankUBN)
    }
}