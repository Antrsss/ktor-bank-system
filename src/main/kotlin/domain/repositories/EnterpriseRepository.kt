package com.example.domain.repositories

import com.example.domain.entities.Enterprise
import com.example.domain.repositories.base.CRUDRepository
import java.util.*

interface EnterpriseRepository: CRUDRepository<Enterprise> {
    suspend fun getEnterprisesByBank(bankUBN: UUID): List<Enterprise>
}