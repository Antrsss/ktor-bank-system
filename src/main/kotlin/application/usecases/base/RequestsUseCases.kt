package com.example.application.usecases.base

import com.example.domain.RequestStatus
import com.example.domain.abstracts.Request
import com.example.domain.repositories.common.RequestRepository
import java.util.UUID

abstract class GetRequestsByBankUseCase<T : Request>(
    private val repository: RequestRepository<T>
) {
    suspend fun execute(bankUBN: UUID): List<T> {
        return repository.getRequestsByBank(bankUBN)
    }
}

abstract class UpdateRequestStatusUseCase<T: Request>(
    private val repository: RequestRepository<T>
) {
    suspend fun execute(requestId: UUID, requestStatus: RequestStatus): T? {
        val request = repository.get(requestId)
            ?: return null

        request.requestStatus = requestStatus
        return repository.update(request)
    }
}