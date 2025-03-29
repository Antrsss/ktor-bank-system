package com.example.adapters.controllers

import com.example.adapters.controllers.common.handlePostRequest
import com.example.adapters.controllers.common.handleDeleteRequest
import com.example.adapters.controllers.common.handleGetEntitiesByRequest
import com.example.adapters.controllers.common.handleGetRequest
import com.example.application.facades.TransactionFacade
import com.example.domain.entities.Transaction
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.transactionController(transactionFacade: TransactionFacade) {

    routing {
        route("/api/transactions") {

            post{

                call.handlePostRequest<Transaction> {
                    transactionFacade.createTransaction(it)
                }
            }

            route("/get") {
                get("/{id}") {
                    call.handleGetRequest<Transaction> { transactionFacade.getTransaction(it) }
                }
                get("/by-account/{id}") {
                    call.handleGetEntitiesByRequest { transactionFacade.getTransactionsByAccount(it) }
                }
                get("/by-bank/{id}") {
                    call.handleGetEntitiesByRequest { transactionFacade.getTransactionsByBank(it) }
                }
            }

            delete("/{id}") {
                call.handleDeleteRequest { transactionFacade.deleteTransaction(it) }
            }
        }
    }
}