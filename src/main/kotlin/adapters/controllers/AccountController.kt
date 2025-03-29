package com.example.adapters.controllers

import com.example.adapters.controllers.common.*
import com.example.application.facades.AccountFacade
import com.example.domain.AccountStatus
import com.example.domain.entities.Account
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*

fun Application.accountController(accountFacade: AccountFacade) {

    routing {
        route("/api/accounts") {

            post {
                call.handlePostRequest<Account> { accountFacade.createAccount(it) }
            }

            route("/get") {
                get("/{id}") {
                    call.handleGetRequest<Account> { accountFacade.getAccount(it) }
                }
                get("/by-owner/{id}") {
                    call.handleGetEntitiesByRequest { accountFacade.getAccountByOwner(it) }
                }
                get("/by-bank/{id}") {
                    call.handleGetEntitiesByRequest { accountFacade.getAccountByBank(it) }
                }
            }

            route("/update") {
                put("/status/{id}"){
                    val updatedAccount = call.receive<Account>()
                    call.handlePutRequest<Account, AccountStatus>(
                        updateEntity = { id, status -> accountFacade.updateAccountStatus(id, status) },
                        updatedField = updatedAccount.status
                    )
                }
                put("/name/{id}") {
                    val updatedAccount = call.receive<Account>()
                    call.handlePutRequest<Account, String>(
                        updateEntity = { id, accountName -> accountFacade.updateAccountName(id, accountName) },
                        updatedField = updatedAccount.accountName
                    )
                }
                put("/balance/{id}") {
                    val updatedAccount = call.receive<Account>()
                    call.handlePutRequest<Account, Double>(
                        updateEntity = { id, balance -> accountFacade.updateAccountBalance(id, balance) },
                        updatedField = updatedAccount.balance
                    )
                }
            }

            delete("/delete/{id}") {
                call.handleDeleteRequest { accountFacade.deleteAccount(it) }
            }
        }
    }
}