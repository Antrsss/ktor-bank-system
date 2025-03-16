package com.example.adapters.repositories.users

import com.example.adapters.repositories.suspendTransaction
import com.example.domain.Role
import com.example.domain.entities.users.Admin
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ResultRow
import java.util.*
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

object AdminsTable : IntIdTable("admins") {
    val FIO = varchar("fio", 255)
    val phone = varchar("phone", 20)
    val email = varchar("email", 255)
    val password = varchar("password", 255)
    val bankUBN = uuid("bank_ubn")
    val userId = uuid("user_id").uniqueIndex()
    val role = enumerationByName("role", 50, Role::class).default(Role.ADMIN)
}

fun ResultRow.toAdmin(): Admin {
    return Admin(
        FIO = this[AdminsTable.FIO],
        phone = this[AdminsTable.phone],
        email = this[AdminsTable.email],
        password = this[AdminsTable.password],
        bankUBN = this[AdminsTable.bankUBN],
        userId = this[AdminsTable.userId]
    )
}

class AdminDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<AdminDAO>(AdminsTable)

    var FIO by AdminsTable.FIO
    var phone by AdminsTable.phone
    var email by AdminsTable.email
    var password by AdminsTable.password
    var bankUBN by AdminsTable.bankUBN
    var userId by AdminsTable.userId
    var role by AdminsTable.role
}

fun daoToModel(dao: AdminDAO) = Admin(
    FIO = dao.FIO,
    phone = dao.phone,
    email = dao.email,
    password = dao.password,
    bankUBN = dao.bankUBN,
    userId = dao.userId
)

import com.example.domain.repositories.common.UserRepository
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction

class AdminRepositoryImpl : UserRepository<Admin> {

    init {
        Database.connect(
            url = "jdbc:postgresql://localhost:5432/bank_db",
            driver = "org.postgresql.Driver",
            user = "postgres",
            password = "625100"
        )

        transaction {
            SchemaUtils.create(AdminsTable)
        }
    }

    override suspend fun createUser(user: Admin): Admin? = suspendTransaction {
        if (getUser(user.userId) == null) {
            AdminDAO.new {
                FIO = user.FIO
                phone = user.phone
                email = user.email
                password = user.password
                bankUBN = user.bankUBN
                userId = user.userId
                role = Role.ADMIN
            }.let { daoToModel(it) }
        } else {
            null
        }
    }

    override suspend fun getUser(userId: UUID): Admin? = suspendTransaction {
        AdminDAO
            .find { AdminsTable.userId eq userId }
            .limit(1)
            .map(::daoToModel)
            .firstOrNull()
    }

    override suspend fun getUserByEmail(email: String): Admin? = suspendTransaction {
        AdminDAO
            .find { AdminsTable.email eq email }
            .limit(1)
            .map(::daoToModel)
            .firstOrNull()
    }

    override suspend fun getUserByPhone(phone: String): Admin? = suspendTransaction {
        AdminDAO
            .find { AdminsTable.phone eq phone }
            .limit(1)
            .map(::daoToModel)
            .firstOrNull()
    }

    override suspend fun getUsersByBank(bankUBN: UUID): List<Admin> = suspendTransaction {
        AdminDAO
            .find { AdminsTable.bankUBN eq bankUBN }
            .map(::daoToModel)
    }

    override suspend fun updateUser(user: Admin): Unit = suspendTransaction {
        AdminDAO
            .find { AdminsTable.userId eq user.userId }
            .limit(1)
            .firstOrNull()
            ?.apply {
                FIO = user.FIO
                phone = user.phone
                email = user.email
                password = user.password
                bankUBN = user.bankUBN
            }
    }

    override suspend fun deleteUser(userId: UUID): Boolean = suspendTransaction {
        val rowsDeleted = AdminsTable.deleteWhere {
            AdminsTable.userId eq userId
        }
        rowsDeleted == 1
    }
}