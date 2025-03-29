package com.example.domain.repositories

import com.example.domain.entities.Account
import com.example.domain.repositories.base.CRUDRepository
import java.util.*

interface AccountRepository: CRUDRepository<Account> {
    suspend fun getAccountsByOwner(ownerId: UUID): List<Account>
    suspend fun getAccountsByBank(bankUBN: UUID): List<Account>
}