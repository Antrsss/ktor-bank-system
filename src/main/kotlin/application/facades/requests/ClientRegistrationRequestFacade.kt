package com.example.application.facades.requests

import com.example.application.facades.base.RequestFacade
import com.example.application.usecases.base.*
import com.example.domain.entities.requests.ClientRegistrationRequest

class ClientRegistrationRequestFacade(
    createRequestUseCase: CreateUseCase<ClientRegistrationRequest>,
    getRequestUseCase: GetUseCase<ClientRegistrationRequest>,
    getRequestsByBankUseCase: GetRequestsByBankUseCase<ClientRegistrationRequest>,
    updateRequestStatusUseCase: UpdateRequestStatusUseCase<ClientRegistrationRequest>,
    deleteRequestUseCase: DeleteUseCase<ClientRegistrationRequest>

) : RequestFacade<ClientRegistrationRequest>(
    createRequestUseCase,
    getRequestUseCase,
    getRequestsByBankUseCase,
    updateRequestStatusUseCase,
    deleteRequestUseCase
)