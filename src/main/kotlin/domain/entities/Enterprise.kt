package com.example.domain.entities

import com.example.domain.EnterpriseType
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Enterprise(
    val legalName: String,
    val enterpriseType: EnterpriseType,
    val enterprisePAN: Long,
    @Contextual val bankUBN: UUID,
    val legalAdress: String,
    @Contextual val enterpriseId: UUID = UUID.randomUUID()
)
