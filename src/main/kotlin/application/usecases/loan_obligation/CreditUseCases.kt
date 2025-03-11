package com.example.usecases

import com.example.domain.LoanObligationStatus
import com.example.domain.entities.loan_obligations.Credit
import java.util.*

/*
class CreateCreditUseCase(
    private val creditRepository: CreditRepository
) {
    suspend fun execute(credit: Credit) {
        creditRepository.createCredit(credit)
    }
}

class GetCreditUseCase(
    private val creditRepository: CreditRepository
) {
    suspend fun execute(creditId: UUID): Credit? {
        return creditRepository.getCreditById(creditId)
    }
}

class GetCreditsByOwnerId(
    private val creditRepository: CreditRepository
) {
    suspend fun execute(ownerId: UUID): List<Credit> {
        return creditRepository.getCreditsByOwner(ownerId)
    }
}

class GetCreditsByBankUseCase(
    private val creditRepository: CreditRepository
) {
    suspend fun execute(bankUBN: UUID): List<Credit> {
        return creditRepository.getCreditsByBank(bankUBN)
    }
}

class UpdateCreditUseCase(
    private val creditRepository: CreditRepository
) {
    suspend fun execute(creditId: UUID, newStatus: LoanObligationStatus) {
        val credit = creditRepository.getCreditById(creditId)
            ?: throw IllegalArgumentException("Credit not found")

        val updatedCredit = credit.copy(status = newStatus)
        creditRepository.updateCredit(updatedCredit)
    }

    suspend fun execute(creditId: UUID, amountToPay: Double) {
        val credit = creditRepository.getCreditById(creditId)
            ?: throw IllegalArgumentException("Credit not found")

        val newAmount = credit.amount + amountToPay
        val updatedCredit = credit.copy(amount = newAmount)
        creditRepository.updateCredit(updatedCredit)
    }
}

class DeleteCreditUseCase(
    private val creditRepository: CreditRepository
) {
    suspend fun execute(creditId: UUID): Boolean {
        return creditRepository.deleteCredit(creditId)
    }
}*/
