package com.example.application.usecases.loans

import com.example.application.usecases.base.CreateUseCase
import com.example.application.usecases.base.DeleteUseCase
import com.example.application.usecases.base.GetUseCase
import com.example.application.usecases.loan_obligation.*
import com.example.domain.entities.loan_obligations.DeferredPayment
import com.example.domain.repositories.common.LoanRepository


class CreateDeferredPaymentUseCase(
    loanRepository: LoanRepository<DeferredPayment>
) : CreateUseCase<DeferredPayment>(loanRepository)

class GetDeferredPaymentUseCase(
    loanRepository: LoanRepository<DeferredPayment>
) : GetUseCase<DeferredPayment>(loanRepository)

class UpdateDeferredPaymentUseCase(
    loanRepository: LoanRepository<DeferredPayment>
) : UpdateLoanUseCase<DeferredPayment>(loanRepository)

class DeleteDeferredPaymentUseCase(
    loanRepository: LoanRepository<DeferredPayment>
) : DeleteUseCase<DeferredPayment>(loanRepository)

class GetDeferredPaymentsByOwnerUseCase(
    loanRepository: LoanRepository<DeferredPayment>
) : GetLoansByOwnerUseCase<DeferredPayment>(loanRepository)

class GetDeferredPaymentsByBankUseCase(
    loanRepository: LoanRepository<DeferredPayment>
) : GetLoansByBankUseCase<DeferredPayment>(loanRepository)