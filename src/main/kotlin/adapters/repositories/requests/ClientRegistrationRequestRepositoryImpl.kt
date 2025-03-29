package com.example.adapters.repositories.requests

import com.example.adapters.repositories.BanksTable
import com.example.adapters.repositories.suspendTransaction
import com.example.domain.ClientCountry
import com.example.domain.RequestStatus
import com.example.domain.entities.requests.ClientRegistrationRequest
import com.example.domain.repositories.common.RequestRepository
import com.example.domain.repositories.base.ImmutableRepository
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.util.*

object ClientRegistrationRequestsTable : IntIdTable("client_registration_requests") {
    val bankUBN = uuid("bank_ubn")
    val FIO = varchar("fio", 255)
    val passportSeries = varchar("passport_series", 10)
    val passportNumber = integer("passport_number")
    val phone = varchar("phone", 20)
    val email = varchar("email", 255)
    val password = varchar("password", 255)
    val country = enumerationByName("country", 50, ClientCountry::class).nullable()
    val requestStatus = enumerationByName("request_status", 50, RequestStatus::class)
    val requestId = uuid("request_id")
}

fun ResultRow.toClientRegistrationRequest(): ClientRegistrationRequest {
    return ClientRegistrationRequest(
        bankUBN = this[ClientRegistrationRequestsTable.bankUBN],
        FIO = this[ClientRegistrationRequestsTable.FIO],
        passportSeries = this[ClientRegistrationRequestsTable.passportSeries],
        passportNumber = this[ClientRegistrationRequestsTable.passportNumber],
        phone = this[ClientRegistrationRequestsTable.phone],
        email = this[ClientRegistrationRequestsTable.email],
        password = this[ClientRegistrationRequestsTable.password],
        country = this[ClientRegistrationRequestsTable.country],
        requestStatus = this[ClientRegistrationRequestsTable.requestStatus],
        requestId = this[ClientRegistrationRequestsTable.requestId]
    )
}

class ClientRegistrationRequestDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ClientRegistrationRequestDAO>(ClientRegistrationRequestsTable)

    var bankUBN by ClientRegistrationRequestsTable.bankUBN
    var FIO by ClientRegistrationRequestsTable.FIO
    var passportSeries by ClientRegistrationRequestsTable.passportSeries
    var passportNumber by ClientRegistrationRequestsTable.passportNumber
    var phone by ClientRegistrationRequestsTable.phone
    var email by ClientRegistrationRequestsTable.email
    var password by ClientRegistrationRequestsTable.password
    var country by ClientRegistrationRequestsTable.country
    var requestStatus by ClientRegistrationRequestsTable.requestStatus
    var requestId by ClientRegistrationRequestsTable.requestId
}

fun daoToModel(dao: ClientRegistrationRequestDAO) = ClientRegistrationRequest(
    bankUBN = dao.bankUBN,
    FIO = dao.FIO,
    passportSeries = dao.passportSeries,
    passportNumber = dao.passportNumber,
    phone = dao.phone,
    email = dao.email,
    password = dao.password,
    country = dao.country,
    requestStatus = dao.requestStatus,
    requestId = dao.requestId
)

class ClientRegistrationRequestRepositoryImpl
    : RequestRepository<ClientRegistrationRequest> {

    init {
        transaction {
            SchemaUtils.create(ClientRegistrationRequestsTable)
        }
    }

    override suspend fun create(entity: ClientRegistrationRequest): Unit = suspendTransaction {
        ClientRegistrationRequestDAO.new {
            bankUBN = entity.bankUBN
            FIO = entity.FIO
            passportSeries = entity.passportSeries
            passportNumber = entity.passportNumber
            phone = entity.phone
            email = entity.email
            password = entity.password
            country = entity.country
            requestStatus = entity.requestStatus
            requestId = entity.requestId
        }
    }

    override suspend fun get(id: UUID): ClientRegistrationRequest? = suspendTransaction {
        ClientRegistrationRequestDAO
            .find { ClientRegistrationRequestsTable.requestId eq id }
            .limit(1)
            .map(::daoToModel)
            .firstOrNull()
    }

    override suspend fun update(entity: ClientRegistrationRequest): ClientRegistrationRequest = suspendTransaction {
        ClientRegistrationRequestsTable.update({ ClientRegistrationRequestsTable.requestId eq entity.requestId }) {
            it[FIO] = entity.FIO
            it[passportSeries] = entity.passportSeries
            it[passportNumber] = entity.passportNumber
            it[phone] = entity.phone
            it[email] = entity.email
            it[password] = entity.password
            it[country] = entity.country
            it[requestStatus] = entity.requestStatus
            it[requestId] = entity.requestId
        }
        entity
    }

    override suspend fun delete(id: UUID): Boolean = suspendTransaction {
        val rowsDeleted = ClientRegistrationRequestsTable.deleteWhere {
            ClientRegistrationRequestsTable.requestId eq id
        }
        rowsDeleted == 1
    }

    override suspend fun getRequestsByBank(bankUBN: UUID): List<ClientRegistrationRequest> = suspendTransaction {
        ClientRegistrationRequestDAO
            .find { ClientRegistrationRequestsTable.bankUBN eq bankUBN }
            .map(::daoToModel)
    }
}