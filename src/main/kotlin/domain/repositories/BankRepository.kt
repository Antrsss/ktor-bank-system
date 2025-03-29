package com.example.domain.repositories

import com.example.domain.entities.Bank
import com.example.domain.repositories.base.CRUDRepository

interface BankRepository: CRUDRepository<Bank> {
    suspend fun getAllBanks(): List<Bank>
}