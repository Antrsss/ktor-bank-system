package com.example.adapters.controllers

import com.example.adapters.controllers.common.*
import com.example.application.facades.BankFacade
import com.example.domain.entities.Bank
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*

fun Application.bankController(bankFacade: BankFacade) {
    //класс с логированием операций (получать роль по фй ди пользователя, токен, логировать)
    routing {
        /*route("/api/moder") // проверка роли пользователя
        route("/api/moder/get...")*/
        route("/api/banks") {

            post {
                call.handlePostRequest<Bank> { bankFacade.createBank(it) }
            }

            route("/get") {
                get {
                    call.handleGetAllRequest { bankFacade.getAllBanks() }
                }
                get("/{id}") {
                    call.handleGetRequest { bankFacade.getBank(it) }
                }
            }


            put("/update/bank-name/{id}") {
                val updatedBank = call.receive<Bank>()
                call.handlePutRequest<Bank, String>(
                    updateEntity = { id, bankName -> bankFacade.updateBankName(id, bankName) },
                    updatedField = updatedBank.bankName
                )
            }

            delete("/delete/{id}") {
                call.handleDeleteRequest { bankFacade.deleteBank(it) }
            }
        }
    }
}