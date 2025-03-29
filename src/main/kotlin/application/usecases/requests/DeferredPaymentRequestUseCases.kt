package com.example.application.usecases.requests

import com.example.application.usecases.base.*
import com.example.domain.entities.requests.DeferredPaymentRequest
import com.example.domain.repositories.common.RequestRepository
import com.example.domain.repositories.base.ImmutableRepository

class CreateDeferredPaymentRequestUseCase(
    repository: RequestRepository<DeferredPaymentRequest>
) : CreateUseCase<DeferredPaymentRequest>(repository)

class GetDeferredPaymentRequestUseCase(
    repository: RequestRepository<DeferredPaymentRequest>
) : GetUseCase<DeferredPaymentRequest>(repository)

class GetDeferredPaymentRequestsByBankUseCase(
    repository: RequestRepository<DeferredPaymentRequest>
) : GetRequestsByBankUseCase<DeferredPaymentRequest>(repository)

class UpdateDeferredPaymentRequestStatusUseCase(
    repository: RequestRepository<DeferredPaymentRequest>
) : UpdateRequestStatusUseCase<DeferredPaymentRequest>(repository)

class DeleteDeferredPaymentRequestUseCase(
    repository: RequestRepository<DeferredPaymentRequest>
) : DeleteUseCase<DeferredPaymentRequest>(repository)