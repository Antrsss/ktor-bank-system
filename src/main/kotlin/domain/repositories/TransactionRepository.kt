package com.example.domain.repositories

import com.example.domain.entities.Transaction
import java.util.*

interface TransactionRepository {
    suspend fun createTransaction(transaction: Transaction)
    suspend fun getTransactionById(transactionId: UUID): Transaction?
    suspend fun getTransactionsByAccount(accountId: UUID): List<Transaction>
    suspend fun getTransactionsByBank(bankUBN: UUID): List<Transaction>
    suspend fun deleteTransaction(transactionId: UUID): Boolean
}