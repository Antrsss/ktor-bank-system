package com.example.application.facades

import com.example.application.usecases.*
import com.example.domain.AccountStatus
import com.example.domain.entities.Account
import java.util.UUID

class AccountFacade(
    private val createAccountUseCase: CreateAccountUseCase,
    private val getAccountUseCase: GetAccountUseCase,
    private val getAccountsByOwnerUseCase: GetAccountsByOwnerUseCase,
    private val getAccountsByBankUseCase: GetAccountsByBankUseCase,
    private val updateAccountUseCase: UpdateAccountUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase,
) {
    suspend fun createAccount(account: Account): Unit {
        return createAccountUseCase.execute(account)
    }

    suspend fun getAccount(accountId: UUID): Account? {
        return getAccountUseCase.execute(accountId)
    }

    suspend fun getAccountByOwner(ownerId: UUID): List<Account> {
        return getAccountsByOwnerUseCase.execute(ownerId)
    }

    suspend fun getAccountByBank(bankUBN: UUID): List<Account> {
        return getAccountsByBankUseCase.execute(bankUBN)
    }

    suspend fun updateAccountName(accountId: UUID, newName: String): Account? {
        return updateAccountUseCase.execute(accountId, newName)
    }

    suspend fun updateAccountBalance(accountId: UUID, newBalance: Double): Account? {
        return updateAccountUseCase.execute(accountId, newBalance)
    }

    suspend fun updateAccountStatus(accountId: UUID, updatedStatus: AccountStatus): Account? {
        return updateAccountUseCase.execute(accountId, updatedStatus)
    }

    suspend fun deleteAccount(accountId: UUID): Boolean {
        return deleteAccountUseCase.execute(accountId)
    }
}