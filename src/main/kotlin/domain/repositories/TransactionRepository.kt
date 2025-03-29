package com.example.domain.repositories

import com.example.domain.entities.Transaction
import com.example.domain.repositories.base.CRUDRepository
import com.example.domain.repositories.base.ImmutableRepository
import java.util.*

interface TransactionRepository: ImmutableRepository<Transaction> {
    suspend fun getTransactionsByAccount(accountId: UUID): List<Transaction>
    suspend fun getTransactionsByBank(bankUBN: UUID): List<Transaction>
}