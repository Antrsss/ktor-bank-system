package com.example.application.usecases.transaction

import com.example.domain.entities.Transaction
import com.example.usecases.*
import java.util.*

class TransactionFacade(
    private val createTransactionUseCase: CreateTransactionUseCase,
    private val getTransactionByIdUseCase: GetTransactionByIdUseCase,
    private val getTransactionsByAccountUseCase: GetTransactionsByAccountUseCase,
    private val getTransactionsByBankUseCase: GetTransactionsByBankUseCase,
    private val deleteTransactionUseCase: DeleteTransactionUseCase,
) {
    suspend fun createTransaction(transaction: Transaction) {
        createTransactionUseCase.execute(transaction)
    }

    suspend fun getTransaction(transactionId: UUID): Transaction? {
        return getTransactionByIdUseCase.execute(transactionId)
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