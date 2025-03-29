package com.example.adapters.repositories.users

import com.example.adapters.repositories.suspendTransaction
import com.example.domain.Role
import com.example.domain.entities.users.OutsideSpecialist
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

object OutsideSpecialistsTable : IntIdTable("outside_specialists") {
    val FIO = varchar("fio", 255)
    val enterpriseId = uuid("enterprise_id")
    val phone = varchar("phone", 20)
    val email = varchar("email", 255)
    val password = varchar("password", 255)
    val bankUBN = uuid("bank_ubn")
    val userId = uuid("user_id").uniqueIndex()
    val role = enumerationByName("role", 50, Role::class).default(Role.OUTSIDE_SPECIALIST)
}

fun ResultRow.toOutsideSpecialist(): OutsideSpecialist {
    return OutsideSpecialist(
        FIO = this[OutsideSpecialistsTable.FIO],
        enterpriseId = this[OutsideSpecialistsTable.enterpriseId],
        phone = this[OutsideSpecialistsTable.phone],
        email = this[OutsideSpecialistsTable.email],
        password = this[OutsideSpecialistsTable.password],
        bankUBN = this[OutsideSpecialistsTable.bankUBN],
        userId = this[OutsideSpecialistsTable.userId]
    )
}

class OutsideSpecialistDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<OutsideSpecialistDAO>(OutsideSpecialistsTable)

    var FIO by OutsideSpecialistsTable.FIO
    var enterpriseId by OutsideSpecialistsTable.enterpriseId
    var phone by OutsideSpecialistsTable.phone
    var email by OutsideSpecialistsTable.email
    var password by OutsideSpecialistsTable.password
    var bankUBN by OutsideSpecialistsTable.bankUBN
    var userId by OutsideSpecialistsTable.userId
    var role by OutsideSpecialistsTable.role
}

fun daoToModel(dao: OutsideSpecialistDAO) = OutsideSpecialist(
    FIO = dao.FIO,
    enterpriseId = dao.enterpriseId,
    phone = dao.phone,
    email = dao.email,
    password = dao.password,
    bankUBN = dao.bankUBN,
    userId = dao.userId
)

class OutsideSpecialistRepositoryImpl : CRUDRepository<OutsideSpecialist>, UsersRepository<OutsideSpecialist> {

    init {
        transaction {
            SchemaUtils.create(OutsideSpecialistsTable)
        }
    }

    override suspend fun create(entity: OutsideSpecialist) = suspendTransaction {
        if (get(entity.userId) == null) {
            OutsideSpecialistDAO.new {
                FIO = entity.FIO
                enterpriseId = entity.enterpriseId
                phone = entity.phone
                email = entity.email
                password = entity.password
                bankUBN = entity.bankUBN
                userId = entity.userId
                role = Role.OUTSIDE_SPECIALIST
            }.let { daoToModel(it) }
        }
    }

    override suspend fun get(id: UUID): OutsideSpecialist? = suspendTransaction {
        OutsideSpecialistDAO
            .find { OutsideSpecialistsTable.userId eq id }
            .limit(1)
            .map(::daoToModel)
            .firstOrNull()
    }

    override suspend fun delete(entityId: UUID): Boolean = suspendTransaction {
        val rowsDeleted = OutsideSpecialistsTable.deleteWhere {
            OutsideSpecialistsTable.userId eq entityId
        }
        rowsDeleted == 1
    }

    override suspend fun update(entity: OutsideSpecialist): OutsideSpecialist = suspendTransaction {
        OutsideSpecialistDAO
            .find { OutsideSpecialistsTable.userId eq entity.userId }
            .limit(1)
            .firstOrNull()
            ?.apply {
                FIO = entity.FIO
                enterpriseId = entity.enterpriseId
                phone = entity.phone
                email = entity.email
                password = entity.password
                bankUBN = entity.bankUBN
            }
        entity
    }

    override suspend fun getUserByEmail(email: String): OutsideSpecialist? = suspendTransaction {
        OutsideSpecialistDAO
            .find { OutsideSpecialistsTable.email eq email }
            .limit(1)
            .map(::daoToModel)
            .firstOrNull()
    }

    override suspend fun getUsersByBank(bankUBN: UUID): List<OutsideSpecialist> = suspendTransaction {
        OutsideSpecialistDAO
            .find { OutsideSpecialistsTable.bankUBN eq bankUBN }
            .map(::daoToModel)
    }
}

