package com.example.adapters.controllers.common

import com.example.adapters.controllers.handleCreateRequest
import com.example.application.facades.users.*
import com.example.domain.abstracts.User
import com.example.domain.entities.users.*
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Application.userController(
    clientFacade: ClientFacade,
    foreignClientFacade: ForeignClientFacade,
    adminFacade: AdminFacade,
    managerFacade: ManagerFacade,
    operatorFacade: OperatorFacade,
    outsideSpecialistFacade: OutsideSpecialistFacade
) {
    routing {

        route("/api/users/clients") {
            post {
                call.handleCreateRequest<Client> { clientFacade.createUser(it) }
            }

            get("/by-id/{clientId}") {
                val clientIdString = call.parameters["clientId"]
                if (clientIdString == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                try {
                    val clientId = UUID.fromString(clientIdString)
                    val client = clientFacade.getUser(clientId)

                    if (client == null) {
                        call.respond(HttpStatusCode.NotFound, "Client not found")
                    } else {
                        call.respond(HttpStatusCode.OK, client)
                    }
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Incorrect UUID")
                }
            }

            get("/by-email/{email}") {
                val email = call.parameters["email"]
                if (email == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                try {
                    val client = clientFacade.getUserByEmail(email)

                    if (client == null) {
                        call.respond(HttpStatusCode.NotFound, "Client not found")
                    } else {
                        call.respond(HttpStatusCode.OK, client)
                    }
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Incorrect UUID")
                }
            }

            get("/by-phone/{phone-number}") {
                val phoneNumber = call.parameters["phone-number"]
                if (phoneNumber == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                try {
                    val client = clientFacade.getUserByPhone(phoneNumber)

                    if (client == null) {
                        call.respond(HttpStatusCode.NotFound, "Client not found")
                    } else {
                        call.respond(HttpStatusCode.OK, client)
                    }
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Incorrect UUID")
                }
            }

            get("/by-bank/{bankUBN}") {
                val bankUBNString = call.parameters["bankUBN"]
                if (bankUBNString == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                try {
                    val bankUBN = UUID.fromString(bankUBNString)
                    val clientList = clientFacade.getUsersByBank(bankUBN)

                    if (clientList.isEmpty()) {
                        call.respond(HttpStatusCode.NotFound, "Clients not found")
                    } else {
                        call.respond(HttpStatusCode.OK, clientList)
                    }
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Incorrect UUID")
                }
            }

            delete("/{requestId}") {
                val clientId = UUID.fromString(call.parameters["requestId"])
                if (clientId == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@delete
                }

                if (clientFacade.deleteUser(clientId)) {
                    call.respond(HttpStatusCode.NoContent, "Client deleted successfully")
                } else {
                    call.respond(HttpStatusCode.NotFound, "Client not found")
                }
            }
        }

        route("/api/users/foreign-clients") {
            post {
                try {
                    val foreignClient = call.receive<ForeignClient>()
                    foreignClientFacade.createUser(foreignClient)
                    call.respond(HttpStatusCode.Created, foreignClient)
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid UUID format")
                } catch (ex: JsonConvertException) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid JSON format")
                }
            }

            get("/by-id/{clientId}") {
                val clientIdString = call.parameters["clientId"]
                if (clientIdString == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                try {
                    val clientId = UUID.fromString(clientIdString)
                    val foreignClient = foreignClientFacade.getUser(clientId)

                    if (foreignClient == null) {
                        call.respond(HttpStatusCode.NotFound, "Foreign client not found")
                    } else {
                        call.respond(HttpStatusCode.OK, foreignClient)
                    }
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Incorrect UUID")
                }
            }

            get("/by-email/{email}") {
                val email = call.parameters["email"]
                if (email == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                try {
                    val foreignClient = foreignClientFacade.getUserByEmail(email)

                    if (foreignClient == null) {
                        call.respond(HttpStatusCode.NotFound, "Foreign client not found")
                    } else {
                        call.respond(HttpStatusCode.OK, foreignClient)
                    }
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Incorrect UUID")
                }
            }

            get("/by-phone/{phone-number}") {
                val phoneNumber = call.parameters["phone-number"]
                if (phoneNumber == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                try {
                    val foreignClient = foreignClientFacade.getUserByPhone(phoneNumber)

                    if (foreignClient == null) {
                        call.respond(HttpStatusCode.NotFound, "Foreign client not found")
                    } else {
                        call.respond(HttpStatusCode.OK, foreignClient)
                    }
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Incorrect UUID")
                }
            }

            get("/by-bank/{bankUBN}") {
                val bankUBNString = call.parameters["bankUBN"]
                if (bankUBNString == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                try {
                    val bankUBN = UUID.fromString(bankUBNString)
                    val foreignClientList = foreignClientFacade.getUsersByBank(bankUBN)

                    if (foreignClientList.isEmpty()) {
                        call.respond(HttpStatusCode.NotFound, "Foreign clients not found")
                    } else {
                        call.respond(HttpStatusCode.OK, foreignClientList)
                    }
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Incorrect UUID")
                }
            }

            delete("/{clientId}") {
                val clientIdString = call.parameters["clientId"]
                if (clientIdString == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@delete
                }

                try {
                    val clientId = UUID.fromString(clientIdString)
                    if (foreignClientFacade.deleteUser(clientId)) {
                        call.respond(HttpStatusCode.NoContent, "Foreign client deleted successfully")
                    } else {
                        call.respond(HttpStatusCode.NotFound, "Foreign client not found")
                    }
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Incorrect UUID")
                }
            }
        }

        route("/api/users/admins") {
            post {
                try {
                    val admin = call.receive<Admin>()
                    adminFacade.createUser(admin)
                    call.respond(HttpStatusCode.Created, admin)
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid UUID format")
                } catch (ex: JsonConvertException) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid JSON format")
                }
            }

            get("/by-id/{adminId}") {
                val adminIdString = call.parameters["adminId"]
                if (adminIdString == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                try {
                    val adminId = UUID.fromString(adminIdString)
                    val admin = adminFacade.getUser(adminId)

                    if (admin == null) {
                        call.respond(HttpStatusCode.NotFound, "Admin not found")
                    } else {
                        call.respond(HttpStatusCode.OK, admin)
                    }
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Incorrect UUID")
                }
            }

            get("/by-email/{email}") {
                val email = call.parameters["email"]
                if (email == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                try {
                    val admin = adminFacade.getUserByEmail(email)

                    if (admin == null) {
                        call.respond(HttpStatusCode.NotFound, "Admin not found")
                    } else {
                        call.respond(HttpStatusCode.OK, admin)
                    }
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Incorrect UUID")
                }
            }

            get("/by-phone/{phone-number}") {
                val phoneNumber = call.parameters["phone-number"]
                if (phoneNumber == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                try {
                    val admin = adminFacade.getUserByPhone(phoneNumber)

                    if (admin == null) {
                        call.respond(HttpStatusCode.NotFound, "Admin not found")
                    } else {
                        call.respond(HttpStatusCode.OK, admin)
                    }
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Incorrect UUID")
                }
            }

            get("/by-bank/{bankUBN}") {
                val bankUBNString = call.parameters["bankUBN"]
                if (bankUBNString == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                try {
                    val bankUBN = UUID.fromString(bankUBNString)
                    val adminList = adminFacade.getUsersByBank(bankUBN)

                    if (adminList.isEmpty()) {
                        call.respond(HttpStatusCode.NotFound, "Admins not found")
                    } else {
                        call.respond(HttpStatusCode.OK, adminList)
                    }
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Incorrect UUID")
                }
            }

            delete("/{adminId}") {
                val adminIdString = call.parameters["adminId"]
                if (adminIdString == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@delete
                }

                try {
                    val adminId = UUID.fromString(adminIdString)
                    if (adminFacade.deleteUser(adminId)) {
                        call.respond(HttpStatusCode.NoContent, "Admin deleted successfully")
                    } else {
                        call.respond(HttpStatusCode.NotFound, "Admin not found")
                    }
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Incorrect UUID")
                }
            }
        }

        route("/api/users/managers") {
            post {
                try {
                    val manager = call.receive<Manager>()
                    managerFacade.createUser(manager)
                    call.respond(HttpStatusCode.Created, manager)
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid UUID format")
                } catch (ex: JsonConvertException) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid JSON format")
                }
            }

            get("/by-id/{managerId}") {
                val managerIdString = call.parameters["managerId"]
                if (managerIdString == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                try {
                    val managerId = UUID.fromString(managerIdString)
                    val manager = managerFacade.getUser(managerId)

                    if (manager == null) {
                        call.respond(HttpStatusCode.NotFound, "Manager not found")
                    } else {
                        call.respond(HttpStatusCode.OK, manager)
                    }
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Incorrect UUID")
                }
            }

            get("/by-email/{email}") {
                val email = call.parameters["email"]
                if (email == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                try {
                    val manager = managerFacade.getUserByEmail(email)

                    if (manager == null) {
                        call.respond(HttpStatusCode.NotFound, "Manager not found")
                    } else {
                        call.respond(HttpStatusCode.OK, manager)
                    }
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Incorrect UUID")
                }
            }

            get("/by-phone/{phone-number}") {
                val phoneNumber = call.parameters["phone-number"]
                if (phoneNumber == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                try {
                    val manager = managerFacade.getUserByPhone(phoneNumber)

                    if (manager == null) {
                        call.respond(HttpStatusCode.NotFound, "Manager not found")
                    } else {
                        call.respond(HttpStatusCode.OK, manager)
                    }
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Incorrect UUID")
                }
            }

            get("/by-bank/{bankUBN}") {
                val bankUBNString = call.parameters["bankUBN"]
                if (bankUBNString == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                try {
                    val bankUBN = UUID.fromString(bankUBNString)
                    val managerList = managerFacade.getUsersByBank(bankUBN)

                    if (managerList.isEmpty()) {
                        call.respond(HttpStatusCode.NotFound, "Managers not found")
                    } else {
                        call.respond(HttpStatusCode.OK, managerList)
                    }
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Incorrect UUID")
                }
            }

            delete("/{managerId}") {
                val managerIdString = call.parameters["managerId"]
                if (managerIdString == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@delete
                }

                try {
                    val managerId = UUID.fromString(managerIdString)
                    if (managerFacade.deleteUser(managerId)) {
                        call.respond(HttpStatusCode.NoContent, "Manager deleted successfully")
                    } else {
                        call.respond(HttpStatusCode.NotFound, "Manager not found")
                    }
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Incorrect UUID")
                }
            }
        }

        route("/api/users/operators") {
            post {
                try {
                    val operator = call.receive<Operator>()
                    operatorFacade.createUser(operator)
                    call.respond(HttpStatusCode.Created, operator)
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid UUID format")
                } catch (ex: JsonConvertException) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid JSON format")
                }
            }

            get("/by-id/{operatorId}") {
                val operatorIdString = call.parameters["operatorId"]
                if (operatorIdString == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                try {
                    val operatorId = UUID.fromString(operatorIdString)
                    val operator = operatorFacade.getUser(operatorId)

                    if (operator == null) {
                        call.respond(HttpStatusCode.NotFound, "Operator not found")
                    } else {
                        call.respond(HttpStatusCode.OK, operator)
                    }
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Incorrect UUID")
                }
            }

            get("/by-email/{email}") {
                val email = call.parameters["email"]
                if (email == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                try {
                    val operator = operatorFacade.getUserByEmail(email)

                    if (operator == null) {
                        call.respond(HttpStatusCode.NotFound, "Operator not found")
                    } else {
                        call.respond(HttpStatusCode.OK, operator)
                    }
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Incorrect UUID")
                }
            }

            get("/by-phone/{phone-number}") {
                val phoneNumber = call.parameters["phone-number"]
                if (phoneNumber == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                try {
                    val operator = operatorFacade.getUserByPhone(phoneNumber)

                    if (operator == null) {
                        call.respond(HttpStatusCode.NotFound, "Operator not found")
                    } else {
                        call.respond(HttpStatusCode.OK, operator)
                    }
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Incorrect UUID")
                }
            }

            get("/by-bank/{bankUBN}") {
                val bankUBNString = call.parameters["bankUBN"]
                if (bankUBNString == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                try {
                    val bankUBN = UUID.fromString(bankUBNString)
                    val operatorList = operatorFacade.getUsersByBank(bankUBN)

                    if (operatorList.isEmpty()) {
                        call.respond(HttpStatusCode.NotFound, "Operators not found")
                    } else {
                        call.respond(HttpStatusCode.OK, operatorList)
                    }
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Incorrect UUID")
                }
            }

            delete("/{operatorId}") {
                val operatorIdString = call.parameters["operatorId"]
                if (operatorIdString == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@delete
                }

                try {
                    val operatorId = UUID.fromString(operatorIdString)
                    if (operatorFacade.deleteUser(operatorId)) {
                        call.respond(HttpStatusCode.NoContent, "Operator deleted successfully")
                    } else {
                        call.respond(HttpStatusCode.NotFound, "Operator not found")
                    }
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Incorrect UUID")
                }
            }
        }

        route("/api/users/outside-specialists") {
            post {
                try {
                    val outsideSpecialist = call.receive<OutsideSpecialist>()
                    outsideSpecialistFacade.createUser(outsideSpecialist)
                    call.respond(HttpStatusCode.Created, outsideSpecialist)
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid UUID format")
                } catch (ex: JsonConvertException) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid JSON format")
                }
            }

            get("/by-id/{specialistId}") {
                val specialistIdString = call.parameters["specialistId"]
                if (specialistIdString == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                try {
                    val specialistId = UUID.fromString(specialistIdString)
                    val outsideSpecialist = outsideSpecialistFacade.getUser(specialistId)

                    if (outsideSpecialist == null) {
                        call.respond(HttpStatusCode.NotFound, "Outside specialist not found")
                    } else {
                        call.respond(HttpStatusCode.OK, outsideSpecialist)
                    }
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Incorrect UUID")
                }
            }

            get("/by-email/{email}") {
                val email = call.parameters["email"]
                if (email == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                try {
                    val outsideSpecialist = outsideSpecialistFacade.getUserByEmail(email)

                    if (outsideSpecialist == null) {
                        call.respond(HttpStatusCode.NotFound, "Outside specialist not found")
                    } else {
                        call.respond(HttpStatusCode.OK, outsideSpecialist)
                    }
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Incorrect UUID")
                }
            }

            get("/by-phone/{phone-number}") {
                val phoneNumber = call.parameters["phone-number"]
                if (phoneNumber == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                try {
                    val outsideSpecialist = outsideSpecialistFacade.getUserByPhone(phoneNumber)

                    if (outsideSpecialist == null) {
                        call.respond(HttpStatusCode.NotFound, "Outside specialist not found")
                    } else {
                        call.respond(HttpStatusCode.OK, outsideSpecialist)
                    }
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Incorrect UUID")
                }
            }

            get("/by-bank/{bankUBN}") {
                val bankUBNString = call.parameters["bankUBN"]
                if (bankUBNString == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                try {
                    val bankUBN = UUID.fromString(bankUBNString)
                    val outsideSpecialistList = outsideSpecialistFacade.getUsersByBank(bankUBN)

                    if (outsideSpecialistList.isEmpty()) {
                        call.respond(HttpStatusCode.NotFound, "Outside specialists not found")
                    } else {
                        call.respond(HttpStatusCode.OK, outsideSpecialistList)
                    }
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Incorrect UUID")
                }
            }

            delete("/{specialistId}") {
                val specialistIdString = call.parameters["specialistId"]
                if (specialistIdString == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@delete
                }

                try {
                    val specialistId = UUID.fromString(specialistIdString)
                    if (outsideSpecialistFacade.deleteUser(specialistId)) {
                        call.respond(HttpStatusCode.NoContent, "Outside specialist deleted successfully")
                    } else {
                        call.respond(HttpStatusCode.NotFound, "Outside specialist not found")
                    }
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Incorrect UUID")
                }
            }
        }
    }
}