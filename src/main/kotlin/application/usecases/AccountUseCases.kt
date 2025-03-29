package com.example.application.usecases

import com.example.application.usecases.base.CreateUseCase
import com.example.application.usecases.base.DeleteUseCase
import com.example.application.usecases.base.GetUseCase
import com.example.domain.AccountStatus
import com.example.domain.entities.Account
import com.example.domain.repositories.AccountRepository
import com.example.domain.repositories.base.CRUDRepository
import java.util.*

class CreateAccountUseCase(
    crudRepository: CRUDRepository<Account>,
) : CreateUseCase<Account>(crudRepository)

class GetAccountUseCase(
    crudRepository: CRUDRepository<Account>,
) : GetUseCase<Account>(crudRepository)

class UpdateAccountUseCase(
    private val crudRepository: CRUDRepository<Account>
) {
    suspend fun execute(accountId: UUID, accountName: String): Account? {
        val account = crudRepository.get(accountId)
            ?: return null

        val updatedAccount = account.copy(accountName = accountName)
        return crudRepository.update(updatedAccount)
    }

    suspend fun execute(accountId: UUID, balance: Double): Account? {
        val account = crudRepository.get(accountId)
            ?: return null

        val updatedAccount = account.copy(balance = balance)
        return crudRepository.update(updatedAccount)
    }

    suspend fun execute(accountId: UUID, updatedStatus: AccountStatus): Account? {
        val account = crudRepository.get(accountId)
            ?: return null

        val updatedAccount = account.copy(status = updatedStatus)
        return crudRepository.update(updatedAccount)
    }
}

class DeleteAccountUseCase(
    crudRepository: CRUDRepository<Account>
) : DeleteUseCase<Account>(crudRepository)



class GetAccountsByOwnerUseCase(
    private val accountRepository: AccountRepository
) {
    suspend fun execute(ownerId: UUID): List<Account> {
        return accountRepository.getAccountsByOwner(ownerId)
    }
}

class GetAccountsByBankUseCase(
    private val accountRepository: AccountRepository
) {
    suspend fun execute(bankUBN: UUID): List<Account> {
        return accountRepository.getAccountsByBank(bankUBN)
    }
}