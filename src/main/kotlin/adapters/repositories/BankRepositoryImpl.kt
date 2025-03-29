package com.example.adapters.repositories

import com.example.domain.entities.Bank
import com.example.domain.repositories.BankRepository
import com.example.domain.repositories.base.CRUDRepository
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.*

object BanksTable : IntIdTable("banks") {
    val bankName = varchar("bank_name", 255)
    val bankUBN = uuid("bank_ubn").uniqueIndex()
}

fun ResultRow.toBank(): Bank {
    return Bank(
        bankName = this[BanksTable.bankName],
        bankUBN = this[BanksTable.bankUBN],
    )
}

class BanksDAO (id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<BanksDAO>(BanksTable)

    var bankName by BanksTable.bankName
    var bankUBN by BanksTable.bankUBN
}

suspend fun <T> suspendTransaction(block: suspend Transaction.() -> T): T =
    newSuspendedTransaction(Dispatchers.IO, statement = block)

fun daoToModel(dao: BanksDAO) = Bank(
    dao.bankName,
    dao.bankUBN,
)

class BankRepositoryImpl: BankRepository {

    init {
        transaction {
            SchemaUtils.create(BanksTable)
            //SchemaUtils.createMissingTablesAndColumns(Banks)
        }
    }

    override suspend fun create(entity: Bank) = suspendTransaction {
        if (get(entity.bankUBN) == null) {
            BanksDAO.new {
                bankName = entity.bankName
                bankUBN = entity.bankUBN
            }
        }
    }

    override suspend fun get(id: UUID): Bank? = suspendTransaction {
        BanksDAO
            .find { (BanksTable.bankUBN eq id) }
            .limit(1)
            .map(::daoToModel)
            .firstOrNull()
    }

    override suspend fun getAllBanks(): List<Bank> = suspendTransaction {
        BanksDAO.all().map(::daoToModel)
    }

    override suspend fun delete(entityId: UUID): Boolean = suspendTransaction {
        val rowsDeleted = BanksTable.deleteWhere {
            BanksTable.bankUBN eq entityId
        }
        rowsDeleted == 1
    }

    override suspend fun update(entity: Bank): Bank = suspendTransaction {
        BanksTable.update({ BanksTable.bankUBN eq entity.bankUBN }) {
            it[bankName] = entity.bankName
        }
        entity
    }
}