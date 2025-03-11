package com.example.adapters.controllers

import com.example.application.usecases.bank.BankFacade
import com.example.domain.entities.Bank
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Application.bankController(bankFacade: BankFacade) {
    //класс с логированием операций (получать роль по фй ди пользователя, токен, логировать)
    routing {
        /*route("/api/moder") // проверка роли пользователя
        route("/api/moder/get...")*/
        route("/api/banks") {

            post {
                try {
                    val bank = call.receive<Bank>()
                    bankFacade.createBank(bank)
                    call.respond(HttpStatusCode.Created, bank)
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid UUID format")
                } catch (ex: JsonConvertException) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid JSON format")
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
                    val bank = bankFacade.getBankByUBN(bankUBN)

                    if (bank == null) {
                        call.respond(HttpStatusCode.NotFound, "Bank not found")
                    } else {
                        call.respond(HttpStatusCode.OK, bank)
                    }
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Incorrect UUID")
                }
            }

            get("/byName/{bankName}") {
                val bankName = call.parameters["bankName"]
                if (bankName == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                val bank = bankFacade.getBankByName(bankName)
                if (bank == null) {
                    call.respond(HttpStatusCode.NotFound, "Bank not found")
                } else {
                    call.respond(HttpStatusCode.OK, bank)
                }
            }

            get {
                val banks = bankFacade.getAllBanks()
                call.respond(HttpStatusCode.OK, banks)
            }

            put("/{bankUBN}") {
                val bankUBN = UUID.fromString(call.parameters["bankUBN"])
                val updatedBank = call.receive<Bank>()
                try {
                    val result = bankFacade.updateBank(bankUBN, updatedBank.bankName)
                    call.respond(HttpStatusCode.OK, result)
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.NotFound, "Bank with this id not found")
                }
            }

            delete("/{bankUBN}") {
                val bankUBN = UUID.fromString(call.parameters["bankUBN"])
                if (bankUBN == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@delete
                }

                if (bankFacade.deleteBank(bankUBN)) {
                    call.respond(HttpStatusCode.NoContent, "Bank deleted successfully")
                } else {
                    call.respond(HttpStatusCode.NotFound, "Bank not found")
                }
            }
        }
    }
}