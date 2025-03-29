package com.example.application.usecases.loans

import com.example.application.usecases.base.CreateUseCase
import com.example.application.usecases.base.DeleteUseCase
import com.example.application.usecases.base.GetUseCase
import com.example.application.usecases.loan_obligation.*
import com.example.domain.entities.loan_obligations.Credit
import com.example.domain.repositories.common.LoanRepository

class CreateCreditUseCase(
    loanRepository: LoanRepository<Credit>
) : CreateUseCase<Credit>(loanRepository)

class GetCreditUseCase(
    loanRepository: LoanRepository<Credit>
) : GetUseCase<Credit>(loanRepository)

class DeleteCreditUseCase(
    loanRepository: LoanRepository<Credit>
) : DeleteUseCase<Credit>(loanRepository)

class UpdateCreditUseCase(
    crudRepository: LoanRepository<Credit>
) : UpdateLoanUseCase<Credit>(crudRepository)

class GetCreditsByOwnerUseCase(
    loanRepository: LoanRepository<Credit>
) : GetLoansByOwnerUseCase<Credit>(loanRepository)

class GetCreditsByBankUseCase(
    loanRepository: LoanRepository<Credit>
) : GetLoansByBankUseCase<Credit>(loanRepository)