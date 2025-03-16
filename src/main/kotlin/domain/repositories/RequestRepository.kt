package com.example.domain.repositories.requests

import com.example.domain.abstracts.Request
import com.example.domain.entities.requests.ClientRegistrationRequest
import java.util.*

interface RequestRepository<T : Request> {
    suspend fun createRequest(request: T)
    suspend fun getRequest(requestId: UUID): T?
    suspend fun getRequestsByBank(bankUBN: UUID): List<T>
    suspend fun deleteRequest(requestId: UUID): Boolean
}