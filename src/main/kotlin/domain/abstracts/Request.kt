package com.example.domain.abstracts

import com.example.domain.RequestStatus
import java.util.*

abstract class Request(
    val status: RequestStatus = RequestStatus.PENDING,
    val requestId: UUID = UUID.randomUUID(),
) {
}