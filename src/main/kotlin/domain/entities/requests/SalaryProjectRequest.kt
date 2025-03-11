package com.example.domain.entities.requests

import com.example.domain.RequestStatus
import java.util.*

data class SalaryProjectRequest(
    val outsideSpecialistID: UUID,
    val enterpriseID: UUID,
    val status: RequestStatus
)
