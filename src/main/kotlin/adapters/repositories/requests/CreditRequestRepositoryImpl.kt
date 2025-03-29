package com.example.adapters.repositories.requests

import com.example.adapters.repositories.suspendTransaction
import com.example.domain.RequestStatus
import com.example.domain.LoanPeriod
import com.example.domain.entities.requests.CreditRequest
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

object CreditRequestsTable : IntIdTable("credit_requests") {
    val bankUBN = uuid("bank_ubn")
    val applicantId = uuid("applicant_id")
    val amount = double("amount")
    val period = enumerationByName("period", 50, LoanPeriod::class)
    val individualRate = double("individual_rate").nullable()
    val requestStatus = enumerationByName("request_status", 50, RequestStatus::class).default(RequestStatus.PENDING)
    val requestId = uuid("request_id").uniqueIndex()
}

fun ResultRow.toCreditRequest(): CreditRequest {
    return CreditRequest(
        bankUBN = this[CreditRequestsTable.bankUBN],
        applicantId = this[CreditRequestsTable.applicantId],
        amount = this[CreditRequestsTable.amount],
        period = this[CreditRequestsTable.period],
        individualRate = this[CreditRequestsTable.individualRate],
        requestStatus = this[CreditRequestsTable.requestStatus],
        requestId = this[CreditRequestsTable.requestId]
    )
}

class CreditRequestDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<CreditRequestDAO>(CreditRequestsTable)

    var bankUBN by CreditRequestsTable.bankUBN
    var applicantId by CreditRequestsTable.applicantId
    var amount by CreditRequestsTable.amount
    var period by CreditRequestsTable.period
    var individualRate by CreditRequestsTable.individualRate
    var requestStatus by CreditRequestsTable.requestStatus
    var requestId by CreditRequestsTable.requestId
}

fun daoToModel(dao: CreditRequestDAO) = CreditRequest(
    bankUBN = dao.bankUBN,
    applicantId = dao.applicantId,
    amount = dao.amount,
    period = dao.period,
    individualRate = dao.individualRate,
    requestStatus = dao.requestStatus,
    requestId = dao.requestId
)

class CreditRequestRepositoryImpl: RequestRepository<CreditRequest> {

    init {
        transaction {
            SchemaUtils.create(CreditRequestsTable)
        }
    }

    override suspend fun create(entity: CreditRequest) = suspendTransaction {
        if (get(entity.requestId) == null) {
            CreditRequestDAO.new {
                bankUBN = entity.bankUBN
                applicantId = entity.applicantId
                amount = entity.amount
                period = entity.period
                individualRate = entity.individualRate
                requestStatus = entity.requestStatus
                requestId = entity.requestId
            }
        }
    }

    override suspend fun get(id: UUID): CreditRequest? = suspendTransaction {
        CreditRequestDAO
            .find { CreditRequestsTable.requestId eq id }
            .limit(1)
            .map(::daoToModel)
            .firstOrNull()
    }

    override suspend fun update(entity: CreditRequest): CreditRequest = suspendTransaction {
        CreditRequestsTable.update({ CreditRequestsTable.requestId eq entity.requestId }) {
            it[bankUBN] = entity.bankUBN
            it[applicantId] = entity.applicantId
            it[amount] = entity.amount
            it[period] = entity.period
            it[individualRate] = entity.individualRate
            it[requestStatus] = entity.requestStatus
            it[requestId] = entity.requestId
        }
        entity
    }

    override suspend fun delete(id: UUID): Boolean = suspendTransaction {
        val rowsDeleted = CreditRequestsTable.deleteWhere {
            CreditRequestsTable.requestId eq id
        }
        rowsDeleted == 1
    }

    override suspend fun getRequestsByBank(bankUBN: UUID): List<CreditRequest> = suspendTransaction {
        CreditRequestDAO
            .find { CreditRequestsTable.bankUBN eq bankUBN }
            .map(::daoToModel)
    }
}