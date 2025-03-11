package com.example.usecases

import com.example.domain.entities.Bank
import com.example.domain.repositories.BankRepository
import java.util.UUID

class CreateBankUseCase(
    private val bankRepository: BankRepository
) {
    suspend fun execute(bank: Bank): Bank {
        bankRepository.createBank(bank)
        return bank
    }
}

class GetBankByUBNUseCase(
    private val bankRepository: BankRepository
) {
    suspend fun execute(bankUBN: UUID): Bank? {
        return bankRepository.getBankByUBN(bankUBN)
    }
}

class GetBankByNameUseCase(
    private val bankRepository: BankRepository
) {
    suspend fun execute(bankName: String): Bank? {
        return bankRepository.getBankByName(bankName)
    }
}

class GetAllBanksUseCase(
    private val bankRepository: BankRepository
) {
    suspend fun execute(): List<Bank> {
        return bankRepository.getAllBanks()
    }
}

class UpdateBankUseCase(
    private val bankRepository: BankRepository
) {
    suspend fun execute(bankUBN: UUID, newName: String): Bank {
        val bank = bankRepository.getBankByUBN(bankUBN)
            ?: throw IllegalArgumentException("Bank not found")

        val updatedBank = bank.copy(bankName = newName)
        bankRepository.updateBank(updatedBank)
        return updatedBank
    }
}

class DeleteBankUseCase(
    private val bankRepository: BankRepository
) {
    suspend fun execute(bankUBN: UUID): Boolean {
        return bankRepository.deleteBank(bankUBN)
    }
}