package com.example.adapters.repositories.loans

import com.example.adapters.repositories.suspendTransaction
import com.example.domain.LoanStatus
import com.example.domain.LoanPeriod
import com.example.domain.entities.loan_obligations.Credit
import com.example.domain.repositories.base.CRUDRepository
import com.example.domain.repositories.common.LoanRepository
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
    val status = enumerationByName("status", 50, LoanStatus::class).default(LoanStatus.IN_PROCESS)
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

class CreditRepositoryImpl: LoanRepository<Credit> {

    init {
        transaction {
            SchemaUtils.create(CreditsTable)
        }
    }

    override suspend fun create(entity: Credit) = suspendTransaction {
        if (get(entity.loanId) == null) {
            CreditsDAO.new {
                ownerId = entity.ownerId
                bankUBN = entity.bankUBN
                amount = entity.amount
                period = entity.period
                individualRate = entity.individualRate
                status = entity.status
                payedAmount = entity.payedAmount
                loanId = entity.loanId
            }
        }
    }

    override suspend fun get(id: UUID): Credit? = suspendTransaction {
        CreditsDAO
            .find { CreditsTable.loanId eq id }
            .limit(1)
            .map(::daoToModel)
            .firstOrNull()
    }

    override suspend fun delete(entityId: UUID): Boolean = suspendTransaction {
        val rowsDeleted = CreditsTable.deleteWhere {
            CreditsTable.loanId eq entityId
        }
        rowsDeleted == 1
    }

    override suspend fun update(entity: Credit): Credit = suspendTransaction {
        CreditsTable.update({ CreditsTable.loanId eq entity.loanId }) {
            it[ownerId] = entity.ownerId
            it[bankUBN] = entity.bankUBN
            it[amount] = entity.amount
            it[period] = entity.period
            it[individualRate] = entity.individualRate
            it[status] = entity.status
            it[payedAmount] = entity.payedAmount
            it[loanId] = entity.loanId
        }
        entity
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
}