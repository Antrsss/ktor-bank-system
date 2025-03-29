package com.example.application.facades.requests

import com.example.application.facades.base.RequestFacade
import com.example.application.usecases.base.*
import com.example.domain.entities.requests.CreditRequest

class CreditRequestFacade(
    createRequestUseCase: CreateUseCase<CreditRequest>,
    getRequestUseCase: GetUseCase<CreditRequest>,
    getRequestsByBankUseCase: GetRequestsByBankUseCase<CreditRequest>,
    updateRequestStatusUseCase: UpdateRequestStatusUseCase<CreditRequest>,
    deleteRequestUseCase: DeleteUseCase<CreditRequest>

) : RequestFacade<CreditRequest>(
    createRequestUseCase,
    getRequestUseCase,
    getRequestsByBankUseCase,
    updateRequestStatusUseCase,
    deleteRequestUseCase
)