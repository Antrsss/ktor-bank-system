package com.example.application.facades

import com.example.domain.entities.Bank
import com.example.usecases.*
import java.util.*

class BankFacade(
    private val createBankUseCase: CreateBankUseCase,
    private val getBankUseCase: GetBankUseCase,
    private val getAllBanksUseCase: GetAllBanksUseCase,
    private val updateBankNameUseCase: UpdateBankNameUseCase,
    private val deleteBankUseCase: DeleteBankUseCase,) {

    suspend fun createBank(bank: Bank): Unit {
        return createBankUseCase.execute(bank)
    }

    suspend fun getBank(bankUBN: UUID): Bank? {
        return getBankUseCase.execute(bankUBN)
    }

    suspend fun getAllBanks(): List<Bank> {
        return getAllBanksUseCase.execute()
    }

    suspend fun updateBankName(bankUBN: UUID, newName: String): Bank? {
        return updateBankNameUseCase.execute(bankUBN, newName)
    }

    suspend fun deleteBank(bankUBN: UUID): Boolean {
        return deleteBankUseCase.execute(bankUBN)
    }
}