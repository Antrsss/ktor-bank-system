package com.example.adapters.repositories

import com.example.domain.TransactionType
import com.example.domain.entities.Transaction
import com.example.domain.repositories.TransactionRepository
import com.example.domain.repositories.base.ImmutableRepository
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
    val transactionType = enumerationByName("transaction_type", 20, TransactionType::class)
    val bankUBN = uuid("bank_ubn")
    val receiverAccountId = uuid("receiver_account_id").nullable()
    val transactionId = uuid("transaction_id")
}

fun ResultRow.toTransaction(): Transaction {
    return Transaction(
        senderAccountId = this[TransactionsTable.senderAccountId],
        amount = this[TransactionsTable.amount],
        transactionType = this[TransactionsTable.transactionType],
        bankUBN = this[TransactionsTable.bankUBN],
        receiverAccountId = this[TransactionsTable.receiverAccountId],
        transactionId = this[TransactionsTable.transactionId],
    )
}

class TransactionsDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TransactionsDAO>(TransactionsTable)

    var senderAccountId by TransactionsTable.senderAccountId
    var amount by TransactionsTable.amount
    var transactionType by TransactionsTable.transactionType
    var bankUBN by TransactionsTable.bankUBN
    var receiverAccountId by TransactionsTable.receiverAccountId
    var transactionId by TransactionsTable.transactionId
}

fun daoToModel(dao: TransactionsDAO) = Transaction(
    dao.senderAccountId,
    dao.amount,
    dao.transactionType,
    dao.bankUBN,
    dao.receiverAccountId,
    dao.transactionId,
)

class TransactionRepositoryImpl: TransactionRepository {

    init {
        transaction {
            SchemaUtils.create(TransactionsTable)
        }
    }

    override suspend fun create(entity: Transaction): Unit = suspendTransaction {
        TransactionsDAO.new {
            senderAccountId = entity.senderAccountId
            amount = entity.amount
            transactionType = entity.transactionType
            bankUBN = entity.bankUBN
            receiverAccountId = entity.receiverAccountId
            transactionId = entity.transactionId
        }
    }

    override suspend fun get(id: UUID): Transaction? = suspendTransaction {
        TransactionsDAO
            .find{ (TransactionsTable.transactionId eq id) }
            .limit(1)
            .map(::daoToModel)
            .firstOrNull()
    }

    override suspend fun delete(id: UUID): Boolean = suspendTransaction {
        val rowsDeleted = TransactionsTable.deleteWhere {
            TransactionsTable.transactionId eq id
        }
        rowsDeleted == 1
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
}