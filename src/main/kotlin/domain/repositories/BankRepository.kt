package com.example.domain.repositories

import com.example.domain.entities.Bank
import java.util.*

interface BankRepository {
    suspend fun createBank(bank: Bank)
    suspend fun getBankByUBN(bankUBN: UUID): Bank?
    suspend fun getBankByName(bankName: String): Bank?
    suspend fun getAllBanks(): List<Bank>
    suspend fun updateBank(bank: Bank)
    suspend fun deleteBank(bankUBN: UUID): Boolean
}