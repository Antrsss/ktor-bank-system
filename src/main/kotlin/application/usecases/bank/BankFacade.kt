package com.example.application.usecases.bank

import com.example.domain.entities.Bank
import com.example.usecases.*
import java.util.*

class BankFacade(
    private val createBankUseCase: CreateBankUseCase,
    private val getBankByUBNUseCase: GetBankByUBNUseCase,
    private val getBankByNameUseCase: GetBankByNameUseCase,
    private val getAllBanksUseCase: GetAllBanksUseCase,
    private val updateBankUseCase: UpdateBankUseCase,
    private val deleteBankUseCase: DeleteBankUseCase,) {

    suspend fun createBank(bank: Bank): Bank {
        return createBankUseCase.execute(bank)
    }

    suspend fun getBankByUBN(bankUBN: UUID): Bank? {
        return getBankByUBNUseCase.execute(bankUBN)
    }

    suspend fun getBankByName(bankName: String): Bank? {
        return getBankByNameUseCase.execute(bankName)
    }

    suspend fun getAllBanks(): List<Bank> {
        return getAllBanksUseCase.execute()
    }

    suspend fun updateBank(bankUBN: UUID, newName: String): Bank {
        return updateBankUseCase.execute(bankUBN, newName)
    }

    suspend fun deleteBank(bankUBN: UUID): Boolean {
        return deleteBankUseCase.execute(bankUBN)
    }
}