package com.example.adapters.controllers

import com.example.adapters.controllers.common.*
import com.example.application.facades.SalaryProjectFacade
import com.example.domain.entities.SalaryProject
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*

fun Application.salaryProjectController(salaryProjectFacade: SalaryProjectFacade) {

    routing {
        route("/api/salary-projects") {

            post{
                call.handlePostRequest<SalaryProject> { salaryProjectFacade.createSalaryProject(it) }
            }

            route("/get") {
                get("/{id}") {
                    call.handleGetRequest { salaryProjectFacade.getSalaryProject(it) }
                }
                get("/by-bank/{id}") {
                    call.handleGetEntitiesByRequest { salaryProjectFacade.getSalaryProjectsByBank(it) }
                }
            }

            put("/update/status/{id}") {
                val updatedSalaryProject = call.receive<SalaryProject>()
                call.handlePutRequest(
                    updateEntity = { id, status -> salaryProjectFacade.updateSalaryProjectStatus(id, status) },
                    updatedField = updatedSalaryProject.status
                )
            }

            delete("/{id}") {
                call.handleDeleteRequest { salaryProjectFacade.deleteSalaryProject(it) }
            }
        }
    }
}