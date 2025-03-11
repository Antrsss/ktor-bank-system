package com.example.domain.repositories

import com.example.domain.entities.Account
import com.example.domain.entities.Enterprise
import java.util.*

interface EnterpriseRepository {
    suspend fun createEnterprise(enterprise: Enterprise)
    suspend fun getEnterpriseById(enterpriseId: UUID): Enterprise?
    suspend fun getAllBankEnterprises(bankUBN: UUID): List<Enterprise>
    suspend fun updateEnterprise(enterprise: Enterprise)
    suspend fun deleteEnterprise(enterpriseId: UUID): Boolean
}