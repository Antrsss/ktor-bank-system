package com.example.adapters.repositories

import com.example.adapters.repositories.users.ClientDAO
import com.example.adapters.repositories.users.ClientsTable
import com.example.adapters.repositories.users.ForeignClientsTable
import com.example.adapters.repositories.users.OutsideSpecialistsTable
import com.example.domain.AccountStatus
import com.example.domain.entities.Account
import com.example.domain.repositories.AccountRepository
import com.example.domain.repositories.base.CRUDRepository
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
    val balance = double("balance").default(0.0)
    val status = enumerationByName("status", 20, AccountStatus::class).default(AccountStatus.ACTIVE)
    val accountId = uuid("account_id").uniqueIndex()
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
        transaction {
            SchemaUtils.create(AccountsTable)
        }
    }

    override suspend fun create(entity: Account): Unit = suspendTransaction {
        AccountsDAO.new {
            accountName = entity.accountName
            ownerId = entity.ownerId
            bankUBN = entity.bankUBN
            balance = entity.balance
            status = entity.status
            accountId = entity.accountId
        }
    }

    override suspend fun get(id: UUID): Account? = suspendTransaction {
        AccountsDAO
            .find{ (AccountsTable.accountId eq id) }
            .limit(1)
            .map(::daoToModel)
            .firstOrNull()
    }

    override suspend fun update(entity: Account): Account = suspendTransaction {
        AccountsTable.update({ AccountsTable.accountId eq entity.accountId }) {
            it[accountName] = entity.accountName
            it[ownerId] = entity.ownerId
            it[bankUBN] = entity.bankUBN
            it[balance] = entity.balance
            it[status] = entity.status
            it[accountId] = entity.accountId
        }
        entity
    }

    override suspend fun delete(entityId: UUID): Boolean = suspendTransaction {
        val rowsDeleted = AccountsTable.deleteWhere {
            AccountsTable.accountId eq entityId
        }
        rowsDeleted == 1
    }



    override suspend fun getAccountsByOwner(ownerId: UUID): List<Account> = suspendTransaction {
        AccountsDAO
            .find{ (AccountsTable.ownerId eq ownerId) }
            .map(::daoToModel)
    }

    override suspend fun getAccountsByBank(bankUBN: UUID): List<Account> = suspendTransaction {
        AccountsDAO
            .find { (AccountsTable.bankUBN eq bankUBN) }
            .map(::daoToModel)
    }
}
