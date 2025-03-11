package com.example.domain.entities

import com.example.domain.SalaryProjectStatus
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class SalaryProject(
    @Contextual val bankUBN: UUID,
    @Contextual val enterpriseId: UUID,
    val status: SalaryProjectStatus,
    @Contextual val salaryProjectId: UUID = UUID.randomUUID()
)