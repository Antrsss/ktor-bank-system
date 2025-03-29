package com.example.adapters.repositories.users

import com.example.adapters.repositories.AccountsDAO
import com.example.adapters.repositories.AccountsTable
import com.example.adapters.repositories.requests.DeferredPaymentRequestDAO.Companion.referrersOn
import com.example.adapters.repositories.suspendTransaction
import com.example.domain.entities.users.Client
import com.example.domain.repositories.common.UsersRepository
import java.util.*
import com.example.domain.Role
import com.example.domain.entities.Account
import com.example.domain.repositories.base.CRUDRepository
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction

object ClientsTable : IntIdTable("clients") {
    val FIO = varchar("fio", 255)
    val passportSeries = varchar("passport_series", 10)
    val passportNumber = integer("passport_number")
    val phone = varchar("phone", 20)
    val email = varchar("email", 255)
    val password = varchar("password", 255)
    val bankUBN = uuid("bank_ubn")
    val userId = uuid("user_id").uniqueIndex()
    val role = enumerationByName("role", 50, Role::class).default(Role.CLIENT)
}

fun ResultRow.toClient(): Client {
    return Client(
        FIO = this[ClientsTable.FIO],
        passportSeries = this[ClientsTable.passportSeries],
        passportNumber = this[ClientsTable.passportNumber],
        phone = this[ClientsTable.phone],
        email = this[ClientsTable.email],
        password = this[ClientsTable.password],
        bankUBN = this[ClientsTable.bankUBN],
        userId = this[ClientsTable.userId]
    )
}

class ClientDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ClientDAO>(ClientsTable)

    var FIO by ClientsTable.FIO
    var passportSeries by ClientsTable.passportSeries
    var passportNumber by ClientsTable.passportNumber
    var phone by ClientsTable.phone
    var email by ClientsTable.email
    var password by ClientsTable.password
    var bankUBN by ClientsTable.bankUBN
    var userId by ClientsTable.userId
    var role by ClientsTable.role
}

fun daoToModel(dao: ClientDAO) = Client(
    FIO = dao.FIO,
    passportSeries = dao.passportSeries,
    passportNumber = dao.passportNumber,
    phone = dao.phone,
    email = dao.email,
    password = dao.password,
    bankUBN = dao.bankUBN,
    userId = dao.userId
)

class ClientRepositoryImpl : CRUDRepository<Client>, UsersRepository<Client> {

    init {
        transaction {
            SchemaUtils.create(ClientsTable)
        }
    }

    override suspend fun create(entity: Client) = suspendTransaction {
        if (get(entity.userId) == null) {
            ClientDAO.new {
                FIO = entity.FIO
                passportSeries = entity.passportSeries
                passportNumber = entity.passportNumber
                phone = entity.phone
                email = entity.email
                password = entity.password
                bankUBN = entity.bankUBN
                userId = entity.userId
                role = Role.CLIENT
            }.let { daoToModel(it) }
        }
    }

    override suspend fun get(id: UUID): Client? = suspendTransaction {
        ClientDAO
            .find { ClientsTable.userId eq id }
            .limit(1)
            .map(::daoToModel)
            .firstOrNull()
    }

    override suspend fun delete(entityId: UUID): Boolean = suspendTransaction {
        val rowsDeleted = ClientsTable.deleteWhere {
            ClientsTable.userId eq entityId
        }
        rowsDeleted == 1
    }

    override suspend fun update(entity: Client): Client = suspendTransaction {
        ClientDAO
            .find { ClientsTable.userId eq entity.userId }
            .limit(1)
            .firstOrNull()
            ?.apply {
                FIO = entity.FIO
                passportSeries = entity.passportSeries
                passportNumber = entity.passportNumber
                phone = entity.phone
                email = entity.email
                password = entity.password
                bankUBN = entity.bankUBN
            }
        entity
    }

    override suspend fun getUserByEmail(email: String): Client? = suspendTransaction {
        ClientDAO
            .find { ClientsTable.email eq email }
            .limit(1)
            .map(::daoToModel)
            .firstOrNull()
    }

    override suspend fun getUsersByBank(bankUBN: UUID): List<Client> = suspendTransaction {
        ClientDAO
            .find { ClientsTable.bankUBN eq bankUBN }
            .map(::daoToModel)
    }
}