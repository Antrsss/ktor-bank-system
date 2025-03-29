package com.example.application.facades.base

import com.example.application.usecases.base.*
import com.example.domain.RequestStatus
import com.example.domain.abstracts.Request
import com.example.domain.entities.requests.ClientRegistrationRequest
import java.util.*

abstract class RequestFacade<T : Request>(
    private val createRequestUseCase: CreateUseCase<T>,
    private val getRequestUseCase: GetUseCase<T>,
    private val getRequestsByBankUseCase: GetRequestsByBankUseCase<T>,
    private val updateRequestStatusUseCase: UpdateRequestStatusUseCase<T>,
    private val deleteRequestUseCase: DeleteUseCase<T>
) {

    suspend fun createRequest(request: T) {
        createRequestUseCase.execute(request)
    }

    suspend fun getRequest(requestId: UUID): T? {
        return getRequestUseCase.execute(requestId)
    }

    suspend fun getRequestsByBank(bankUBN: UUID): List<T> {
        return getRequestsByBankUseCase.execute(bankUBN)
    }

    suspend fun updateRequestStatus(requestId: UUID, requestStatus: RequestStatus): T? {
        return updateRequestStatusUseCase.execute(requestId, requestStatus)
    }

    suspend fun deleteRequest(requestId: UUID): Boolean {
        return deleteRequestUseCase.execute(requestId)
    }
}