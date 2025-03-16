package com.example.adapters.repositories

import com.example.domain.ClientCountry
import com.example.domain.RequestStatus
import com.example.domain.entities.requests.ClientRegistrationRequest
import com.example.domain.repositories.requests.RequestRepository
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
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
        Database.connect(
            url = "jdbc:postgresql://localhost:5432/bank_db",
            driver = "org.postgresql.Driver",
            user = "postgres",
            password = "625100"
        )

        transaction {
            SchemaUtils.create(ClientRegistrationRequestsTable)
        }
    }

    override suspend fun createRequest(request: ClientRegistrationRequest): Unit = suspendTransaction {
        if (getRequest(request.requestId) != null) {
            ClientRegistrationRequestDAO.new {
                bankUBN = request.bankUBN
                FIO = request.FIO
                passportSeries = request.passportSeries
                passportNumber = request.passportNumber
                phone = request.phone
                email = request.email
                password = request.password
                country = request.country
                requestStatus = request.requestStatus
                requestId = request.requestId
            }
        }
    }

    override suspend fun getRequest(requestId: UUID): ClientRegistrationRequest? = suspendTransaction {
        ClientRegistrationRequestDAO
            .find { ClientRegistrationRequestsTable.requestId eq requestId }
            .limit(1)
            .map(::daoToModel)
            .firstOrNull()
    }

    override suspend fun getRequestsByBank(bankUBN: UUID): List<ClientRegistrationRequest> = suspendTransaction {
        ClientRegistrationRequestDAO
            .find { ClientRegistrationRequestsTable.bankUBN eq bankUBN }
            .map(::daoToModel)
    }

    override suspend fun deleteRequest(requestId: UUID): Boolean = suspendTransaction {
        val rowsDeleted = ClientRegistrationRequestsTable.deleteWhere {
            ClientRegistrationRequestsTable.requestId eq requestId
        }
        rowsDeleted == 1
    }
}