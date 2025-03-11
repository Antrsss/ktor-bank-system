package com.example.adapters.controllers

import com.example.application.usecases.loan_obligation.LoanObligationFacade
import com.example.domain.abstracts.LoanObligation
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Application.loanObligationController(loanObligationFacade: LoanObligationFacade) {

    routing {
        route("/api/loans") {

            post {
                try {
                    val loan = call.receive<LoanObligation>()
                    loanObligationFacade.createLoan(loan)
                    call.respond(HttpStatusCode.Created, loan)
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid UUID format")
                } catch (ex: JsonConvertException) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid JSON format")
                }
            }

            get("/byId/{loanId}") {
                val loanIdString = call.parameters["loanId"]
                if (loanIdString == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                try {
                    val loanId = UUID.fromString(loanIdString)
                    val loan = loanObligationFacade.getLoanById(loanId)

                    if (loan == null) {
                        call.respond(HttpStatusCode.NotFound, "Loan not found")
                    } else {
                        call.respond(HttpStatusCode.OK, loan)
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
                    val loansList = loanObligationFacade.getLoansByOwner(ownerId)

                    if (loansList.isEmpty()) {
                        call.respond(HttpStatusCode.NotFound, "Owner has no loans")
                    } else {
                        call.respond(HttpStatusCode.OK, loansList)
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
                    val loansList = loanObligationFacade.getLoansByBank(bankUBN)

                    if (loansList.isEmpty()) {
                        call.respond(HttpStatusCode.NotFound, "Bank has no loans")
                    } else {
                        call.respond(HttpStatusCode.OK, loansList)
                    }
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Incorrect UUID")
                }
            }

            put("/loanPayAmount/{loanId}") {
                val loanId = UUID.fromString(call.parameters["loanId"])
                val updatedLoan = call.receive<LoanObligation>()

                try {
                    val result = loanObligationFacade.payForLoan(loanId, updatedLoan.payedAmount)
                    call.respond(HttpStatusCode.OK, result)
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.NotFound, "Loan with this id not found")
                }
            }

            put("/loanStatus/{loanId}") {
                val loanId = UUID.fromString(call.parameters["loanId"])
                val updatedLoan = call.receive<LoanObligation>()

                try {
                    val result = loanObligationFacade.updateLoanStatus(loanId, updatedLoan.status)
                    call.respond(HttpStatusCode.OK, result)
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.NotFound, "Loan with this id not found")
                }
            }

            delete("/{loanId}") {
                val loanId = UUID.fromString(call.parameters["loanId"])
                if (loanId == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@delete
                }

                if (loanObligationFacade.deleteLoan(loanId)) {
                    call.respond(HttpStatusCode.NoContent, "Loan deleted successfully")
                } else {
                    call.respond(HttpStatusCode.NotFound, "Loan not found")
                }
            }
        }
    }
}