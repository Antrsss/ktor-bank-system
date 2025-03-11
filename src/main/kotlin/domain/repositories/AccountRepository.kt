package com.example.domain.repositories

import com.example.domain.entities.Account
import java.util.*

interface AccountRepository {
    suspend fun createAccount(account: Account)
    suspend fun getAccountById(accountId: UUID): Account?
    suspend fun getAccountsByOwnerId(ownerId: UUID): List<Account>
    suspend fun getAccountsByBankUBN(bankUBN: UUID): List<Account>
    suspend fun getAllAccounts(): List<Account>
    suspend fun updateAccount(account: Account)
    suspend fun deleteAccount(accountId: UUID): Boolean
}