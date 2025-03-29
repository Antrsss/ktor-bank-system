package com.example.domain.entities.requests

import com.example.domain.RequestStatus
import com.example.domain.abstracts.Request
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
@SerialName("SalaryProjectRequest")
data class SalaryProjectRequest(
    @Contextual @SerialName("sal_project_request_bankUBN") override val bankUBN: UUID,
    @Contextual val applicantId: UUID,
    @Contextual val enterpriseId: UUID,
    @SerialName("sal_project_request_status") override var requestStatus: RequestStatus,
    @Contextual @SerialName("sal_project_request_id") override val requestId: UUID,
) : Request(bankUBN, requestStatus, requestId)
