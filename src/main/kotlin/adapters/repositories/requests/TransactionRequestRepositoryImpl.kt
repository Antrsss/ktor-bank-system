package com.example.adapters.repositories.requests

import com.example.adapters.repositories.TransactionsTable.transactionType
import com.example.adapters.repositories.suspendTransaction
import com.example.domain.RequestStatus
import com.example.domain.TransactionType
import com.example.domain.entities.requests.TransactionRequest
import com.example.domain.repositories.common.RequestRepository
import com.example.domain.repositories.base.ImmutableRepository
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.util.*

object TransactionRequestsTable : IntIdTable("transaction_requests") {
    val bankUBN = uuid("bank_ubn")
    val applicantId = uuid("applicant_id")
    val amount = double("amount")
    val type = enumerationByName("type", 50, TransactionType::class)
    val recipientId = uuid("recipient_id")
    val requestStatus = enumerationByName("request_status", 50, RequestStatus::class).default(RequestStatus.PENDING)
    val requestId = uuid("request_id").uniqueIndex()
}

fun ResultRow.toTransactionRequest(): TransactionRequest {
    return TransactionRequest(
        bankUBN = this[TransactionRequestsTable.bankUBN],
        applicantId = this[TransactionRequestsTable.applicantId],
        amount = this[TransactionRequestsTable.amount],
        transactionType = this[TransactionRequestsTable.type],
        recipientId = this[TransactionRequestsTable.recipientId],
        requestStatus = this[TransactionRequestsTable.requestStatus],
        requestId = this[TransactionRequestsTable.requestId]
    )
}

class TransactionRequestDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TransactionRequestDAO>(TransactionRequestsTable)

    var bankUBN by TransactionRequestsTable.bankUBN
    var applicantId by TransactionRequestsTable.applicantId
    var amount by TransactionRequestsTable.amount
    var transactionType by TransactionRequestsTable.type
    var recipientId by TransactionRequestsTable.recipientId
    var requestStatus by TransactionRequestsTable.requestStatus
    var requestId by TransactionRequestsTable.requestId
}

fun daoToModel(dao: TransactionRequestDAO) = TransactionRequest(
    bankUBN = dao.bankUBN,
    applicantId = dao.applicantId,
    amount = dao.amount,
    transactionType = dao.transactionType,
    recipientId = dao.recipientId,
    requestStatus = dao.requestStatus,
    requestId = dao.requestId
)

class TransactionRequestRepositoryImpl: RequestRepository<TransactionRequest> {

    init {
        transaction {
            SchemaUtils.create(TransactionRequestsTable)
        }
    }

    override suspend fun getRequestsByBank(bankUBN: UUID): List<TransactionRequest> = suspendTransaction {
        TransactionRequestDAO
            .find { TransactionRequestsTable.bankUBN eq bankUBN }
            .map(::daoToModel)
    }

    override suspend fun create(entity: TransactionRequest) = suspendTransaction {
        if (get(entity.requestId) == null) {
            TransactionRequestDAO.new {
                bankUBN = entity.bankUBN
                applicantId = entity.applicantId
                amount = entity.amount
                transactionType = entity.transactionType
                recipientId = entity.recipientId
                requestStatus = entity.requestStatus
                requestId = entity.requestId
            }
        }
    }

    override suspend fun get(id: UUID): TransactionRequest? = suspendTransaction {
        TransactionRequestDAO
            .find { TransactionRequestsTable.requestId eq id }
            .limit(1)
            .map(::daoToModel)
            .firstOrNull()
    }

    override suspend fun update(entity: TransactionRequest): TransactionRequest = suspendTransaction {
        TransactionRequestsTable.update({ TransactionRequestsTable.requestId eq entity.requestId }) {
            it[bankUBN] = entity.bankUBN
            it[applicantId] = entity.applicantId
            it[amount] = entity.amount
            it[transactionType] = entity.transactionType
            it[recipientId] = entity.recipientId
            it[requestStatus] = entity.requestStatus
            it[requestId] = entity.requestId
        }
        entity
    }

    override suspend fun delete(id: UUID): Boolean = suspendTransaction {
        val rowsDeleted = TransactionRequestsTable.deleteWhere {
            TransactionRequestsTable.requestId eq id
        }
        rowsDeleted == 1
    }
}