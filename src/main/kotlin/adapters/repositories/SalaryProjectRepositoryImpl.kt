package com.example.adapters.repositories

import com.example.domain.SalaryProjectStatus
import com.example.domain.entities.SalaryProject
import com.example.domain.repositories.SalaryProjectRepository
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
    val status = enumerationByName("status", 20, SalaryProjectStatus::class)
    val salaryProjectId = uuid("salary_project_id")
}

// Преобразование ResultRow в SalaryProject
fun ResultRow.toSalaryProject(): SalaryProject {
    return SalaryProject(
        bankUBN = this[SalaryProjectsTable.bankUBN],
        enterpriseId = this[SalaryProjectsTable.enterpriseId],
        status = this[SalaryProjectsTable.status],
        salaryProjectId = this[SalaryProjectsTable.salaryProjectId]
    )
}

// DAO-класс для SalaryProject
class SalaryProjectsDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<SalaryProjectsDAO>(SalaryProjectsTable)

    var bankUBN by SalaryProjectsTable.bankUBN
    var enterpriseId by SalaryProjectsTable.enterpriseId
    var status by SalaryProjectsTable.status
    var salaryProjectId by SalaryProjectsTable.salaryProjectId
}

// Преобразование DAO в модель
fun daoToModel(dao: SalaryProjectsDAO) = SalaryProject(
    dao.bankUBN,
    dao.enterpriseId,
    dao.status,
    dao.salaryProjectId
)

class SalaryProjectRepositoryImpl: SalaryProjectRepository {

    init {
        Database.connect(
            url = "jdbc:postgresql://localhost:5432/bank_db",
            driver = "org.postgresql.Driver",
            user = "postgres",
            password = "625100"
        )

        transaction {
            SchemaUtils.create(SalaryProjectsTable)
        }
    }

    override suspend fun createSalaryProject(salaryProject: SalaryProject): Unit = suspendTransaction {
        SalaryProjectsDAO.new {
            salaryProject.bankUBN
            salaryProject.enterpriseId
            salaryProject.status
            salaryProject.salaryProjectId
        }
    }

    override suspend fun getSalaryProjectById(salaryProjectId: UUID): SalaryProject? = suspendTransaction {
        SalaryProjectsDAO
            .find{ (SalaryProjectsTable.salaryProjectId eq salaryProjectId) }
            .limit(1)
            .map(::daoToModel)
            .firstOrNull()
    }

    override suspend fun getSalaryProjectsByBank(bankUBN: UUID): List<SalaryProject> = suspendTransaction {
        SalaryProjectsDAO
            .find{ (SalaryProjectsTable.bankUBN eq bankUBN) }
            .map(::daoToModel)
    }

    override suspend fun updateSalaryProject(salaryProject: SalaryProject): Unit = suspendTransaction {
        SalaryProjectsTable.update({ SalaryProjectsTable.salaryProjectId eq salaryProject.salaryProjectId }) {
            it[status] = salaryProject.status
        }
    }

    override suspend fun deleteSalaryProject(salaryProjectId: UUID): Boolean = suspendTransaction {
        val rowsDeleted = SalaryProjectsTable.deleteWhere {
            SalaryProjectsTable.salaryProjectId eq salaryProjectId
        }
        rowsDeleted == 1
    }
}