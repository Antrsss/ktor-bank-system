package com.example.domain

import com.example.domain.entities.users.Client


enum class Role {
    ADMIN, CLIENT, MANAGER, OPERATOR, OUTSIDE_SPECIALIST
}


enum class RequestStatus {
    PENDING, CANCELLED, APPROVED
}


enum class LoanObligationStatus {
    IN_PROCESS,
    PAYED
}

enum class LoanPeriod(var monthCount: Int, val fixedRate: Double?) {
    MONTH_3(3, 2.5),
    MONTH_6(6, 5.0),
    MONTH_12(12, 7.5),
    MONTH_24(24, 10.0),
    ABOVE_24_MONTH(0, null)
}

enum class LoanType {
    CREDIT,
    DEFERRED_PAYMENT
}

enum class AccountStatus {
    ACTIVE,
    BLOCKED,
    FROZEN,
    CLOSED,
}

enum class SalaryProjectStatus {
    ACTIVE,
    BLOCKED,
    FROZEN,
    CLOSED,
}


enum class EnterpriseType {
    IP,
    OOO,
    ZAO
}


enum class TransactionType {
    DEPOSIT,
    WITHDRAWAL,
    TRANSFER
}


data class ActionLog(
    val client: Client,
    val action: String,
)