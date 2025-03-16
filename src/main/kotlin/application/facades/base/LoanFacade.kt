package com.example.application.facades.loans

import com.example.application.usecases.CreateUseCase
import com.example.application.usecases.DeleteUseCase
import com.example.application.usecases.GetUseCase
import com.example.application.usecases.loan_obligation.*
import com.example.domain.LoanStatus
import com.example.domain.abstracts.Loan
import java.util.*

abstract class LoanFacade<T : Loan>(
    private val createLoanUseCase: CreateUseCase<T>,
    private val getLoanUseCase: GetUseCase<T>,
    private val updateLoanUseCase: UpdateLoanUseCase<T>,
    private val deleteLoanUseCase: DeleteUseCase<T>,
    private val getLoansByOwnerUseCase: GetLoansByOwnerUseCase<T>,
    private val getLoansByBankUseCase: GetLoansByBankUseCase<T>,
) {
    suspend fun createLoan(loan: T): Unit {
        return createLoanUseCase.execute(loan)
    }

    suspend fun getLoan(loanId: UUID): T? {
        return getLoanUseCase.execute(loanId)
    }

    suspend fun updateLoanStatus(loanId: UUID, newStatus: LoanStatus): T? {
        return updateLoanUseCase.execute(loanId, newStatus)
    }

    suspend fun payForLoan(loanId: UUID, amountToPay: Double): T? {
        return updateLoanUseCase.execute(loanId, amountToPay)
    }

    suspend fun deleteLoan(loanId: UUID): Boolean {
        return deleteLoanUseCase.execute(loanId)
    }



    suspend fun getLoansByOwner(ownerId: UUID): List<T> {
        return getLoansByOwnerUseCase.execute(ownerId)
    }

    suspend fun getLoansByBank(bankId: UUID): List<T> {
        return getLoansByBankUseCase.execute(bankId)
    }
}