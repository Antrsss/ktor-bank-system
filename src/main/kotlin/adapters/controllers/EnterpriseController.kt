package com.example.adapters.controllers

import com.example.application.usecases.enterprise.EnterpriseFacade
import com.example.domain.entities.Enterprise
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Application.enterpriseController(enterpriseFacade: EnterpriseFacade) {

    routing {
        route("/api/enterprises") {

            post{
                try {
                    val enterprise = call.receive<Enterprise>()
                    enterpriseFacade.createEnterprise(enterprise)
                    call.respond(HttpStatusCode.Created, enterprise)
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid UUID format")
                } catch (ex: JsonConvertException) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid JSON format")
                }
            }

            get("/byId/{enterpriseId}") {
                val enterpriseIdString = call.parameters["enterpriseId"]
                if (enterpriseIdString == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                try {
                    val enterpriseId = UUID.fromString(enterpriseIdString)
                    val enterprise = enterpriseFacade.getEnterpriseById(enterpriseId)

                    if (enterprise == null) {
                        call.respond(HttpStatusCode.NotFound, "Enterprise not found")
                    } else {
                        call.respond(HttpStatusCode.OK, enterprise)
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
                    val enterprisesList = enterpriseFacade.getAllBankEnterprises(bankUBN)

                    if (enterprisesList.isEmpty()) {
                        call.respond(HttpStatusCode.NotFound, "Enterprises not found")
                    } else {
                        call.respond(HttpStatusCode.OK, enterprisesList)
                    }
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Incorrect UUID")
                }
            }

            put("/legalName/{enterpriseId}") {
                val accountId = UUID.fromString(call.parameters["enterpriseId"])
                val updatedEnterprise = call.receive<Enterprise>()

                try {
                    val result = enterpriseFacade.updateEnterpriseLegalName(accountId, updatedEnterprise.legalName)
                    call.respond(HttpStatusCode.OK, result)
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.NotFound, "Enterprise with this id not found")
                }
            }

            put("/bankUBN/{enterpriseId}") {
                val accountId = UUID.fromString(call.parameters["enterpriseId"])
                val updatedEnterprise = call.receive<Enterprise>()

                try {
                    val result = enterpriseFacade.updateEnterpriseBankUBN(accountId, updatedEnterprise.bankUBN)
                    call.respond(HttpStatusCode.OK, result)
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.NotFound, "Enterprise with this id not found")
                }
            }

            put("/legalAdress/{enterpriseId}") {
                val accountId = UUID.fromString(call.parameters["enterpriseId"])
                val updatedEnterprise = call.receive<Enterprise>()

                try {
                    val result = enterpriseFacade.updateEnterpriseLegalAdress(accountId, updatedEnterprise.legalAdress)
                    call.respond(HttpStatusCode.OK, result)
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.NotFound, "Enterprise with this id not found")
                }
            }

            delete("/{enterpriseId}") {
                val enterpriseId = UUID.fromString(call.parameters["enterpriseId"])
                if (enterpriseId == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@delete
                }

                if (enterpriseFacade.deleteEnterprise(enterpriseId)) {
                    call.respond(HttpStatusCode.NoContent, "Enterprise deleted successfully")
                } else {
                    call.respond(HttpStatusCode.NotFound, "Enterprise not found")
                }
            }
        }
    }
}