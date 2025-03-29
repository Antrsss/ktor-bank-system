package com.example.adapters.controllers

import com.example.adapters.controllers.common.*
import com.example.application.facades.EnterpriseFacade
import com.example.domain.entities.Enterprise
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*

fun Application.enterpriseController(enterpriseFacade: EnterpriseFacade) {

    routing {
        route("/api/enterprises") {

            post {
                call.handlePostRequest<Enterprise> { enterpriseFacade.createEnterprise(it) }
            }

            route("/get") {
                get("/{id}") {
                    call.handleGetRequest { enterpriseFacade.getEnterprise(it) }
                }
                get("/by-bank/{id}") {
                    call.handleGetEntitiesByRequest { enterpriseFacade.getEnterprisesByBank(it) }
                }
            }

            route("/update") {
                put("/legal-name/{id}") {
                    val updatedEnterprise = call.receive<Enterprise>()
                    call.handlePutRequest<Enterprise, String>(
                        updateEntity = { id, legalName -> enterpriseFacade.updateEnterpriseLegalName(id, legalName) },
                        updatedField = updatedEnterprise.legalName
                    )
                }
                put("/legal-adress/{id}") {
                    val updatedEnterprise = call.receive<Enterprise>()
                    call.handlePutRequest<Enterprise, String>(
                        updateEntity = { id, legalAdress -> enterpriseFacade.updateEnterpriseLegalAdress(id, legalAdress) },
                        updatedField = updatedEnterprise.legalAdress
                    )
                }
            }

            delete("/{id}") {
                call.handleDeleteRequest { enterpriseFacade.deleteEnterprise(it) }
            }
        }
    }
}