package com.example.adapters.repositories.requests

import com.example.adapters.repositories.suspendTransaction
import com.example.domain.RequestStatus
import com.example.domain.LoanPeriod
import com.example.domain.entities.requests.DeferredPaymentRequest
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

object DeferredPaymentRequestsTable : IntIdTable("deferred_payment_requests") {
    val bankUBN = uuid("bank_ubn")
    val applicantId = uuid("applicant_id")
    val amount = double("amount")
    val period = enumerationByName("period", 50, LoanPeriod::class)
    val requestStatus = enumerationByName("request_status", 50, RequestStatus::class).default(RequestStatus.PENDING)
    val requestId = uuid("request_id").uniqueIndex()
}

fun ResultRow.toDeferredPaymentRequest(): DeferredPaymentRequest {
    return DeferredPaymentRequest(
        bankUBN = this[DeferredPaymentRequestsTable.bankUBN],
        applicantId = this[DeferredPaymentRequestsTable.applicantId],
        amount = this[DeferredPaymentRequestsTable.amount],
        period = this[DeferredPaymentRequestsTable.period],
        requestStatus = this[DeferredPaymentRequestsTable.requestStatus],
        requestId = this[DeferredPaymentRequestsTable.requestId]
    )
}

class DeferredPaymentRequestDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<DeferredPaymentRequestDAO>(DeferredPaymentRequestsTable)

    var bankUBN by DeferredPaymentRequestsTable.bankUBN
    var applicantId by DeferredPaymentRequestsTable.applicantId
    var amount by DeferredPaymentRequestsTable.amount
    var period by DeferredPaymentRequestsTable.period
    var requestStatus by DeferredPaymentRequestsTable.requestStatus
    var requestId by DeferredPaymentRequestsTable.requestId
}

fun daoToModel(dao: DeferredPaymentRequestDAO) = DeferredPaymentRequest(
    bankUBN = dao.bankUBN,
    applicantId = dao.applicantId,
    amount = dao.amount,
    period = dao.period,
    requestStatus = dao.requestStatus,
    requestId = dao.requestId
)

class DeferredPaymentRequestRepositoryImpl: RequestRepository<DeferredPaymentRequest> {

    init {
        transaction {
            SchemaUtils.create(DeferredPaymentRequestsTable)
        }
    }

    override suspend fun create(entity: DeferredPaymentRequest) = suspendTransaction {
        if (get(entity.requestId) == null) {
            DeferredPaymentRequestDAO.new {
                bankUBN = entity.bankUBN
                applicantId = entity.applicantId
                amount = entity.amount
                period = entity.period
                requestStatus = entity.requestStatus
                requestId = entity.requestId
            }
        }
    }

    override suspend fun get(id: UUID): DeferredPaymentRequest? = suspendTransaction {
        DeferredPaymentRequestDAO
            .find { DeferredPaymentRequestsTable.requestId eq id }
            .limit(1)
            .map(::daoToModel)
            .firstOrNull()
    }

    override suspend fun update(entity: DeferredPaymentRequest): DeferredPaymentRequest = suspendTransaction {
        DeferredPaymentRequestsTable.update({ DeferredPaymentRequestsTable.requestId eq entity.requestId }) {
            it[bankUBN] = entity.bankUBN
            it[applicantId] = entity.applicantId
            it[amount] = entity.amount
            it[period] = entity.period
            it[requestStatus] = entity.requestStatus
            it[requestId] = entity.requestId
        }
        entity
    }

    override suspend fun delete(id: UUID): Boolean = suspendTransaction {
        val rowsDeleted = DeferredPaymentRequestsTable.deleteWhere {
            DeferredPaymentRequestsTable.requestId eq id
        }
        rowsDeleted == 1
    }

    override suspend fun getRequestsByBank(bankUBN: UUID): List<DeferredPaymentRequest> = suspendTransaction {
        DeferredPaymentRequestDAO
            .find { DeferredPaymentRequestsTable.bankUBN eq bankUBN }
            .map(::daoToModel)
    }
}