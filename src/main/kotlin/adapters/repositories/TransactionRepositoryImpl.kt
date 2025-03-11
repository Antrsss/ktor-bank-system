package com.example.adapters.repositories

import com.example.domain.TransactionType
import com.example.domain.entities.Transaction
import com.example.domain.repositories.SalaryProjectRepository
import com.example.domain.repositories.TransactionRepository
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

object TransactionsTable : IntIdTable("transactions") {
    val senderAccountId = uuid("sender_account_id")
    val amount = double("amount")
    val type = enumerationByName("type", 20, TransactionType::class)
    val bankUBN = uuid("bank_ubn")
    val receiverAccountId = uuid("receiver_account_id").nullable()
    val transactionId = uuid("transaction_id")
}

fun ResultRow.toTransaction(): Transaction {
    return Transaction(
        senderAccountId = this[TransactionsTable.senderAccountId],
        amount = this[TransactionsTable.amount],
        type = this[TransactionsTable.type],
        bankUBN = this[TransactionsTable.bankUBN],
        receiverAccountId = this[TransactionsTable.receiverAccountId],
        transactionId = this[TransactionsTable.transactionId],
    )
}

class TransactionsDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TransactionsDAO>(TransactionsTable)

    var senderAccountId by TransactionsTable.senderAccountId
    var amount by TransactionsTable.amount
    var type by TransactionsTable.type
    var bankUBN by TransactionsTable.bankUBN
    var receiverAccountId by TransactionsTable.receiverAccountId
    var transactionId by TransactionsTable.transactionId
}

// Преобразование DAO в модель
fun daoToModel(dao: TransactionsDAO) = Transaction(
    dao.senderAccountId,
    dao.amount,
    dao.type,
    dao.bankUBN,
    dao.receiverAccountId,
    dao.transactionId,
)

class TransactionRepositoryImpl: TransactionRepository {

    init {
        Database.connect(
            url = "jdbc:postgresql://localhost:5432/bank_db",
            driver = "org.postgresql.Driver",
            user = "postgres",
            password = "625100"
        )

        transaction {
            SchemaUtils.create(TransactionsTable)
        }
    }

    override suspend fun createTransaction(transaction: Transaction): Unit = suspendTransaction {
        TransactionsDAO.new {
            transaction.senderAccountId
            transaction.amount
            transaction.type
            transaction.bankUBN
            transaction.receiverAccountId
            transaction.transactionId
        }
    }

    override suspend fun getTransactionById(transactionId: UUID): Transaction? = suspendTransaction {
        TransactionsDAO
            .find{ (TransactionsTable.transactionId eq transactionId) }
            .limit(1)
            .map(::daoToModel)
            .firstOrNull()
    }

    override suspend fun getTransactionsByAccount(accountId: UUID): List<Transaction> = suspendTransaction {
        TransactionsDAO
            .find{ (TransactionsTable.senderAccountId eq accountId) }
            .map(::daoToModel)
    }

    override suspend fun getTransactionsByBank(bankUBN: UUID): List<Transaction> = suspendTransaction {
        TransactionsDAO
            .find{ (TransactionsTable.bankUBN eq bankUBN) }
            .map(::daoToModel)
    }

    override suspend fun deleteTransaction(transactionId: UUID): Boolean = suspendTransaction {
        val rowsDeleted = TransactionsTable.deleteWhere {
            TransactionsTable.transactionId eq transactionId
        }
        rowsDeleted == 1
    }
}