package com.example.application.facades.loans

import com.example.application.facades.base.LoansFacade
import com.example.application.usecases.loan_obligation.GetLoansByBankUseCase
import com.example.application.usecases.loan_obligation.GetLoansByOwnerUseCase
import com.example.application.usecases.loan_obligation.UpdateLoanUseCase
import com.example.application.usecases.loans.CreateCreditUseCase
import com.example.application.usecases.loans.DeleteCreditUseCase
import com.example.application.usecases.loans.GetCreditUseCase
import com.example.domain.entities.loan_obligations.Credit

class CreditFacade(
    createLoanUseCase: CreateCreditUseCase,
    getLoanUseCase: GetCreditUseCase,
    deleteLoanUseCase: DeleteCreditUseCase,
    getLoansByOwnerUseCase: GetLoansByOwnerUseCase<Credit>,
    getLoansByBankUseCase: GetLoansByBankUseCase<Credit>,
    updateLoanUseCase: UpdateLoanUseCase<Credit>,

) : LoansFacade<Credit>(
    createLoanUseCase,
    getLoanUseCase,
    updateLoanUseCase,
    deleteLoanUseCase,
    getLoansByOwnerUseCase,
    getLoansByBankUseCase
)