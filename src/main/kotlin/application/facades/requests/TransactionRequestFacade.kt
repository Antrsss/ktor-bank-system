package com.example.application.facades.requests

import com.example.application.facades.base.RequestFacade
import com.example.application.usecases.base.*
import com.example.domain.entities.requests.TransactionRequest

class TransactionRequestFacade(
    createRequestUseCase: CreateUseCase<TransactionRequest>,
    getRequestUseCase: GetUseCase<TransactionRequest>,
    getRequestsByBankUseCase: GetRequestsByBankUseCase<TransactionRequest>,
    updateRequestStatusUseCase: UpdateRequestStatusUseCase<TransactionRequest>,
    deleteRequestUseCase: DeleteUseCase<TransactionRequest>

) : RequestFacade<TransactionRequest>(
    createRequestUseCase,
    getRequestUseCase,
    getRequestsByBankUseCase,
    updateRequestStatusUseCase,
    deleteRequestUseCase
)