package com.example.application.usecases.requests

import com.example.application.usecases.base.*
import com.example.domain.entities.requests.ClientRegistrationRequest
import com.example.domain.repositories.common.RequestRepository
import com.example.domain.repositories.base.ImmutableRepository

class CreateClientRegistrationRequestUseCase(
    requestRepository: RequestRepository<ClientRegistrationRequest>
) : CreateUseCase<ClientRegistrationRequest>(requestRepository)

class GetClientRegistrationRequestUseCase(
    requestRepository: RequestRepository<ClientRegistrationRequest>
) : GetUseCase<ClientRegistrationRequest>(requestRepository)

class GetClientRegistrationRequestsByBankUseCase(
    requestRepository: RequestRepository<ClientRegistrationRequest>,
) : GetRequestsByBankUseCase<ClientRegistrationRequest>(requestRepository)

class UpdateClientRegistrationRequestStatusUseCase(
    requestRepository: RequestRepository<ClientRegistrationRequest>
) : UpdateRequestStatusUseCase<ClientRegistrationRequest>(requestRepository)

class DeleteClientRegistrationRequestUseCase(
    requestRepository: RequestRepository<ClientRegistrationRequest>
) : DeleteUseCase<ClientRegistrationRequest>(requestRepository)