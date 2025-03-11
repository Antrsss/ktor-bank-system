package com.example.application.usecases.account

import com.example.domain.AccountStatus
import com.example.domain.entities.Account
import java.util.UUID

class AccountFacade(
    private val createAccountUseCase: CreateAccountUseCase,
    private val getAccountByIdUseCase: GetAccountByIdUseCase,
    private val getAccountsByOwnerUseCase: GetAccountsByOwnerUseCase,
    private val getAccountsByBankUBNUseCase: GetAccountsByBankUBNUseCase,
    private val getAllAccountsUseCase: GetAllAccountsUseCase,
    private val updateAccountNameUseCase: UpdateAccountNameUseCase,
    private val updateAccountBalanceUseCases: UpdateAccountBalanceUseCases,
    private val updateAccountStatusUseCases: UpdateAccountStatusUseCases,
    private val deleteAccountUseCase: DeleteAccountUseCase,
) {
    suspend fun createAccount(account: Account): Account {
        return createAccountUseCase.execute(account)
    }

    suspend fun getAccountById(accountId: UUID): Account? {
        return getAccountByIdUseCase.execute(accountId)
    }

    suspend fun getAccountByOwner(ownerId: UUID): List<Account> {
        return getAccountsByOwnerUseCase.execute(ownerId)
    }

    suspend fun getAccountByBank(bankUBN: UUID): List<Account> {
        return getAccountsByBankUBNUseCase.execute(bankUBN)
    }

    suspend fun getAllAccounts(): List<Account> {
        return getAllAccountsUseCase.execute()
    }

    suspend fun updateAccountName(accountId: UUID, newName: String): Account {
        return updateAccountNameUseCase.execute(accountId, newName)
    }

    suspend fun updateAccountBalance(accountId: UUID, newBalance: Double): Account {
        return updateAccountBalanceUseCases.execute(accountId, newBalance)
    }

    suspend fun updateAccountStatus(accountId: UUID, newStatus: AccountStatus): Account {
        return updateAccountStatusUseCases.execute(accountId, newStatus)
    }

    suspend fun deleteAccount(accountId: UUID): Boolean {
        return deleteAccountUseCase.execute(accountId)
    }
}