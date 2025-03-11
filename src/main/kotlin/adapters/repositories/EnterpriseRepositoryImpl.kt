package com.example.adapters.repositories

import com.example.domain.EnterpriseType
import com.example.domain.entities.Enterprise
import com.example.domain.repositories.EnterpriseRepository
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

object EnterprisesTable : IntIdTable("enterprises") {
    val legalName = varchar("legal_name", 255)
    val enterpriseType = enumerationByName("enterprise_type", 20, EnterpriseType::class)
    val enterprisePAN = long("enterprise_pan")
    val bankUBN = uuid("bank_ubn")
    val legalAdress = varchar("legal_address", 512)
    val enterpriseId = uuid("enterprise_id")
}

fun ResultRow.toEnterprise(): Enterprise {
    return Enterprise(
        legalName = this[EnterprisesTable.legalName],
        enterpriseType = this[EnterprisesTable.enterpriseType],
        enterprisePAN = this[EnterprisesTable.enterprisePAN],
        bankUBN = this[EnterprisesTable.bankUBN],
        legalAdress = this[EnterprisesTable.legalAdress],
        enterpriseId = this[EnterprisesTable.enterpriseId]
    )
}

class EnterprisesDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<EnterprisesDAO>(EnterprisesTable)

    var legalName by EnterprisesTable.legalName
    var enterpriseType by EnterprisesTable.enterpriseType
    var enterprisePAN by EnterprisesTable.enterprisePAN
    var bankUBN by EnterprisesTable.bankUBN
    var legalAdress by EnterprisesTable.legalAdress
    var enterpriseId by EnterprisesTable.enterpriseId
}

fun daoToModel(dao: EnterprisesDAO) = Enterprise(
    dao.legalName,
    dao.enterpriseType,
    dao.enterprisePAN,
    dao.bankUBN,
    dao.legalAdress,
    dao.enterpriseId
)

class EnterpriseRepositoryImpl: EnterpriseRepository {

    init {
        Database.connect(
            url = "jdbc:postgresql://localhost:5432/bank_db",
            driver = "org.postgresql.Driver",
            user = "postgres",
            password = "625100"
        )

        transaction {
            SchemaUtils.create(EnterprisesTable)
        }
    }

    override suspend fun createEnterprise(enterprise: Enterprise): Unit = suspendTransaction {
        EnterprisesDAO.new {
            legalName = enterprise.legalName
            enterpriseType = enterprise.enterpriseType
            enterprisePAN = enterprise.enterprisePAN
            bankUBN = enterprise.bankUBN
            legalAdress = enterprise.legalAdress
            enterpriseId = enterprise.enterpriseId
        }
    }

    override suspend fun getEnterpriseById(enterpriseId: UUID): Enterprise? = suspendTransaction {
        EnterprisesDAO
            .find { (EnterprisesTable.enterpriseId eq enterpriseId) }
            .limit(1)
            .map(::daoToModel)
            .firstOrNull()
    }

    override suspend fun getAllBankEnterprises(bankUBN: UUID): List<Enterprise> = suspendTransaction {
        EnterprisesDAO.all().map(::daoToModel)
    }

    override suspend fun updateEnterprise(enterprise: Enterprise): Unit = suspendTransaction {
        EnterprisesTable.update({ EnterprisesTable.enterpriseId eq enterprise.enterpriseId }) {
            it[legalName] = enterprise.legalName
            it[enterpriseType] = enterprise.enterpriseType
            it[legalAdress] = enterprise.legalAdress
        }
    }

    override suspend fun deleteEnterprise(enterpriseId: UUID): Boolean = suspendTransaction {
        val rowsDeleted = EnterprisesTable.deleteWhere {
            EnterprisesTable.enterpriseId eq enterpriseId
        }
        rowsDeleted == 1
    }
}