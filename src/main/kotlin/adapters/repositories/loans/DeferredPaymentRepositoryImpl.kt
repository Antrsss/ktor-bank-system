package com.example.adapters.repositories.loans

import com.example.adapters.repositories.suspendTransaction
import com.example.domain.LoanStatus
import com.example.domain.LoanPeriod
import com.example.domain.entities.loan_obligations.DeferredPayment
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

object DeferredPaymentsTable : IntIdTable("deferred_payments") {
    val ownerId = uuid("owner_id")
    val bankUBN = uuid("bank_ubn")
    val amount = double("amount")
    val period = enumerationByName("period", 50, LoanPeriod::class)
    val status = enumerationByName("status", 50, LoanStatus::class).default(LoanStatus.IN_PROCESS)
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

class DeferredPaymentRepositoryImpl: LoanRepository<DeferredPayment> {

    init {
        transaction {
            SchemaUtils.create(DeferredPaymentsTable)
        }
    }

    override suspend fun create(entity: DeferredPayment) = suspendTransaction {
        if (get(entity.loanId) == null) {
            DeferredPaymentsDAO.new {
                ownerId = entity.ownerId
                bankUBN = entity.bankUBN
                amount = entity.amount
                period = entity.period
                status = entity.status
                payedAmount = entity.payedAmount
                loanId = entity.loanId
            }
        }
    }

    override suspend fun get(id: UUID): DeferredPayment? = suspendTransaction {
        DeferredPaymentsDAO
            .find { DeferredPaymentsTable.loanId eq id }
            .limit(1)
            .map(::daoToModel)
            .firstOrNull()
    }

    override suspend fun delete(entityId: UUID): Boolean = suspendTransaction {
        val rowsDeleted = DeferredPaymentsTable.deleteWhere {
            DeferredPaymentsTable.loanId eq entityId
        }
        rowsDeleted == 1
    }

    override suspend fun update(entity: DeferredPayment): DeferredPayment = suspendTransaction {
        DeferredPaymentsTable.update({ DeferredPaymentsTable.loanId eq entity.loanId }) {
            it[ownerId] = entity.ownerId
            it[bankUBN] = entity.bankUBN
            it[amount] = entity.amount
            it[period] = entity.period
            it[status] = entity.status
            it[payedAmount] = entity.payedAmount
            it[loanId] = entity.loanId
        }
        entity
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
}