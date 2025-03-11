package com.example.adapters.controllers

import com.example.application.usecases.salary_project.SalaryProjectFacade
import com.example.domain.entities.SalaryProject
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Application.salaryProjectController(salaryProjectFacade: SalaryProjectFacade) {

    routing {
        route("/api/salaryProjects") {

            post{
                try {
                    val salaryProject = call.receive<SalaryProject>()
                    salaryProjectFacade.createSalaryProject(salaryProject)
                    call.respond(HttpStatusCode.Created, salaryProject)
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid UUID format")
                } catch (ex: JsonConvertException) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid JSON format")
                }
            }

            get("/byId/{salaryProjectId}") {
                val salaryProjectUUIDString = call.parameters["salaryProjectId"]
                if (salaryProjectUUIDString == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                try {
                    val salaryProjectId = UUID.fromString(salaryProjectUUIDString)
                    val salaryProject = salaryProjectFacade.getSalaryProjectById(salaryProjectId)

                    if (salaryProject == null) {
                        call.respond(HttpStatusCode.NotFound, "Salary project not found")
                    } else {
                        call.respond(HttpStatusCode.OK, salaryProject)
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
                    val salaryProjectsList = salaryProjectFacade.getSalaryProjectsByBank(bankUBN)

                    if (salaryProjectsList.isEmpty()) {
                        call.respond(HttpStatusCode.NotFound, "Salary projects not found")
                    } else {
                        call.respond(HttpStatusCode.OK, salaryProjectsList)
                    }
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Incorrect UUID")
                }
            }

            put("/status/{salaryProjectId}") {
                val salaryProjectId = UUID.fromString(call.parameters["salaryProjectId"])
                val updatedSalaryProject = call.receive<SalaryProject>()

                try {
                    val result = salaryProjectFacade.updateSalaryProjectStatus(salaryProjectId, updatedSalaryProject.status)
                    call.respond(HttpStatusCode.OK, result)
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.NotFound, "Salary project with this id not found")
                }
            }

            delete("/{salaryProjectId}") {
                val salaryProjectId = UUID.fromString(call.parameters["salaryProjectId"])
                if (salaryProjectId == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@delete
                }

                if (salaryProjectFacade.deleteSalaryProject(salaryProjectId)) {
                    call.respond(HttpStatusCode.NoContent, "Salary project deleted successfully")
                } else {
                    call.respond(HttpStatusCode.NotFound, "Salary project not found")
                }
            }
        }
    }
}