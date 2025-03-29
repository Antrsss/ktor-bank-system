package com.example.application.usecases.requests

import com.example.application.usecases.base.*
import com.example.domain.entities.requests.TransactionRequest
import com.example.domain.repositories.common.RequestRepository
import com.example.domain.repositories.base.ImmutableRepository

class CreateTransactionRequestUseCase(
    repository: RequestRepository<TransactionRequest>
) : CreateUseCase<TransactionRequest>(repository)

class GetTransactionRequestUseCase(
    repository: RequestRepository<TransactionRequest>
) : GetUseCase<TransactionRequest>(repository)

class GetTransactionRequestsByBankUseCase(
    repository: RequestRepository<TransactionRequest>
) : GetRequestsByBankUseCase<TransactionRequest>(repository)

class UpdateTransactionRequestStatusUseCase(
    repository: RequestRepository<TransactionRequest>
) : UpdateRequestStatusUseCase<TransactionRequest>(repository)

class DeleteTransactionRequestUseCase(
    repository: RequestRepository<TransactionRequest>
) : DeleteUseCase<TransactionRequest>(repository)