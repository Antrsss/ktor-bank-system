package com.example.adapters.repositories

import com.example.domain.LoanObligationStatus
import com.example.domain.LoanPeriod
import com.example.domain.entities.loan_obligations.DeferredPayment
import com.example.domain.repositories.LoanRepository
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.util.*

object DeferredPaymentsTable : IntIdTable("deferred_payments") {
    val ownerId = uuid("owner_id")
    val bankUBN = uuid("bank_ubn")
    val amount = double("amount")
    val period = enumerationByName("period", 50, LoanPeriod::class)
    val status = enumerationByName("status", 50, LoanObligationStatus::class).default(LoanObligationStatus.IN_PROCESS)
    val payedAmount = double("payed_amount").default(0.0)
    val loanId = uuid("loan_id").uniqueIndex()
}

fun ResultRow.toDeferredPayment(): DeferredPayment {
    return DeferredPayment(
        ownerId = this[DeferredPaymentsTable.ownerId],
        bankUBN = this[DeferredPaymentsTable.bankUBN],
        amount = this[DeferredPaymentsTable.amount],
        period = this[DeferredPaymentsTable.period],
        status = this[DeferredPaymentsTable.status],
        payedAmount = this[DeferredPaymentsTable.payedAmount],
        loanId = this[DeferredPaymentsTable.loanId]
    )
}

class DeferredPaymentsDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<DeferredPaymentsDAO>(DeferredPaymentsTable)

    var ownerId by DeferredPaymentsTable.ownerId
    var bankUBN by DeferredPaymentsTable.bankUBN
    var amount by DeferredPaymentsTable.amount
    var period by DeferredPaymentsTable.period
    var status by DeferredPaymentsTable.status
    var payedAmount by DeferredPaymentsTable.payedAmount
    var loanId by DeferredPaymentsTable.loanId
}

fun daoToModel(dao: DeferredPaymentsDAO) = DeferredPayment(
    dao.ownerId,
    dao.bankUBN,
    dao.amount,
    dao.period,
    dao.status,
    dao.payedAmount,
    dao.loanId
)

class DeferredPaymentRepositoryImpl : LoanRepository<DeferredPayment> {

    init {
        Database.connect(
            url = "jdbc:postgresql://localhost:5432/bank_db",
            driver = "org.postgresql.Driver",
            user = "postgres",
            password = "625100"
        )

        transaction {
            SchemaUtils.create(DeferredPaymentsTable)
        }
    }

    override suspend fun createLoan(loan: DeferredPayment): Unit = suspendTransaction {
        if (getLoan(loan.loanId) == null) {
            DeferredPaymentsDAO.new {
                ownerId = loan.ownerId
                bankUBN = loan.bankUBN
                amount = loan.amount
                period = loan.period
                status = loan.status
                payedAmount = loan.payedAmount
                loanId = loan.loanId
            }
        }
    }

    override suspend fun getLoan(loanId: UUID): DeferredPayment? = suspendTransaction {
        DeferredPaymentsDAO
            .find { DeferredPaymentsTable.loanId eq loanId }
            .limit(1)
            .map(::daoToModel)
            .firstOrNull()
    }

    override suspend fun getLoansByOwner(ownerId: UUID): List<DeferredPayment> = suspendTransaction {
        DeferredPaymentsDAO
            .find { DeferredPaymentsTable.ownerId eq ownerId }
            .map(::daoToModel)
    }

    override suspend fun getLoansByBank(bankUBN: UUID): List<DeferredPayment> = suspendTransaction {
        DeferredPaymentsDAO
            .find { DeferredPaymentsTable.bankUBN eq bankUBN }
            .map(::daoToModel)
    }

    override suspend fun updateLoan(loan: DeferredPayment): Unit = suspendTransaction {
        DeferredPaymentsTable.update({ DeferredPaymentsTable.loanId eq loan.loanId }) {
            it[status] = loan.status
            it[payedAmount] = loan.payedAmount
        }
    }

    override suspend fun deleteLoan(loanId: UUID): Boolean = suspendTransaction {
        val rowsDeleted = DeferredPaymentsTable.deleteWhere {
            DeferredPaymentsTable.loanId eq loanId
        }
        rowsDeleted == 1
    }
}