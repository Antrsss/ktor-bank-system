package com.example.usecases

import com.example.application.usecases.base.*
import com.example.domain.entities.Transaction
import com.example.domain.repositories.TransactionRepository
import com.example.domain.repositories.base.ImmutableRepository
import java.util.*

class CreateTransactionUseCase(
    transactionRepository: TransactionRepository
) : ImmutableCreateUseCase<Transaction>(transactionRepository)

class GetTransactionUseCase(
    transactionRepository: TransactionRepository
) : ImmutableGetUseCase<Transaction>(transactionRepository)

class DeleteTransactionUseCase(
    transactionRepository: ImmutableRepository<Transaction>
) : ImmutableDeleteUseCase<Transaction>(transactionRepository)

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