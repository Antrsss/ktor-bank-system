package com.example

import com.example.domain.LoanPeriod
import com.example.domain.LoanStatus
import com.example.domain.entities.loan_obligations.DeferredPayment
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import java.sql.Connection
import java.sql.DriverManager
import org.jetbrains.exposed.sql.*
import java.util.*

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            ignoreUnknownKeys = true
            isLenient = true
            serializersModule = com.example.serializersModule
        })
    }
}
