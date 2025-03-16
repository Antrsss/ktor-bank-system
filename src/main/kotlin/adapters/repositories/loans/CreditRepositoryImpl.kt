package com.example.adapters.repositories

import com.example.domain.LoanObligationStatus
import com.example.domain.LoanPeriod
import com.example.domain.entities.loan_obligations.Credit
import com.example.domain.repositories.LoanRepository
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.util.*

object CreditsTable : IntIdTable("credits") {
    val ownerId = uuid("owner_id")
    val bankUBN = uuid("bank_ubn")
    val amount = double("amount")
    val period = enumerationByName("period", 50, LoanPeriod::class)
    val individualRate = double("individual_rate").nullable()
    val status = enumerationByName("status", 50, LoanObligationStatus::class).default(LoanObligationStatus.IN_PROCESS)
    val payedAmount = double("payed_amount").default(0.0)
    val loanId = uuid("loan_id").uniqueIndex()
}

fun ResultRow.toCredit(): Credit {
    return Credit(
        ownerId = this[CreditsTable.ownerId],
        bankUBN = this[CreditsTable.bankUBN],
        amount = this[CreditsTable.amount],
        period = this[CreditsTable.period],
        individualRate = this[CreditsTable.individualRate],
        status = this[CreditsTable.status],
        payedAmount = this[CreditsTable.payedAmount],
        loanId = this[CreditsTable.loanId]
    )
}

class CreditsDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<CreditsDAO>(CreditsTable)

    var ownerId by CreditsTable.ownerId
    var bankUBN by CreditsTable.bankUBN
    var amount by CreditsTable.amount
    var period by CreditsTable.period
    var individualRate by CreditsTable.individualRate
    var status by CreditsTable.status
    var payedAmount by CreditsTable.payedAmount
    var loanId by CreditsTable.loanId
}

fun daoToModel(dao: CreditsDAO) = Credit(
    dao.ownerId,
    dao.bankUBN,
    dao.amount,
    dao.period,
    dao.individualRate,
    dao.status,
    dao.payedAmount,
    dao.loanId
)

class CreditRepositoryImpl : LoanRepository<Credit> {

    init {
        Database.connect(
            url = "jdbc:postgresql://localhost:5432/bank_db",
            driver = "org.postgresql.Driver",
            user = "postgres",
            password = "625100"
        )

        transaction {
            SchemaUtils.create(CreditsTable)
        }
    }

    override suspend fun createLoan(loan: Credit): Unit = suspendTransaction {
        if (getLoan(loan.loanId) == null) {
            CreditsDAO.new {
                ownerId = loan.ownerId
                bankUBN = loan.bankUBN
                amount = loan.amount
                period = loan.period
                individualRate = loan.individualRate
                status = loan.status
                payedAmount = loan.payedAmount
                loanId = loan.loanId
            }
        }
    }

    override suspend fun getLoan(loanId: UUID): Credit? = suspendTransaction {
        CreditsDAO
            .find { CreditsTable.loanId eq loanId }
            .limit(1)
            .map(::daoToModel)
            .firstOrNull()
    }

    override suspend fun getLoansByOwner(ownerId: UUID): List<Credit> = suspendTransaction {
        CreditsDAO
            .find { CreditsTable.ownerId eq ownerId }
            .map(::daoToModel)
    }

    override suspend fun getLoansByBank(bankUBN: UUID): List<Credit> = suspendTransaction {
        CreditsDAO
            .find { CreditsTable.bankUBN eq bankUBN }
            .map(::daoToModel)
    }

    override suspend fun updateLoan(loan: Credit): Unit = suspendTransaction {
        CreditsTable.update({ CreditsTable.loanId eq loan.loanId }) {
            it[status] = loan.status
            it[payedAmount] = loan.payedAmount
        }
    }

    override suspend fun deleteLoan(loanId: UUID): Boolean = suspendTransaction {
        val rowsDeleted = CreditsTable.deleteWhere {
            CreditsTable.loanId eq loanId
        }
        rowsDeleted == 1
    }
}