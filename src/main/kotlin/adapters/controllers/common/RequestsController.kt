package com.example.adapters.controllers.common

import com.example.application.facades.requests.*
import com.example.application.facades.users.ClientFacade
import com.example.domain.RequestStatus
import com.example.domain.entities.requests.*
import com.example.domain.entities.users.Client
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import java.util.*

fun Application.requestsController(
    clientFacade: ClientFacade,
    clientRegistrationRequestFacade: ClientRegistrationRequestFacade,
    creditRequestFacade: CreditRequestFacade,
    deferredPaymentRequestFacade: DeferredPaymentRequestFacade,
    salaryProjectRequestFacade: SalaryProjectRequestFacade,
    transactionRequestFacade: TransactionRequestFacade,
) {

    routing {
        route("/api/requests") {

            route("/client-registrations") {

                post {
                    call.handlePostRequest<ClientRegistrationRequest> {
                        clientRegistrationRequestFacade.createRequest(it)
                    }
                }

                route("/get") {
                    get("/{id}") {
                        call.handleGetRequest { clientRegistrationRequestFacade.getRequest(it) }
                    }
                    get("/by-bank/{id}") {
                        call.handleGetEntitiesByRequest {
                            clientRegistrationRequestFacade.getRequestsByBank(it)
                        }
                    }
                }

                put("/update/status/{id}") {
                    val updatedRequest = call.receive<ClientRegistrationRequest>()
                    call.handlePutRequest<ClientRegistrationRequest, RequestStatus>(
                        updateEntity = { id, status -> clientRegistrationRequestFacade.updateRequestStatus(id, status) },
                        updatedField = updatedRequest.requestStatus
                    )
                }

                delete("/{id}") {
                    call.handleDeleteRequest { clientRegistrationRequestFacade.deleteRequest(it) }
                }
            }

            route("/credits") {

                post {
                    call.handlePostRequest<CreditRequest> {
                        creditRequestFacade.createRequest(it)
                    }
                }

                route("/get") {
                    get("/{id}") {
                        call.handleGetRequest { creditRequestFacade.getRequest(it) }
                    }
                    get("/by-bank/{id}") {
                        call.handleGetEntitiesByRequest {
                            creditRequestFacade.getRequestsByBank(it)
                        }
                    }
                }

                put("/update/status/{id}") {
                    val updatedRequest = call.receive<ClientRegistrationRequest>()
                    call.handlePutRequest<ClientRegistrationRequest, RequestStatus>(
                        updateEntity = { id, status -> clientRegistrationRequestFacade.updateRequestStatus(id, status) },
                        updatedField = updatedRequest.requestStatus
                    )
                }

                delete("/{id}") {
                    call.handleDeleteRequest { creditRequestFacade.deleteRequest(it) }
                }
            }

            route("/deferred-payments") {

                post {
                    call.handlePostRequest<DeferredPaymentRequest> {
                        deferredPaymentRequestFacade.createRequest(it)
                    }
                }

                route("/get") {
                    get("/{id}") {
                        call.handleGetRequest { deferredPaymentRequestFacade.getRequest(it) }
                    }
                    get("/by-bank/{id}") {
                        call.handleGetEntitiesByRequest {
                            deferredPaymentRequestFacade.getRequestsByBank(it)
                        }
                    }
                }

                put("/update/status/{id}") {
                    val updatedRequest = call.receive<ClientRegistrationRequest>()
                    call.handlePutRequest<ClientRegistrationRequest, RequestStatus>(
                        updateEntity = { id, status -> clientRegistrationRequestFacade.updateRequestStatus(id, status) },
                        updatedField = updatedRequest.requestStatus
                    )
                }

                delete("/{id}") {
                    call.handleDeleteRequest { deferredPaymentRequestFacade.deleteRequest(it) }
                }
            }

            route("/salary-projects") {

                post {
                    call.handlePostRequest<SalaryProjectRequest> {
                        salaryProjectRequestFacade.createRequest(it)
                    }

                    route("/get") {
                        get("/{id}") {
                            call.handleGetRequest { salaryProjectRequestFacade.getRequest(it) }
                        }
                        get("/by-bank/{id}") {
                            call.handleGetEntitiesByRequest {
                                salaryProjectRequestFacade.getRequestsByBank(it)
                            }
                        }
                    }

                    put("/update/status/{id}") {
                        val updatedRequest = call.receive<ClientRegistrationRequest>()
                        call.handlePutRequest<ClientRegistrationRequest, RequestStatus>(
                            updateEntity = { id, status -> clientRegistrationRequestFacade.updateRequestStatus(id, status) },
                            updatedField = updatedRequest.requestStatus
                        )
                    }

                    delete("/{id}") {
                        call.handleDeleteRequest { salaryProjectRequestFacade.deleteRequest(it) }
                    }
                }
            }

            route("/transactions") {

                post {
                    call.handlePostRequest<TransactionRequest> {
                        transactionRequestFacade.createRequest(it)
                    }
                }

                route("/get") {
                    get("/{id}") {
                        call.handleGetRequest { transactionRequestFacade.getRequest(it) }
                    }
                    get("/by-bank/{id}") {
                        call.handleGetEntitiesByRequest {
                            transactionRequestFacade.getRequestsByBank(it)
                        }
                    }
                }

                put("/update/status/{id}") {
                    val updatedRequest = call.receive<ClientRegistrationRequest>()
                    call.handlePutRequest<ClientRegistrationRequest, RequestStatus>(
                        updateEntity = { id, status -> clientRegistrationRequestFacade.updateRequestStatus(id, status) },
                        updatedField = updatedRequest.requestStatus
                    )
                }

                delete("/{id}") {
                    call.handleDeleteRequest { transactionRequestFacade.deleteRequest(it) }
                }
            }
        }
    }
}