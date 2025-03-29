package com.example.adapters.controllers.common

import com.example.json
import com.example.serializersModule
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.serialization.serializer
import java.util.*

suspend inline fun <reified T : Any> ApplicationCall.handlePostRequest(createUser: suspend (T) -> Unit) {
    try {
        val entity = receive<T>()
        createUser(entity)
        println(entity)
        json.encodeToString(entity)
        respond(HttpStatusCode.Created, entity)
    } catch (ex: IllegalArgumentException) {
        respond(HttpStatusCode.BadRequest, "Invalid UUID format")
    } catch (ex: JsonConvertException) {
        respond(HttpStatusCode.BadRequest, "Invalid JSON format")
    }
}

suspend inline fun <reified T : Any> ApplicationCall.handleGetRequest(getEntity: suspend (UUID) -> T?) {
    val idString = parameters["id"]
    println("idString: $idString")
    if (idString == null) {
        respond(HttpStatusCode.BadRequest)
        return
    }

    try {
        val id = UUID.fromString(idString)
        val entity = getEntity(id)
        println("entity: $entity")
        if (entity == null) {
            respond(HttpStatusCode.NotFound, "Entity not found")
        } else {
            respond(HttpStatusCode.OK, entity)
        }
    } catch (ex: IllegalArgumentException) {
        respond(HttpStatusCode.BadRequest, "Incorrect UUID")
    }
}

suspend inline fun <reified T : Any> ApplicationCall.handleGetEntityByStringRequest(getEntity: suspend (String) -> T?) {
    val idString = parameters["id"]
    if (idString == null) {
        respond(HttpStatusCode.BadRequest)
        return
    }

    try {
        val entity = getEntity(idString)
        if (entity == null) {
            respond(HttpStatusCode.NotFound, "Entity not found")
        } else {
            respond(HttpStatusCode.OK, entity)
        }
    } catch (ex: IllegalArgumentException) {
        print(idString)
        respond(HttpStatusCode.BadRequest, "Incorrect UUID")
    }
}

suspend inline fun <reified T : Any> ApplicationCall.handleGetEntitiesByRequest(getEntityBy: suspend (UUID) -> List<T>) {
    val idString = parameters["id"]
    if (idString == null) {
        respond(HttpStatusCode.BadRequest)
        return
    }

    try {
        val id = UUID.fromString(idString)
        val entityList = getEntityBy(id)
        if (entityList.isEmpty()) {
            respond(HttpStatusCode.NotFound, "Entities by that UUID not found")
        } else {
            respond(HttpStatusCode.OK, entityList)
        }
    } catch (ex: IllegalArgumentException) {
        respond(HttpStatusCode.BadRequest, "Incorrect UUID")
    }
}

suspend inline fun <reified T : Any> ApplicationCall.handleGetAllRequest(getAllEntities: suspend () -> List<T>) {
    val entityList = getAllEntities()
    respond(HttpStatusCode.OK, entityList)
}

suspend inline fun <reified T : Any, K : Any> ApplicationCall.handlePutRequest(
    updateEntity: suspend (UUID, K) -> T?,
    updatedField: K
) {
    val idString = parameters["id"]
    if (idString == null) {
        respond(HttpStatusCode.BadRequest)
        return
    }

    try {
        val id = UUID.fromString(idString)
        val result = updateEntity(id, updatedField)
            ?: respond(HttpStatusCode.BadRequest, "Entity with that UUID does not exist")
        respond(HttpStatusCode.OK, result)
    } catch (ex: IllegalArgumentException) {
        respond(HttpStatusCode.BadRequest, "Incorrect UUID")
    }
}

suspend inline fun ApplicationCall.handleDeleteRequest(deleteEntity: suspend (UUID) -> Boolean) {
    val idString = parameters["id"]
    if (idString == null) {
        respond(HttpStatusCode.BadRequest)
        return
    }

    try {
        val id = UUID.fromString(idString)
        if (deleteEntity(id)) {
            respond(HttpStatusCode.NoContent, "Entity deleted successfully")
        } else {
            respond(HttpStatusCode.NotFound, "Entity not found")
        }
    } catch (ex: IllegalArgumentException) {
        respond(HttpStatusCode.BadRequest, "Incorrect UUID")
    }
}