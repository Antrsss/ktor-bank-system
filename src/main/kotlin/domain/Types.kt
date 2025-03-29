package com.example.domain

import com.example.domain.entities.users.Client
import kotlinx.serialization.Serializable

@Serializable
enum class Role {
    ADMIN, CLIENT, FOREIGN_CLIENT, MANAGER, OPERATOR, OUTSIDE_SPECIALIST
}

@Serializable
enum class ClientCountry {
    RUSSIA, UKRAINE, POLAND, LATVIA
}

@Serializable
enum class RequestStatus {
    PENDING, CANCELLED, APPROVED
}


@Serializable
enum class LoanStatus {
    IN_PROCESS,
    PAYED
}

/*@Serializable(with = LoanPeriodSerializer::class)
enum class LoanPeriod(val monthCount: Int, val fixedRate: Double?) {
    @SerialName("MONTH_3") MONTH_3(3, 2.5),
    @SerialName("MONTH_6") MONTH_6(6, 5.0),
    @SerialName("MONTH_12") MONTH_12(12, 7.5),
    @SerialName("MONTH_24") MONTH_24(24, 10.0),
    @SerialName("ABOVE_24_MONTH") ABOVE_24_MONTH(0, null)
}*/

@Serializable
enum class LoanPeriod {
    MONTH_3,
    MONTH_6,
    MONTH_12,
    MONTH_24,
    ABOVE_24_MONTH
}

@Serializable
enum class LoanType {
    CREDIT,
    DEFERRED_PAYMENT
}

@Serializable
enum class AccountStatus {
    ACTIVE,
    BLOCKED,
    FROZEN,
    CLOSED,
}

@Serializable
enum class SalaryProjectStatus {
    ACTIVE,
    BLOCKED,
    FROZEN,
    CLOSED,
}

@Serializable
enum class EnterpriseType {
    IP,
    OOO,
    ZAO
}

@Serializable
enum class TransactionType {
    DEPOSIT,
    WITHDRAWAL,
    TRANSFER
}


data class ActionLog(
    val client: Client,
    val action: String,
)