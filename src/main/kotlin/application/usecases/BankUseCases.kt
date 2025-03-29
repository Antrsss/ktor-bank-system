package com.example.usecases

import com.example.application.usecases.base.CreateUseCase
import com.example.application.usecases.base.DeleteUseCase
import com.example.application.usecases.base.GetUseCase
import com.example.domain.entities.Bank
import com.example.domain.repositories.BankRepository
import com.example.domain.repositories.base.CRUDRepository
import java.util.*

class CreateBankUseCase(
    bankRepository: BankRepository,
) : CreateUseCase<Bank>(bankRepository)

class GetBankUseCase(
    bankRepository: BankRepository
) : GetUseCase<Bank>(bankRepository)

class GetAllBanksUseCase(
    private val bankRepository: BankRepository
) {
    suspend fun execute(): List<Bank> {
        return bankRepository.getAllBanks()
    }
}

class UpdateBankNameUseCase(
    private val bankRepository: BankRepository
) {
    suspend fun execute(bankUBN: UUID, newName: String): Bank? {
        val bank = bankRepository.get(bankUBN)
            ?: return null

        val updatedBank = bank.copy(bankName = newName)
        return bankRepository.update(updatedBank)
    }
}

class DeleteBankUseCase(
    bankRepository: BankRepository
) : DeleteUseCase<Bank>(bankRepository)