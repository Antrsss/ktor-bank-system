package com.example.domain.abstracts

import com.example.domain.RequestStatus
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.*


@Serializable
abstract class Request(
    @Contextual open val bankUBN: UUID,
    open var requestStatus: RequestStatus = RequestStatus.PENDING,
    @Contextual open val requestId: UUID = UUID.randomUUID(),
)