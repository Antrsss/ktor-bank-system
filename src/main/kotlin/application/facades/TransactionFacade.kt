package com.example.application.facades

import com.example.domain.entities.Transaction
import com.example.usecases.*
import java.util.*

class TransactionFacade(
    private val createTransactionUseCase: CreateTransactionUseCase,
    private val getTransactionUseCase: GetTransactionUseCase,
    private val getTransactionsByAccountUseCase: GetTransactionsByAccountUseCase,
    private val getTransactionsByBankUseCase: GetTransactionsByBankUseCase,
    private val deleteTransactionUseCase: DeleteTransactionUseCase,
) {
    suspend fun createTransaction(transaction: Transaction) {
        createTransactionUseCase.execute(transaction)
    }

    suspend fun getTransaction(transactionId: UUID): Transaction? {
        return getTransactionUseCase.execute(transactionId)
    }

    suspend fun getTransactionsByAccount(accountId: UUID): List<Transaction> {
        return getTransactionsByAccountUseCase.execute(accountId)
    }

    suspend fun getTransactionsByBank(bank: UUID): List<Transaction> {
        return getTransactionsByBankUseCase.execute(bank)
    }

    suspend fun deleteTransaction(transactionId: UUID): Boolean {
        return deleteTransactionUseCase.execute(transactionId)
    }
}