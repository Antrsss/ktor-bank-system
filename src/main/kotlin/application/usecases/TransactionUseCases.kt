package com.example.usecases

import com.example.domain.TransactionType
import com.example.domain.entities.Transaction
import com.example.domain.repositories.TransactionRepository
import java.util.*

class CreateTransactionUseCase(
    private val transactionRepository: TransactionRepository
) {
    suspend fun execute(transaction: Transaction) {
        transactionRepository.createTransaction(transaction)
    }
}

class GetTransactionByIdUseCase(
    private val transactionRepository: TransactionRepository
) {
    suspend fun execute(transactionId: UUID): Transaction? {
        return transactionRepository.getTransactionById(transactionId)
    }
}

class GetTransactionsByAccountUseCase(
    private val transactionRepository: TransactionRepository
) {
    suspend fun execute(accountId: UUID): List<Transaction> {
        return transactionRepository.getTransactionsByAccount(accountId)
    }
}

class GetTransactionsByBankUseCase(
    private val transactionRepository: TransactionRepository
) {
    suspend fun execute(bankUBN: UUID): List<Transaction> {
        return transactionRepository.getTransactionsByBank(bankUBN)
    }
}

class DeleteTransactionUseCase(
    private val transactionRepository: TransactionRepository
) {
    suspend fun execute(transactionId: UUID): Boolean {
        val transaction = transactionRepository.getTransactionById(transactionId)
            ?: throw IllegalArgumentException("Transaction not found")

        if (transaction.type != TransactionType.WITHDRAWAL) {
            transactionRepository.deleteTransaction(transactionId)
            return true
        }
        else {
            return false
        }
    }
}