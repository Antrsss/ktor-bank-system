package com.example.adapters.controllers.common

import com.example.application.facades.users.*
import com.example.domain.entities.users.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*

fun Application.usersController(
    clientFacade: ClientFacade,
    foreignClientFacade: ForeignClientFacade,
    adminFacade: AdminFacade,
    managerFacade: ManagerFacade,
    operatorFacade: OperatorFacade,
    outsideSpecialistFacade: OutsideSpecialistFacade
) {
    routing {

        route("/api/users") {

            route("/clients") {

                post {
                    call.handlePostRequest<Client> { clientFacade.createUser(it) }
                }

                route("/get") {
                    get("{id}") {
                        call.handleGetRequest { clientFacade.getUser(it) }
                    }
                    get("/by-email/{id}") {
                        call.handleGetEntityByStringRequest { clientFacade.getUserByEmail(it) }
                    }
                    get("/by-bank/{id}") {
                        call.handleGetEntitiesByRequest { clientFacade.getUsersByBank(it) }
                    }
                }

                put("/email/{id}") {
                    val updatedClient = call.receive<Client>()
                    call.handlePutRequest<Client, String>(
                        updateEntity = { id, newEmail -> clientFacade.updateUserEmail(id, newEmail) },
                        updatedField = updatedClient.email
                    )
                }

                delete("/{id}") {
                    call.handleDeleteRequest { clientFacade.deleteUser(it) }
                }
            }

            route("/foreign-clients") {

                post {
                    call.handlePostRequest<ForeignClient> { foreignClientFacade.createUser(it) }
                }

                route("/get") {
                    get("{id}") {
                        call.handleGetRequest { foreignClientFacade.getUser(it) }
                    }
                    get("/by-email/{id}") {
                        call.handleGetEntityByStringRequest { foreignClientFacade.getUserByEmail(it) }
                    }
                    get("/by-bank/{id}") {
                        call.handleGetEntitiesByRequest { foreignClientFacade.getUsersByBank(it) }
                    }
                }

                put("/email") {
                    val updatedForeignClient = call.receive<ForeignClient>()
                    call.handlePutRequest<ForeignClient, String>(
                        updateEntity = { id, newEmail -> foreignClientFacade.updateUserEmail(id, newEmail) },
                        updatedField = updatedForeignClient.email
                    )
                }

                delete("/{id}") {
                    call.handleDeleteRequest { foreignClientFacade.deleteUser(it) }
                }
            }

            route("/outside-specialists") {

                post {
                    call.handlePostRequest<OutsideSpecialist> { outsideSpecialistFacade.createUser(it) }
                }

                route("/get") {
                    get("{id}") {
                        call.handleGetRequest { outsideSpecialistFacade.getUser(it) }
                    }
                    get("/by-email/{id}") {
                        call.handleGetEntityByStringRequest { outsideSpecialistFacade.getUserByEmail(it) }
                    }
                    get("/by-bank/{id}") {
                        call.handleGetEntitiesByRequest { outsideSpecialistFacade.getUsersByBank(it) }
                    }
                }

                put("/email/{id}") {
                    val updatedOutsideSpecialist = call.receive<OutsideSpecialist>()
                    call.handlePutRequest<OutsideSpecialist, String>(
                        updateEntity = { id, newEmail -> outsideSpecialistFacade.updateUserEmail(id, newEmail) },
                        updatedField = updatedOutsideSpecialist.email
                    )
                }

                delete("/{id}") {
                    call.handleDeleteRequest { outsideSpecialistFacade.deleteUser(it) }
                }
            }
        }

        route("/api/employees") {

            route("/admins") {

                post {
                    call.handlePostRequest<Admin> { adminFacade.createUser(it) }
                }

                route("/get") {
                    get("{id}") {
                        call.handleGetRequest { adminFacade.getUser(it) }
                    }
                    get("/by-email/{id}") {
                        call.handleGetEntityByStringRequest { adminFacade.getUserByEmail(it) }
                    }
                    get("/by-bank/{id}") {
                        call.handleGetEntitiesByRequest { adminFacade.getUsersByBank(it) }
                    }
                }

                put("/email") {
                    val updatedAdmin = call.receive<Admin>()
                    call.handlePutRequest<Admin, String>(
                        updateEntity = { id, newEmail -> adminFacade.updateUserEmail(id, newEmail) },
                        updatedField = updatedAdmin.email
                    )
                }

                delete("/{id}") {
                    call.handleDeleteRequest { adminFacade.deleteUser(it) }
                }
            }

            route("/managers") {

                post {
                    call.handlePostRequest<Manager> { managerFacade.createUser(it) }
                }

                route("/get") {
                    get("{id}") {
                        call.handleGetRequest { managerFacade.getUser(it) }
                    }
                    get("/by-email/{id}") {
                        call.handleGetEntityByStringRequest { managerFacade.getUserByEmail(it) }
                    }
                    get("/by-bank/{id}") {
                        call.handleGetEntitiesByRequest { managerFacade.getUsersByBank(it) }
                    }
                }

                put("/email") {
                    val updatedManager = call.receive<Manager>()
                    call.handlePutRequest<Manager, String>(
                        updateEntity = { id, newEmail -> managerFacade.updateUserEmail(id, newEmail) },
                        updatedField = updatedManager.email
                    )
                }

                delete("/{id}") {
                    call.handleDeleteRequest { managerFacade.deleteUser(it) }
                }
            }

            route("/operators") {

                post {
                    call.handlePostRequest<Operator> { operatorFacade.createUser(it) }
                }

                route("/get") {
                    get("{id}") {
                        call.handleGetRequest { operatorFacade.getUser(it) }
                    }
                    get("/by-email/{id}") {
                        call.handleGetEntityByStringRequest { operatorFacade.getUserByEmail(it) }
                    }
                    get("/by-bank/{id}") {
                        call.handleGetEntitiesByRequest { operatorFacade.getUsersByBank(it) }
                    }
                }

                put("/email") {
                    val updatedOperator = call.receive<Operator>()
                    call.handlePutRequest<Operator, String>(
                        updateEntity = { id, newEmail -> operatorFacade.updateUserEmail(id, newEmail) },
                        updatedField = updatedOperator.email
                    )
                }

                delete("/{id}") {
                    call.handleDeleteRequest { operatorFacade.deleteUser(it) }
                }
            }
        }
    }
}