package com.example.adapters.repositories.requests

import com.example.adapters.repositories.suspendTransaction
import com.example.domain.RequestStatus
import com.example.domain.entities.requests.SalaryProjectRequest
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

object SalaryProjectRequestsTable : IntIdTable("salary_project_requests") {
    val bankUBN = uuid("bank_ubn")
    val applicantId = uuid("applicant_id")
    val enterpriseId = uuid("enterprise_id")
    val requestStatus = enumerationByName("request_status", 50, RequestStatus::class)
    val requestId = uuid("request_id").uniqueIndex()
}

fun ResultRow.toSalaryProjectRequest(): SalaryProjectRequest {
    return SalaryProjectRequest(
        bankUBN = this[SalaryProjectRequestsTable.bankUBN],
        applicantId = this[SalaryProjectRequestsTable.applicantId],
        enterpriseId = this[SalaryProjectRequestsTable.enterpriseId],
        requestStatus = this[SalaryProjectRequestsTable.requestStatus],
        requestId = this[SalaryProjectRequestsTable.requestId]
    )
}

class SalaryProjectRequestDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<SalaryProjectRequestDAO>(SalaryProjectRequestsTable)

    var bankUBN by SalaryProjectRequestsTable.bankUBN
    var applicantId by SalaryProjectRequestsTable.applicantId
    var enterpriseId by SalaryProjectRequestsTable.enterpriseId
    var requestStatus by SalaryProjectRequestsTable.requestStatus
    var requestId by SalaryProjectRequestsTable.requestId
}

fun daoToModel(dao: SalaryProjectRequestDAO) = SalaryProjectRequest(
    bankUBN = dao.bankUBN,
    applicantId = dao.applicantId,
    enterpriseId = dao.enterpriseId,
    requestStatus = dao.requestStatus,
    requestId = dao.requestId
)

class SalaryProjectRequestRepositoryImpl: RequestRepository<SalaryProjectRequest> {

    init {
        transaction {
            SchemaUtils.create(SalaryProjectRequestsTable)
        }
    }

    override suspend fun getRequestsByBank(bankUBN: UUID): List<SalaryProjectRequest> = suspendTransaction {
        SalaryProjectRequestDAO
            .find { SalaryProjectRequestsTable.bankUBN eq bankUBN }
            .map(::daoToModel)
    }

    override suspend fun create(entity: SalaryProjectRequest) = suspendTransaction {
        if (get(entity.requestId) == null) {
            SalaryProjectRequestDAO.new {
                bankUBN = entity.bankUBN
                applicantId = entity.applicantId
                enterpriseId = entity.enterpriseId
                requestStatus = entity.requestStatus
                requestId = entity.requestId
            }
        }
    }

    override suspend fun get(id: UUID): SalaryProjectRequest? = suspendTransaction {
        SalaryProjectRequestDAO
            .find { SalaryProjectRequestsTable.requestId eq id }
            .limit(1)
            .map(::daoToModel)
            .firstOrNull()
    }

    override suspend fun update(entity: SalaryProjectRequest): SalaryProjectRequest = suspendTransaction {
        SalaryProjectRequestsTable.update({ SalaryProjectRequestsTable.requestId eq entity.requestId }) {
            it[bankUBN] = entity.bankUBN
            it[applicantId] = entity.applicantId
            it[enterpriseId] = entity.enterpriseId
            it[requestStatus] = entity.requestStatus
            it[requestId] = entity.requestId
        }
        entity
    }

    override suspend fun delete(id: UUID): Boolean = suspendTransaction {
        val rowsDeleted = SalaryProjectRequestsTable.deleteWhere {
            SalaryProjectRequestsTable.requestId eq id
        }
        rowsDeleted == 1
    }
}