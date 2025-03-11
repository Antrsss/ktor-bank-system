package com.example.adapters.repositories

import com.example.domain.AccountStatus
import com.example.domain.entities.Account
import com.example.domain.repositories.AccountRepository
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

object AccountsTable : IntIdTable("accounts") {
    val accountName = varchar("account_name", 255)
    val ownerId = uuid("owner_id")
    val bankUBN = uuid("bank_ubn")
    val balance = double("balance")
    val status = enumerationByName("status", 20, AccountStatus::class)
    val accountId = uuid("account_id")
}

fun ResultRow.toAccount(): Account {
    return Account(
        accountName = this[AccountsTable.accountName],
        ownerId = this[AccountsTable.ownerId],
        bankUBN = this[AccountsTable.bankUBN],
        balance = this[AccountsTable.balance],
        status = this[AccountsTable.status],
        accountId = this[AccountsTable.accountId]
    )
}

class AccountsDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<AccountsDAO>(AccountsTable)

    var accountName by AccountsTable.accountName
    var ownerId by AccountsTable.ownerId
    var bankUBN by AccountsTable.bankUBN
    var balance by AccountsTable.balance
    var status by AccountsTable.status
    var accountId by AccountsTable.accountId
}

fun daoToModel(dao: AccountsDAO) = Account(
    dao.accountName,
    dao.ownerId,
    dao.bankUBN,
    dao.balance,
    dao.status,
    dao.accountId
)

class AccountRepositoryImpl: AccountRepository {

    init {
        Database.connect(
            url = "jdbc:postgresql://localhost:5432/bank_db",
            driver = "org.postgresql.Driver",
            user = "postgres",
            password = "625100"
        )

        println("Account table created")
        transaction {
            SchemaUtils.create(AccountsTable)
        }
    }

    override suspend fun createAccount(account: Account): Unit = suspendTransaction {
        AccountsDAO.new {
            account.accountName
            account.ownerId
            account.bankUBN
            account.balance
            account.status
            account.accountId
        }
    }

    override suspend fun getAccountById(accountId: UUID): Account? = suspendTransaction {
        AccountsDAO
            .find{ (AccountsTable.accountId eq accountId) }
            .limit(1)
            .map(::daoToModel)
            .firstOrNull()
    }

    override suspend fun getAccountsByOwnerId(ownerId: UUID): List<Account> = suspendTransaction {
        AccountsDAO
            .find{ (AccountsTable.ownerId eq ownerId) }
            .map(::daoToModel)
    }

    override suspend fun getAccountsByBankUBN(bankUBN: UUID): List<Account> = suspendTransaction {
        AccountsDAO
            .find { (AccountsTable.bankUBN eq bankUBN) }
            .map(::daoToModel)
    }

    override suspend fun getAllAccounts(): List<Account> = suspendTransaction {
        AccountsDAO.all().map(::daoToModel)
    }

    override suspend fun updateAccount(account: Account): Unit = suspendTransaction {
        AccountsTable.update({ AccountsTable.accountId eq account.accountId }) {
            it[accountName] = account.accountName
            it[balance] = account.balance
            it[status] = account.status
        }
    }

    override suspend fun deleteAccount(accountId: UUID): Boolean = suspendTransaction {
        val rowsDeleted = AccountsTable.deleteWhere {
            AccountsTable.accountId eq accountId
        }
        rowsDeleted == 1
    }
}
