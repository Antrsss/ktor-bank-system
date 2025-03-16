package com.example.application.facades.requests

import com.example.application.usecases.requests.*
import com.example.domain.abstracts.Request
import com.example.domain.entities.requests.ClientRegistrationRequest
import java.util.*

abstract class RequestFacade<T : Request>(
    private val createRequestUseCase: CreateRequestUseCase<T>,
    private val getRequestUseCase: GetRequestUseCase<T>,
    private val getRequestsByBankUseCase: GetRequestsByBankUseCase<T>,
    private val deleteRequestUseCase: DeleteRequestUseCase<T>
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

    suspend fun deleteRequest(requestId: UUID): Boolean {
        return deleteRequestUseCase.execute(requestId)
    }
}