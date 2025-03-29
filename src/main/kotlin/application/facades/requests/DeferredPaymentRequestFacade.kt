package com.example.application.facades.requests

import com.example.application.facades.base.RequestFacade
import com.example.application.usecases.base.*
import com.example.domain.entities.requests.DeferredPaymentRequest

class DeferredPaymentRequestFacade(
    createRequestUseCase: CreateUseCase<DeferredPaymentRequest>,
    getRequestUseCase: GetUseCase<DeferredPaymentRequest>,
    getRequestsByBankUseCase: GetRequestsByBankUseCase<DeferredPaymentRequest>,
    updateRequestStatusUseCase: UpdateRequestStatusUseCase<DeferredPaymentRequest>,
    deleteRequestUseCase: DeleteUseCase<DeferredPaymentRequest>

) : RequestFacade<DeferredPaymentRequest>(
    createRequestUseCase,
    getRequestUseCase,
    getRequestsByBankUseCase,
    updateRequestStatusUseCase,
    deleteRequestUseCase
)