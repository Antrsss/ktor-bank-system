package com.example.adapters.repositories.users

import com.example.adapters.repositories.suspendTransaction
import com.example.domain.ClientCountry
import com.example.domain.Role
import com.example.domain.entities.users.ForeignClient
import com.example.domain.repositories.base.CRUDRepository
import com.example.domain.repositories.common.UsersRepository
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ResultRow
import java.util.*
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction

object ForeignClientsTable : IntIdTable("foreign_clients") {
    val FIO = varchar("fio", 255)
    val passportSeries = varchar("passport_series", 10)
    val passportNumber = integer("passport_number")
    val phone = varchar("phone", 20)
    val email = varchar("email", 255)
    val password = varchar("password", 255)
    val country = enumerationByName("country", 50, ClientCountry::class)
    val bankUBN = uuid("bank_ubn")
    val userId = uuid("user_id").uniqueIndex()
    val role = enumerationByName("role", 50, Role::class).default(Role.CLIENT)
}

fun ResultRow.toForeignClient(): ForeignClient {
    return ForeignClient(
        FIO = this[ForeignClientsTable.FIO],
        passportSeries = this[ForeignClientsTable.passportSeries],
        passportNumber = this[ForeignClientsTable.passportNumber],
        phone = this[ForeignClientsTable.phone],
        email = this[ForeignClientsTable.email],
        password = this[ForeignClientsTable.password],
        country = this[ForeignClientsTable.country],
        bankUBN = this[ForeignClientsTable.bankUBN],
        userId = this[ForeignClientsTable.userId]
    )
}

class ForeignClientDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ForeignClientDAO>(ForeignClientsTable)

    var FIO by ForeignClientsTable.FIO
    var passportSeries by ForeignClientsTable.passportSeries
    var passportNumber by ForeignClientsTable.passportNumber
    var phone by ForeignClientsTable.phone
    var email by ForeignClientsTable.email
    var password by ForeignClientsTable.password
    var country by ForeignClientsTable.country
    var bankUBN by ForeignClientsTable.bankUBN
    var userId by ForeignClientsTable.userId
    var role by ForeignClientsTable.role
}

fun daoToModel(dao: ForeignClientDAO) = ForeignClient(
    FIO = dao.FIO,
    passportSeries = dao.passportSeries,
    passportNumber = dao.passportNumber,
    phone = dao.phone,
    email = dao.email,
    password = dao.password,
    country = dao.country,
    bankUBN = dao.bankUBN,
    userId = dao.userId
)

class ForeignClientRepositoryImpl : CRUDRepository<ForeignClient>, UsersRepository<ForeignClient> {

    init {
        transaction {
            SchemaUtils.create(ForeignClientsTable)
        }
    }

    override suspend fun create(entity: ForeignClient) = suspendTransaction {
        if (get(entity.userId) == null) {
            ForeignClientDAO.new {
                FIO = entity.FIO
                passportSeries = entity.passportSeries
                passportNumber = entity.passportNumber
                phone = entity.phone
                email = entity.email
                password = entity.password
                country = entity.country
                bankUBN = entity.bankUBN
                userId = entity.userId
                role = Role.CLIENT
            }.let { daoToModel(it) }
        }
    }

    override suspend fun get(id: UUID): ForeignClient? = suspendTransaction {
        ForeignClientDAO
            .find { ForeignClientsTable.userId eq id }
            .limit(1)
            .map(::daoToModel)
            .firstOrNull()
    }

    override suspend fun delete(entityId: UUID): Boolean = suspendTransaction {
        val rowsDeleted = ForeignClientsTable.deleteWhere {
            ForeignClientsTable.userId eq entityId
        }
        rowsDeleted == 1
    }

    override suspend fun update(entity: ForeignClient): ForeignClient = suspendTransaction {
        ForeignClientDAO
            .find { ForeignClientsTable.userId eq entity.userId }
            .limit(1)
            .firstOrNull()
            ?.apply {
                FIO = entity.FIO
                passportSeries = entity.passportSeries
                passportNumber = entity.passportNumber
                phone = entity.phone
                email = entity.email
                password = entity.password
                country = entity.country
                bankUBN = entity.bankUBN
            }
        entity
    }

    override suspend fun getUserByEmail(email: String): ForeignClient? = suspendTransaction {
        ForeignClientDAO
            .find { ForeignClientsTable.email eq email }
            .limit(1)
            .map(::daoToModel)
            .firstOrNull()
    }

    override suspend fun getUsersByBank(bankUBN: UUID): List<ForeignClient> = suspendTransaction {
        ForeignClientDAO
            .find { ForeignClientsTable.bankUBN eq bankUBN }
            .map(::daoToModel)
    }
}