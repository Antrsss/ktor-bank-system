package com.example.adapters.repositories.users

import com.example.adapters.repositories.suspendTransaction
import com.example.domain.Role
import com.example.domain.abstracts.User
import com.example.domain.entities.users.Admin
import com.example.domain.entities.users.Manager
import com.example.domain.entities.users.Operator
import com.example.domain.repositories.base.CRUDRepository
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ResultRow
import java.util.*
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import com.example.domain.repositories.common.UsersRepository
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction

object EmployeeTable : IntIdTable("employee") {
    val FIO = varchar("fio", 255)
    val phone = varchar("phone", 20)
    val email = varchar("email", 255)
    val password = varchar("password", 255)
    val bankUBN = uuid("bank_ubn")
    val userId = uuid("user_id").uniqueIndex()
    val role = enumerationByName("role", 50, Role::class)
}

fun ResultRow.toEmployee(): User {
    return when (this[EmployeeTable.role]) {
        Role.MANAGER -> Manager(
            FIO = this[EmployeeTable.FIO],
            phone = this[EmployeeTable.phone],
            email = this[EmployeeTable.email],
            password = this[EmployeeTable.password],
            bankUBN = this[EmployeeTable.bankUBN],
            userId = this[EmployeeTable.userId]
        )
        Role.OPERATOR -> Operator(
            FIO = this[EmployeeTable.FIO],
            phone = this[EmployeeTable.phone],
            email = this[EmployeeTable.email],
            password = this[EmployeeTable.password],
            bankUBN = this[EmployeeTable.bankUBN],
            userId = this[EmployeeTable.userId]
        )
        Role.ADMIN -> Admin(
            FIO = this[EmployeeTable.FIO],
            phone = this[EmployeeTable.phone],
            email = this[EmployeeTable.email],
            password = this[EmployeeTable.password],
            bankUBN = this[EmployeeTable.bankUBN],
            userId = this[EmployeeTable.userId]
        )
        else -> throw IllegalArgumentException("Unknown role")
    }
}

class EmployeeDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<EmployeeDAO>(EmployeeTable)

    var FIO by EmployeeTable.FIO
    var phone by EmployeeTable.phone
    var email by EmployeeTable.email
    var password by EmployeeTable.password
    var bankUBN by EmployeeTable.bankUBN
    var userId by EmployeeTable.userId
    var role by EmployeeTable.role
}

fun daoToModel(dao: EmployeeDAO): User {
    return when (dao.role) {
        Role.MANAGER -> Manager(
            FIO = dao.FIO,
            phone = dao.phone,
            email = dao.email,
            password = dao.password,
            bankUBN = dao.bankUBN,
            userId = dao.userId
        )
        Role.OPERATOR -> Operator(
            FIO = dao.FIO,
            phone = dao.phone,
            email = dao.email,
            password = dao.password,
            bankUBN = dao.bankUBN,
            userId = dao.userId
        )
        Role.ADMIN -> Admin(
            FIO = dao.FIO,
            phone = dao.phone,
            email = dao.email,
            password = dao.password,
            bankUBN = dao.bankUBN,
            userId = dao.userId
        )
        else -> throw IllegalArgumentException("Unknown role")
    }
}

class EmployeeRepositoryImpl: UsersRepository<User> {

    init {
        transaction {
            SchemaUtils.create(EmployeeTable)
        }
    }

    override suspend fun create(entity: User) = suspendTransaction {
        if (entity is User && get(entity.userId) == null) {
            EmployeeDAO.new {
                FIO = entity.FIO
                phone = entity.phone
                email = entity.email
                password = entity.password
                bankUBN = entity.bankUBN
                userId = entity.userId
                role = entity.role
            }.let { daoToModel(it) }
        }
    }

    override suspend fun get(id: UUID): User? = suspendTransaction {
        EmployeeDAO
            .find { EmployeeTable.userId eq id }
            .limit(1)
            .map(::daoToModel)
            .firstOrNull()
    }

    override suspend fun delete(entityId: UUID): Boolean = suspendTransaction {
        val rowsDeleted = EmployeeTable.deleteWhere {
            EmployeeTable.userId eq entityId
        }
        rowsDeleted == 1
    }

    override suspend fun update(entity: User): User = suspendTransaction {
        EmployeeDAO
            .find { EmployeeTable.userId eq entity.userId }
            .limit(1)
            .firstOrNull()
            ?.apply {
                FIO = entity.FIO
                phone = entity.phone
                email = entity.email
                password = entity.password
                bankUBN = entity.bankUBN
                role = entity.role
            }
        entity
    }

    override suspend fun getUserByEmail(email: String): User? = suspendTransaction {
        EmployeeDAO
            .find { EmployeeTable.email eq email }
            .limit(1)
            .map(::daoToModel)
            .firstOrNull()
    }

    override suspend fun getUsersByBank(bankUBN: UUID): List<User> = suspendTransaction {
        EmployeeDAO
            .find { EmployeeTable.bankUBN eq bankUBN }
            .map(::daoToModel)
    }
}