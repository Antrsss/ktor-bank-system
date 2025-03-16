package com.example.adapters.controllers.common

import com.example.application.facades.requests.*
import com.example.domain.entities.requests.*
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Application.requestController(
    clientRegistrationRequestFacade: ClientRegistrationRequestFacade,
    creditRequestFacade: CreditRequestFacade,
    deferredPaymentRequestFacade: DeferredPaymentRequestFacade,
    salaryProjectRequestFacade: SalaryProjectRequestFacade,
    transactionRequestFacade: TransactionRequestFacade,
) {

    routing {
        route("/api/requests/clients-registration") {

            post {
                try {
                    val clientRegRequest = call.receive<ClientRegistrationRequest>()
                    clientRegistrationRequestFacade.createRequest(clientRegRequest)
                    call.respond(HttpStatusCode.Created, clientRegRequest)
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid UUID format")
                } catch (ex: JsonConvertException) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid JSON format")
                }
            }

            get("/byId/{requestId}") {
                val requestIdString = call.parameters["requestId"]
                if (requestIdString == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                try {
                    val requestId = UUID.fromString(requestIdString)
                    val clientRegRequest = clientRegistrationRequestFacade.getRequest(requestId)

                    if (clientRegRequest == null) {
                        call.respond(HttpStatusCode.NotFound, "Reg request not found")
                    } else {
                        call.respond(HttpStatusCode.OK, clientRegRequest)
                    }
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Incorrect UUID")
                }
            }

            get("/byUBN/{bankUBN}") {
                val bankUBNString = call.parameters["bankUBN"]
                if (bankUBNString == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                try {
                    val bankUBN = UUID.fromString(bankUBNString)
                    val clientRegRequestsList = clientRegistrationRequestFacade.getRequestsByBank(bankUBN)

                    if (clientRegRequestsList.isEmpty()) {
                        call.respond(HttpStatusCode.NotFound, "Bank not found")
                    } else {
                        call.respond(HttpStatusCode.OK, clientRegRequestsList)
                    }
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Incorrect UUID")
                }
            }

            delete("/{requestId}") {
                val requestId = UUID.fromString(call.parameters["requestId"])
                if (requestId == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@delete
                }

                if (clientRegistrationRequestFacade.deleteRequest(requestId)) {
                    call.respond(HttpStatusCode.NoContent, "Reg request deleted successfully")
                } else {
                    call.respond(HttpStatusCode.NotFound, "Reg request not found")
                }
            }
        }

        route("/api/requests/credits") {
            post {
                try {
                    val creditRequest = call.receive<CreditRequest>()
                    creditRequestFacade.createRequest(creditRequest)
                    call.respond(HttpStatusCode.Created, creditRequest)
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid UUID format")
                } catch (ex: JsonConvertException) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid JSON format")
                }
            }

            get("/byId/{requestId}") {
                val requestIdString = call.parameters["requestId"]
                if (requestIdString == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                try {
                    val requestId = UUID.fromString(requestIdString)
                    val creditRequest = creditRequestFacade.getRequest(requestId)

                    if (creditRequest == null) {
                        call.respond(HttpStatusCode.NotFound, "Credit request not found")
                    } else {
                        call.respond(HttpStatusCode.OK, creditRequest)
                    }
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Incorrect UUID")
                }
            }

            get("/byUBN/{bankUBN}") {
                val bankUBNString = call.parameters["bankUBN"]
                if (bankUBNString == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                try {
                    val bankUBN = UUID.fromString(bankUBNString)
                    val creditRequestsList = creditRequestFacade.getRequestsByBank(bankUBN)

                    if (creditRequestsList.isEmpty()) {
                        call.respond(HttpStatusCode.NotFound, "Credit request not found")
                    } else {
                        call.respond(HttpStatusCode.OK, creditRequestsList)
                    }
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Incorrect UUID")
                }
            }

            delete("/{requestId}") {
                val requestId = UUID.fromString(call.parameters["requestId"])
                if (requestId == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@delete
                }

                if (creditRequestFacade.deleteRequest(requestId)) {
                    call.respond(HttpStatusCode.NoContent, "Credit request deleted successfully")
                } else {
                    call.respond(HttpStatusCode.NotFound, "Credit request not found")
                }
            }
        }

        route("/api/requests/deferred-payments") {

            post {
                try {
                    val deferredPaymentRequest = call.receive<DeferredPaymentRequest>()
                    deferredPaymentRequestFacade.createRequest(deferredPaymentRequest)
                    call.respond(HttpStatusCode.Created, deferredPaymentRequest)
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid UUID format")
                } catch (ex: JsonConvertException) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid JSON format")
                }
            }

            get("/byId/{requestId}") {
                val requestIdString = call.parameters["requestId"]
                if (requestIdString == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                try {
                    val requestId = UUID.fromString(requestIdString)
                    val deferredPaymentRequest = deferredPaymentRequestFacade.getRequest(requestId)

                    if (deferredPaymentRequest == null) {
                        call.respond(HttpStatusCode.NotFound, "Deff payment request not found")
                    } else {
                        call.respond(HttpStatusCode.OK, deferredPaymentRequest)
                    }
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Incorrect UUID")
                }
            }

            get("/byUBN/{bankUBN}") {
                val bankUBNString = call.parameters["bankUBN"]
                if (bankUBNString == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                try {
                    val bankUBN = UUID.fromString(bankUBNString)
                    val deferredPaymentRequestsList = deferredPaymentRequestFacade.getRequestsByBank(bankUBN)

                    if (deferredPaymentRequestsList.isEmpty()) {
                        call.respond(HttpStatusCode.NotFound, "Def payment request not found")
                    } else {
                        call.respond(HttpStatusCode.OK, deferredPaymentRequestsList)
                    }
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Incorrect UUID")
                }
            }

            delete("/{requestId}") {
                val requestId = UUID.fromString(call.parameters["requestId"])
                if (requestId == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@delete
                }

                if (creditRequestFacade.deleteRequest(requestId)) {
                    call.respond(HttpStatusCode.NoContent, "Def payment request deleted successfully")
                } else {
                    call.respond(HttpStatusCode.NotFound, "Def payment request not found")
                }
            }
        }

        route("/api/requests/salary-projects") {
            post {
                try {
                    val salaryProjectRequest = call.receive<SalaryProjectRequest>()
                    salaryProjectRequestFacade.createRequest(salaryProjectRequest)
                    call.respond(HttpStatusCode.Created, salaryProjectRequest)
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid UUID format")
                } catch (ex: JsonConvertException) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid JSON format")
                }
            }

            get("/byId/{requestId}") {
                val requestIdString = call.parameters["requestId"]
                if (requestIdString == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                try {
                    val requestId = UUID.fromString(requestIdString)
                    val salaryProjectRequest = salaryProjectRequestFacade.getRequest(requestId)

                    if (salaryProjectRequest == null) {
                        call.respond(HttpStatusCode.NotFound, "Salary project request not found")
                    } else {
                        call.respond(HttpStatusCode.OK, salaryProjectRequest)
                    }
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Incorrect UUID")
                }
            }

            get("/byUBN/{bankUBN}") {
                val bankUBNString = call.parameters["bankUBN"]
                if (bankUBNString == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                try {
                    val bankUBN = UUID.fromString(bankUBNString)
                    val salaryProjectRequestsList = deferredPaymentRequestFacade.getRequestsByBank(bankUBN)

                    if (salaryProjectRequestsList.isEmpty()) {
                        call.respond(HttpStatusCode.NotFound, "Salary project request not found")
                    } else {
                        call.respond(HttpStatusCode.OK, salaryProjectRequestsList)
                    }
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Incorrect UUID")
                }
            }

            delete("/{requestId}") {
                val requestId = UUID.fromString(call.parameters["requestId"])
                if (requestId == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@delete
                }

                if (salaryProjectRequestFacade.deleteRequest(requestId)) {
                    call.respond(HttpStatusCode.NoContent, "Salary project request deleted successfully")
                } else {
                    call.respond(HttpStatusCode.NotFound, "Salary project request not found")
                }
            }
        }

        route("/api/requests/transactions") {
            post {
                try {
                    val transactionRequest = call.receive<TransactionRequest>()
                    transactionRequestFacade.createRequest(transactionRequest)
                    call.respond(HttpStatusCode.Created, transactionRequest)
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid UUID format")
                } catch (ex: JsonConvertException) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid JSON format")
                }
            }

            get("/byId/{requestId}") {
                val requestIdString = call.parameters["requestId"]
                if (requestIdString == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                try {
                    val requestId = UUID.fromString(requestIdString)
                    val transactionRequest = transactionRequestFacade.getRequest(requestId)

                    if (transactionRequest == null) {
                        call.respond(HttpStatusCode.NotFound, "Transaction project request not found")
                    } else {
                        call.respond(HttpStatusCode.OK, transactionRequest)
                    }
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Incorrect UUID")
                }
            }

            get("/byUBN/{bankUBN}") {
                val bankUBNString = call.parameters["bankUBN"]
                if (bankUBNString == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                try {
                    val bankUBN = UUID.fromString(bankUBNString)
                    val transactionRequestsList = transactionRequestFacade.getRequestsByBank(bankUBN)

                    if (transactionRequestsList.isEmpty()) {
                        call.respond(HttpStatusCode.NotFound, "Transaction request not found")
                    } else {
                        call.respond(HttpStatusCode.OK, transactionRequestsList)
                    }
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Incorrect UUID")
                }
            }

            delete("/{requestId}") {
                val requestId = UUID.fromString(call.parameters["requestId"])
                if (requestId == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@delete
                }

                if (transactionRequestFacade.deleteRequest(requestId)) {
                    call.respond(HttpStatusCode.NoContent, "Transaction request deleted successfully")
                } else {
                    call.respond(HttpStatusCode.NotFound, "Transaction request not found")
                }
            }
        }
    }
}