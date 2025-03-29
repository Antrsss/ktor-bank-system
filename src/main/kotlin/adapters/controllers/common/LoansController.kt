package com.example.adapters.controllers.common

import com.example.application.facades.loans.CreditFacade
import com.example.application.facades.loans.DeferredPaymentFacade
import com.example.domain.LoanStatus
import com.example.domain.entities.loan_obligations.Credit
import com.example.domain.entities.loan_obligations.DeferredPayment
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*

fun Application.loansController(
    creditFacade: CreditFacade,
    deferredPaymentFacade: DeferredPaymentFacade
) {
    routing {
        route("/api/credits") {

            post {
                call.handlePostRequest<Credit> { creditFacade.createLoan(it) }
            }

            route("/get") {
                get("/{id}") {
                    call.handleGetRequest { creditFacade.getLoan(it) }
                }
                get("/by-ownerId/{id}") {
                    call.handleGetEntitiesByRequest { creditFacade.getLoansByOwner(it) }
                }
                get("/by-bank/{id}") {
                    call.handleGetEntitiesByRequest { creditFacade.getLoansByBank(it) }
                }
            }

            route("/update") {
                put("/credit-status/{id}") {
                    val updatedCredit = call.receive<Credit>()
                    call.handlePutRequest<Credit, LoanStatus>(
                        updateEntity = { id, status -> creditFacade.updateLoanStatus(id, status) },
                        updatedField = updatedCredit.status,
                    )
                }
                put("/pay-for-credit/{id}") {
                    val updatedCredit = call.receive<Credit>()
                    call.handlePutRequest<Credit, Double>(
                        updateEntity = { id, amountToPay -> creditFacade.payForLoan(id, amountToPay) },
                        updatedField = updatedCredit.payedAmount,
                    )
                }
            }

            delete("/{id}") {
                call.handleDeleteRequest { creditFacade.deleteLoan(it) }
            }
        }

        route("/api/deferred-payments") {

            post {
                call.handlePostRequest<DeferredPayment> { deferredPaymentFacade.createLoan(it) }
            }

            route("/get") {
                get("/{id}") {
                    call.handleGetRequest { deferredPaymentFacade.getLoan(it) }
                }
                get("/by-ownerId/{id}") {
                    call.handleGetEntitiesByRequest { deferredPaymentFacade.getLoansByOwner(it) }
                }
                get("/by-bank/{id}") {
                    call.handleGetEntitiesByRequest { deferredPaymentFacade.getLoansByBank(it) }
                }
            }

            route("/update") {
                put("/deferred-payment-status/{id}") {
                    val updatedDeferredPayment = call.receive<DeferredPayment>()
                    call.handlePutRequest<DeferredPayment, LoanStatus>(
                        updateEntity = { id, status -> deferredPaymentFacade.updateLoanStatus(id, status) },
                        updatedField = updatedDeferredPayment.status,
                    )
                }
                put("/pay-for-deferred-payment/{id}") {
                    val updatedDeferredPayment = call.receive<DeferredPayment>()
                    call.handlePutRequest<DeferredPayment, Double>(
                        updateEntity = { id, amountToPay -> deferredPaymentFacade.payForLoan(id, amountToPay) },
                        updatedField = updatedDeferredPayment.payedAmount,
                    )
                }
            }

            delete("/{id}") {
                call.handleDeleteRequest { deferredPaymentFacade.deleteLoan(it) }
            }
        }
    }
}