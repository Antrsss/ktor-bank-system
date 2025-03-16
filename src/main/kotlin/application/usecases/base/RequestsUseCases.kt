package com.example.application.usecases.base

import com.example.domain.abstracts.Request
import com.example.domain.repositories.RequestRepository
import java.util.UUID

abstract class CreateRequestUseCase<T : Request>(
    private val repository: RequestRepository<T>
) {
    suspend fun execute(request: T) {
        repository.createRequest(request)
    }
}

abstract class GetRequestUseCase<T : Request>(
    private val repository: RequestRepository<T>
) {
    suspend fun execute(requestId: UUID): T? {
        return repository.getRequest(requestId)
    }
}

abstract class GetRequestsByBankUseCase<T : Request>(
    private val repository: RequestRepository<T>
) {
    suspend fun execute(bankUBN: UUID): List<T> {
        return repository.getRequestsByBank(bankUBN)
    }
}

abstract class DeleteRequestUseCase<T : Request>(
    private val repository: RequestRepository<T>
) {
    suspend fun execute(requestId: UUID): Boolean {
        return repository.deleteRequest(requestId)
    }
}