package com.example.application.usecases.account

import com.example.domain.AccountStatus
import com.example.domain.entities.Account
import com.example.domain.repositories.AccountRepository
import java.util.*
import javax.security.auth.login.AccountNotFoundException

class CreateAccountUseCase(
    private val accountRepository: AccountRepository
) {
    suspend fun execute(account: Account): Account {
        accountRepository.createAccount(account)
        return account
    }
}

class GetAccountByIdUseCase(
    private val accountRepository: AccountRepository
) {
    suspend fun execute(accountId: UUID): Account? {
        val account = accountRepository.getAccountById(accountId)
        return account
    }
}

class GetAccountsByOwnerUseCase(
    private val accountRepository: AccountRepository
) {
    suspend fun execute(ownerId: UUID): List<Account> {
        return accountRepository.getAccountsByOwnerId(ownerId)
    }
}

class GetAccountsByBankUBNUseCase(
    private val accountRepository: AccountRepository
) {
    suspend fun execute(bankUBN: UUID): List<Account> {
        return accountRepository.getAccountsByBankUBN(bankUBN)
    }
}

class GetAllAccountsUseCase(
    private val accountRepository: AccountRepository
) {
    suspend fun execute(): List<Account> {
        return accountRepository.getAllAccounts()
    }
}

class UpdateAccountNameUseCase(
    private val accountRepository: AccountRepository
) {
    suspend fun execute(accountId: UUID, accountName: String): Account {
        val account = accountRepository.getAccountById(accountId)
            ?: throw AccountNotFoundException("Account with id $accountId not found")

        val updatedAccount = account.copy(accountName = accountName)
        accountRepository.updateAccount(account)
        return updatedAccount
    }
}

class UpdateAccountBalanceUseCases(
    private val accountRepository: AccountRepository
) {
    suspend fun execute(accountId: UUID, balance: Double): Account {
        val account = accountRepository.getAccountById(accountId)
            ?: throw AccountNotFoundException("Account with id $accountId not found")

        val updatedAccount = account.copy(balance = balance)
        accountRepository.updateAccount(account)
        return updatedAccount
    }
}

class UpdateAccountStatusUseCases(
    private val accountRepository: AccountRepository
) {
    suspend fun execute(accountId: UUID, status: AccountStatus): Account {
        val account = accountRepository.getAccountById(accountId)
            ?: throw AccountNotFoundException("Account with id $accountId not found")

        val updatedAccount = account.copy(status = status)
        accountRepository.updateAccount(account)
        return updatedAccount
    }
}

class DeleteAccountUseCase(
    private val accountRepository: AccountRepository
) {
    suspend fun execute(accountId: UUID): Boolean {
        return accountRepository.deleteAccount(accountId)
    }
}