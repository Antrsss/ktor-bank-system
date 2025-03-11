package com.example.adapters.repositories

import com.example.domain.entities.Bank
import com.example.domain.repositories.BankRepository
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
    val bankUBN: Column<UUID> = uuid("bank_ubn")
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

class BankRepositoryImpl : BankRepository {

    init {
        Database.connect(
            url = "jdbc:postgresql://localhost:5432/bank_db",
            driver = "org.postgresql.Driver",
            user = "postgres",
            password = "625100"
        )

        transaction {
            SchemaUtils.create(BanksTable)
            //SchemaUtils.createMissingTablesAndColumns(Banks)
        }
    }

    override suspend fun createBank(bank: Bank): Unit = suspendTransaction {
        if (getBankByName(bank.bankName) == null) {
            BanksDAO.new {
                bankName = bank.bankName
                bankUBN = bank.bankUBN
            }
        }
    }

    override suspend fun getBankByUBN(bankUBN: UUID): Bank? = suspendTransaction {
        BanksDAO
            .find { (BanksTable.bankUBN eq bankUBN) }
            .limit(1)
            .map(::daoToModel)
            .firstOrNull()
    }

    override suspend fun getBankByName(bankName: String): Bank? = suspendTransaction {
        BanksDAO
            .find { (BanksTable.bankName eq bankName) }
            .limit(1)
            .map(::daoToModel)
            .firstOrNull()
    }

    override suspend fun getAllBanks(): List<Bank> = suspendTransaction {
        BanksDAO.all().map(::daoToModel)
    }

    override suspend fun updateBank(bank: Bank): Unit = suspendTransaction {
        BanksTable.update({ BanksTable.bankUBN eq bank.bankUBN }) {
            it[bankName] = bank.bankName
        }
    }

    override suspend fun deleteBank(bankUBN: UUID): Boolean = suspendTransaction {
        val rowsDeleted = BanksTable.deleteWhere {
            BanksTable.bankUBN eq bankUBN
        }
        rowsDeleted == 1
    }
}