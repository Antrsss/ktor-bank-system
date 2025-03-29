package com.example.application.facades.loans

import com.example.application.facades.base.LoansFacade
import com.example.application.usecases.loan_obligation.*
import com.example.application.usecases.loans.CreateDeferredPaymentUseCase
import com.example.application.usecases.loans.DeleteDeferredPaymentUseCase
import com.example.application.usecases.loans.GetDeferredPaymentUseCase
import com.example.domain.entities.loan_obligations.DeferredPayment

class DeferredPaymentFacade(
    createLoanUseCase: CreateDeferredPaymentUseCase,
    getLoanUseCase: GetDeferredPaymentUseCase,
    deleteLoanUseCase: DeleteDeferredPaymentUseCase,
    getLoansByOwnerUseCase: GetLoansByOwnerUseCase<DeferredPayment>,
    getLoansByBankUseCase: GetLoansByBankUseCase<DeferredPayment>,
    updateLoanUseCase: UpdateLoanUseCase<DeferredPayment>,

) : LoansFacade<DeferredPayment>(
    createLoanUseCase,
    getLoanUseCase,
    updateLoanUseCase,
    deleteLoanUseCase,
    getLoansByOwnerUseCase,
    getLoansByBankUseCase
)