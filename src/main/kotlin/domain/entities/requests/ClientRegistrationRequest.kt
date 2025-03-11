package com.example.domain.entities.requests

import com.example.domain.RequestStatus

data class ClientRegistrationRequest(
    val FIO: String,
    val passportSeries: String,
    val passportNumber: Int,
    val phone: String,
    val email: String,
    val password: String,
    val status: RequestStatus = RequestStatus.PENDING,
)