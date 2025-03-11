package com.example.adapters.controllers

import com.example.application.usecases.account.AccountFacade
import com.example.domain.entities.Account
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Application.accountController(accountFacade: AccountFacade) {

    routing {
        route("/api/accounts") {

            post {
                try {
                    val account = call.receive<Account>()
                    accountFacade.createAccount(account)
                    call.respond(HttpStatusCode.Created, account)
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid UUID format")
                } catch (ex: JsonConvertException) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid JSON format")
                }
            }

            get("/byId/{accountId}") {
                val accountIdString = call.parameters["accountId"]
                if (accountIdString == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                try {
                    val accountId = UUID.fromString(accountIdString)
                    val account = accountFacade.getAccountById(accountId)

                    if (account == null) {
                        call.respond(HttpStatusCode.NotFound, "Account not found")
                    } else {
                        call.respond(HttpStatusCode.OK, account)
                    }
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Incorrect UUID")
                }
            }

            get("/byOwnerId/{ownerId}") {
                val ownerIdString = call.parameters["ownerId"]
                if (ownerIdString == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                try {
                    val ownerId = UUID.fromString(ownerIdString)
                    val accountsList = accountFacade.getAccountByOwner(ownerId)

                    if (accountsList.isEmpty()) {
                        call.respond(HttpStatusCode.NotFound, "Owner has no accounts")
                    } else {
                        call.respond(HttpStatusCode.OK, accountsList)
                    }
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Incorrect UUID")
                }
            }

            get("/byBankUBN/{bankUBN}") {
                val bankUBNString = call.parameters["bankUBN"]
                if (bankUBNString == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                try {
                    val bankUBN = UUID.fromString(bankUBNString)
                    val accountsList = accountFacade.getAccountByBank(bankUBN)

                    if (accountsList.isEmpty()) {
                        call.respond(HttpStatusCode.NotFound, "Bank has no accounts")
                    } else {
                        call.respond(HttpStatusCode.OK, accountsList)
                    }
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Incorrect UUID")
                }
            }

            get {
                val accounts = accountFacade.getAllAccounts()
                call.respond(HttpStatusCode.OK, accounts)
            }

            put("/accountName/{accountId}") {
                val accountId = UUID.fromString(call.parameters["accountId"])
                val updatedAccount = call.receive<Account>()

                try {
                    val result = accountFacade.updateAccountName(accountId, updatedAccount.accountName)
                    call.respond(HttpStatusCode.OK, result)
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.NotFound, "Account with this id not found")
                }
            }

            put("/accountBalance/{accountId}") {
                val accountId = UUID.fromString(call.parameters["accountId"])
                val updatedAccount = call.receive<Account>()

                try {
                    val result = accountFacade.updateAccountBalance(accountId, updatedAccount.balance)
                    call.respond(HttpStatusCode.OK, result)
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.NotFound, "Account with this id not found")
                }
            }

            put("/accountStatus/{accountId}") {
                val accountId = UUID.fromString(call.parameters["accountId"])
                val updatedAccount = call.receive<Account>()

                try {
                    val result = accountFacade.updateAccountStatus(accountId, updatedAccount.status)
                    call.respond(HttpStatusCode.OK, result)
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.NotFound, "Account with this id not found")
                }
            }

            delete("/{accountId}") {
                val accountId = UUID.fromString(call.parameters["accountId"])
                if (accountId == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@delete
                }

                if (accountFacade.deleteAccount(accountId)) {
                    call.respond(HttpStatusCode.NoContent, "Account deleted successfully")
                } else {
                    call.respond(HttpStatusCode.NotFound, "Account not found")
                }
            }
        }
    }
}