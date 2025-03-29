package com.example.adapters.repositories

import com.example.domain.EnterpriseType
import com.example.domain.entities.Enterprise
import com.example.domain.repositories.EnterpriseRepository
import com.example.domain.repositories.base.CRUDRepository
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
    val enterpriseId = uuid("enterprise_id").uniqueIndex().default(UUID.randomUUID())
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
        transaction {
            SchemaUtils.create(EnterprisesTable)
        }
    }

    override suspend fun create(entity: Enterprise): Unit = suspendTransaction {
        EnterprisesDAO.new {
            legalName = entity.legalName
            enterpriseType = entity.enterpriseType
            enterprisePAN = entity.enterprisePAN
            bankUBN = entity.bankUBN
            legalAdress = entity.legalAdress
            enterpriseId = entity.enterpriseId
        }
    }

    override suspend fun get(id: UUID): Enterprise? = suspendTransaction {
        EnterprisesDAO
            .find { (EnterprisesTable.enterpriseId eq id) }
            .limit(1)
            .map(::daoToModel)
            .firstOrNull()
    }

    override suspend fun delete(entityId: UUID): Boolean = suspendTransaction {
        val rowsDeleted = EnterprisesTable.deleteWhere {
            EnterprisesTable.enterpriseId eq entityId
        }
        rowsDeleted == 1
    }

    override suspend fun update(entity: Enterprise): Enterprise = suspendTransaction {
        EnterprisesTable.update({ EnterprisesTable.enterpriseId eq entity.enterpriseId }) {
            it[legalName] = entity.legalName
            it[enterpriseType] = entity.enterpriseType
            it[enterprisePAN] = entity.enterprisePAN
            it[bankUBN] = entity.bankUBN
            it[legalAdress] = entity.legalAdress
            it[enterpriseId] = entity.enterpriseId
        }
        entity
    }

    override suspend fun getEnterprisesByBank(bankUBN: UUID): List<Enterprise> = suspendTransaction {
        EnterprisesDAO
            .find { EnterprisesTable.bankUBN eq bankUBN }
            .map(::daoToModel)
    }
}