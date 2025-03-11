package com.example.adapters.controllers

import com.example.application.usecases.transaction.TransactionFacade
import com.example.domain.entities.Transaction
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Application.transactionController(transactionFacade: TransactionFacade) {

    routing {
        route("/api/transactions") {

            post{
                try {
                    val transaction = call.receive<Transaction>()
                    transactionFacade.createTransaction(transaction)
                    call.respond(HttpStatusCode.Created, transaction)
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid UUID format")
                } catch (ex: JsonConvertException) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid JSON format")
                }
            }

            get("/byId/{transactionId}") {
                val transactionUUIDString = call.parameters["transactionId"]
                if (transactionUUIDString == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                try {
                    val transactionId = UUID.fromString(transactionUUIDString)
                    val transaction = transactionFacade.getTransaction(transactionId)

                    if (transaction == null) {
                        call.respond(HttpStatusCode.NotFound, "Transaction not found")
                    } else {
                        call.respond(HttpStatusCode.OK, transaction)
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
                    val transactionList = transactionFacade.getTransactionsByBank(bankUBN)

                    if (transactionList.isEmpty()) {
                        call.respond(HttpStatusCode.NotFound, "Transactions not found")
                    } else {
                        call.respond(HttpStatusCode.OK, transactionList)
                    }
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Incorrect UUID")
                }
            }

            delete("/{transactionId}") {
                val transactionId = UUID.fromString(call.parameters["transactionId"])
                if (transactionId == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@delete
                }

                if (transactionFacade.deleteTransaction(transactionId)) {
                    call.respond(HttpStatusCode.NoContent, "Transaction deleted successfully")
                } else {
                    call.respond(HttpStatusCode.NotFound, "Transaction not found")
                }
            }
        }
    }
}