package com.example.application.usecases.requests

import com.example.application.usecases.base.*
import com.example.domain.entities.requests.CreditRequest
import com.example.domain.repositories.common.RequestRepository
import com.example.domain.repositories.base.ImmutableRepository

class CreateCreditRequestUseCase(
    requestRepository: RequestRepository<CreditRequest>
) : CreateUseCase<CreditRequest>(requestRepository)

class GetCreditRequestUseCase(
    requestRepository: RequestRepository<CreditRequest>
) : GetUseCase<CreditRequest>(requestRepository)

class GetCreditRequestsByBankUseCase(
    requestRepository: RequestRepository<CreditRequest>,
) : GetRequestsByBankUseCase<CreditRequest>(requestRepository)

class UpdateCreditRequestStatusUseCase(
    requestRepository: RequestRepository<CreditRequest>
) : UpdateRequestStatusUseCase<CreditRequest>(requestRepository)

class DeleteCreditRequestUseCase(
    requestRepository: RequestRepository<CreditRequest>
) : DeleteUseCase<CreditRequest>(requestRepository)