package com.example.adapters.repositories

import com.example.domain.SalaryProjectStatus
import com.example.domain.entities.SalaryProject
import com.example.domain.repositories.SalaryProjectRepository
import com.example.domain.repositories.base.CRUDRepository
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

object SalaryProjectsTable : IntIdTable("salary_projects") {
    val bankUBN = uuid("bank_ubn")
    val enterpriseId = uuid("enterprise_id")
    val userId = uuid("user_id")
    val status = enumerationByName("status", 20, SalaryProjectStatus::class).default(SalaryProjectStatus.ACTIVE)
    val salaryProjectId = uuid("salary_project_id")
}

fun ResultRow.toSalaryProject(): SalaryProject {
    return SalaryProject(
        bankUBN = this[SalaryProjectsTable.bankUBN],
        enterpriseId = this[SalaryProjectsTable.enterpriseId],
        userId = this[SalaryProjectsTable.userId],
        status = this[SalaryProjectsTable.status],
        salaryProjectId = this[SalaryProjectsTable.salaryProjectId]
    )
}

class SalaryProjectsDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<SalaryProjectsDAO>(SalaryProjectsTable)

    var bankUBN by SalaryProjectsTable.bankUBN
    var enterpriseId by SalaryProjectsTable.enterpriseId
    var userId by SalaryProjectsTable.userId
    var status by SalaryProjectsTable.status
    var salaryProjectId by SalaryProjectsTable.salaryProjectId
}

fun daoToModel(dao: SalaryProjectsDAO) = SalaryProject(
    dao.bankUBN,
    dao.enterpriseId,
    dao.userId,
    dao.status,
    dao.salaryProjectId
)

class SalaryProjectRepositoryImpl: SalaryProjectRepository {

    init {
        transaction {
            SchemaUtils.create(SalaryProjectsTable)
        }
    }

    override suspend fun create(entity: SalaryProject): Unit = suspendTransaction {
        SalaryProjectsDAO.new {
            bankUBN = entity.bankUBN
            enterpriseId = entity.enterpriseId
            userId = entity.userId
            status = entity.status
            salaryProjectId = entity.salaryProjectId
        }
    }

    override suspend fun get(id: UUID): SalaryProject? = suspendTransaction {
        SalaryProjectsDAO
            .find{ (SalaryProjectsTable.salaryProjectId eq id) }
            .limit(1)
            .map(::daoToModel)
            .firstOrNull()
    }

    override suspend fun delete(entityId: UUID): Boolean = suspendTransaction {
        val rowsDeleted = SalaryProjectsTable.deleteWhere {
            SalaryProjectsTable.salaryProjectId eq entityId
        }
        rowsDeleted == 1
    }

    override suspend fun update(entity: SalaryProject): SalaryProject = suspendTransaction {
        SalaryProjectsTable.update({ SalaryProjectsTable.salaryProjectId eq entity.salaryProjectId }) {
            it[bankUBN] = entity.bankUBN
            it[enterpriseId] = entity.enterpriseId
            it[status] = entity.status
            it[salaryProjectId] = entity.salaryProjectId
        }
        entity
    }

    override suspend fun getSalaryProjectsByBank(bankUBN: UUID): List<SalaryProject> = suspendTransaction {
        SalaryProjectsDAO
            .find{ (SalaryProjectsTable.bankUBN eq bankUBN) }
            .map(::daoToModel)
    }
}