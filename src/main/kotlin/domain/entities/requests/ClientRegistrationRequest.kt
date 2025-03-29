package com.example.domain.entities.requests

import com.example.domain.ClientCountry
import com.example.domain.RequestStatus
import com.example.domain.abstracts.Request
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
@SerialName("ClientRegistrationRequest")
data class ClientRegistrationRequest(
    @Contextual @SerialName("client_reg_request_bankUBN") override val bankUBN: UUID,
    val FIO: String,
    val passportSeries: String,
    val passportNumber: Int,
    val phone: String,
    val email: String,
    val password: String,
    val country: ClientCountry? = null,
    @SerialName("client_reg_request_status") override var requestStatus: RequestStatus = RequestStatus.PENDING,
    @Contextual @SerialName("client_reg_request_id") override val requestId: UUID = UUID.randomUUID(),
) : Request(bankUBN, requestStatus, requestId)