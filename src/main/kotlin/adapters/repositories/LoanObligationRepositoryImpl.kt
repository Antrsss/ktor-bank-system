package com.example.adapters.repositories

import com.example.domain.LoanObligationStatus
import com.example.domain.LoanPeriod
import com.example.domain.LoanType
import com.example.domain.abstracts.LoanObligation
import com.example.domain.entities.loan_obligations.Credit
import com.example.domain.entities.loan_obligations.DeferredPayment
import com.example.domain.repositories.LoanObligationRepository
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

object LoanObligationsTable : IntIdTable("loan_obligations") {
    val ownerId = uuid("owner_id")
    val bankUBN = uuid("bank_ubn")
    val amount = double("amount")
    val period = enumerationByName("period", 50, LoanPeriod::class) // Можно хранить в месяцах
    val individualRate = double("individual_rate").nullable()
    val type = enumerationByName("type", 50, LoanType::class) // "Credit" или "DeferredPayment"
    val status = enumerationByName("status", 20, LoanObligationStatus::class)
    val payedAmount = double("payed_amount")
    val loanId = uuid("loan_id").uniqueIndex()
}

class LoanObligationDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<LoanObligationDAO>(LoanObligationsTable)

    var ownerId by LoanObligationsTable.ownerId
    var bankUBN by LoanObligationsTable.bankUBN
    var amount by LoanObligationsTable.amount
    var loanPeriod by LoanObligationsTable.period
    var individualRate by LoanObligationsTable.individualRate
    var type by LoanObligationsTable.type
    var status by LoanObligationsTable.status
    var payedAmount by LoanObligationsTable.payedAmount
    var loanId by LoanObligationsTable.loanId
}

fun daoToModel(dao: LoanObligationDAO): LoanObligation {
    return when (dao.type) {
        LoanType.CREDIT -> Credit(
            ownerId = dao.ownerId,
            bankUBN = dao.bankUBN,
            amount = dao.amount,
            period = dao.loanPeriod,
            individualRate = dao.individualRate,
            status = dao.status,
            payedAmount = dao.payedAmount,
            loanId = dao.loanId
        )
        LoanType.DEFERRED_PAYMENT -> DeferredPayment(
            ownerId = dao.ownerId,
            bankUBN = dao.bankUBN,
            amount = dao.amount,
            period = dao.loanPeriod,
            status = dao.status,
            payedAmount = dao.payedAmount,
            loanId = dao.loanId
        )
        else -> throw IllegalArgumentException("Unknown loan type: ${dao.type}")
    }
}

private fun ResultRow.toLoanObligation(): LoanObligation {
    return when (this[LoanObligationsTable.type]) {
        LoanType.CREDIT -> Credit(
            ownerId = this[LoanObligationsTable.ownerId],
            bankUBN = this[LoanObligationsTable.bankUBN],
            amount = this[LoanObligationsTable.amount],
            period = this[LoanObligationsTable.period],
            individualRate = this[LoanObligationsTable.individualRate],
            status = this[LoanObligationsTable.status],
            payedAmount = this[LoanObligationsTable.payedAmount],
            loanId = this[LoanObligationsTable.loanId]
        )
        LoanType.DEFERRED_PAYMENT -> DeferredPayment(
            ownerId = this[LoanObligationsTable.ownerId],
            bankUBN = this[LoanObligationsTable.bankUBN],
            amount = this[LoanObligationsTable.amount],
            period = this[LoanObligationsTable.period],
            status = this[LoanObligationsTable.status],
            payedAmount = this[LoanObligationsTable.payedAmount],
            loanId = this[LoanObligationsTable.loanId]
        )
        else -> throw IllegalArgumentException("Unknown loan type")
    }
}


class LoanObligationRepositoryImpl : LoanObligationRepository {

    init {
        Database.connect(
            url = "jdbc:postgresql://localhost:5432/bank_db",
            driver = "org.postgresql.Driver",
            user = "postgres",
            password = "625100"
        )

        transaction {
            SchemaUtils.create(LoanObligationsTable)
        }
    }

    override suspend fun createLoan(loan: LoanObligation): Unit = suspendTransaction {
        LoanObligationsTable.insert {
            it[ownerId] = loan.ownerId
            it[bankUBN] = loan.bankUBN
            it[amount] = loan.amount
            it[period] = loan.period
            it[individualRate] = loan.individualRate
            it[type] = loan.loanType
            it[status] = loan.status
            it[payedAmount] = loan.payedAmount
            it[loanId] = loan.loanId
        }
    }

    override suspend fun getLoanById(loanId: UUID): LoanObligation? = suspendTransaction {
        LoanObligationDAO
            .find { (LoanObligationsTable.loanId eq loanId) }
            .limit(1)
            .map(::daoToModel)
            .firstOrNull()
    }

    override suspend fun getLoansByOwner(loanId: UUID): List<LoanObligation> = suspendTransaction {
        LoanObligationDAO
            .find { (LoanObligationsTable.loanId eq loanId) }
            .map(::daoToModel)
    }

    override suspend fun getLoansByBank(bankUBN: UUID): List<LoanObligation> = suspendTransaction {
        LoanObligationDAO
            .find { (LoanObligationsTable.bankUBN eq bankUBN) }
            .map(::daoToModel)
    }

    override suspend fun updateLoan(loan: LoanObligation): Unit = suspendTransaction {
        LoanObligationsTable.update({ LoanObligationsTable.loanId eq loan.loanId }) {
            it[status] = loan.status
            it[payedAmount] = loan.payedAmount
        }
    }

    override suspend fun deleteLoan(loanId: UUID): Boolean = suspendTransaction {
        val rowsDeleted = LoanObligationsTable.deleteWhere {
            LoanObligationsTable.loanId eq loanId
        }
        rowsDeleted == 1
    }
}