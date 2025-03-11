package com.example.application.usecases.loan_obligation

import com.example.domain.LoanObligationStatus
import com.example.domain.entities.loan_obligations.DeferredPayment
import java.util.*

/*
class CreateDeferredPaymentUseCase(
    private val deferredPaymentRepository: DeferredPaymentRepository
) {
    suspend fun execute(payment: DeferredPayment) {
        deferredPaymentRepository.createDeferredPayment(payment)
    }
}

class GetDeferredPaymentByIdUseCase(
    private val deferredPaymentRepository: DeferredPaymentRepository
) {
    suspend fun execute(id: UUID): DeferredPayment? {
        return deferredPaymentRepository.getDeferredPaymentById(id)
    }
}

class GetDeferredPaymentsByOwner(
    private val deferredPaymentRepository: DeferredPaymentRepository
) {
    suspend fun execute(ownerId: UUID): List<DeferredPayment> {
        return deferredPaymentRepository.getDeferredPaymentsByOwner(ownerId)
    }
}

class GetDeferredPaymentsByBankUseCase(
    private val deferredPaymentRepository: DeferredPaymentRepository
) {
    suspend fun execute(bankUBN: UUID): List<DeferredPayment> {
        return deferredPaymentRepository.getDeferredPaymentsByBank(bankUBN)
    }
}

class UpdateDeferredPaymentUseCase(
    private val deferredPaymentRepository: DeferredPaymentRepository
) {
    suspend fun execute(paymentId: UUID, newStatus: LoanObligationStatus) {
        val deferredPayment = deferredPaymentRepository.getDeferredPaymentById(paymentId)
            ?: throw IllegalArgumentException("Deferred payment not found")

        val updatedPayment = deferredPayment.copy(status = newStatus)
        deferredPaymentRepository.updateDeferredPayment(updatedPayment)
    }

    suspend fun execute(paymentId: UUID, amountToPay: Double) {
        val deferredPayment = deferredPaymentRepository.getDeferredPaymentById(paymentId)
            ?: throw IllegalArgumentException("Credit not found")

        val newAmount = deferredPayment.amount + amountToPay
        val updatedPayment = deferredPayment.copy(amount = newAmount)
        deferredPaymentRepository.updateDeferredPayment(updatedPayment)
    }
}

class DeleteDeferredPaymentUseCase(
    private val deferredPaymentRepository: DeferredPaymentRepository
) {
    suspend fun execute(paymentId: UUID): Boolean {
        return deferredPaymentRepository.deleteDeferredPayment(paymentId)
    }
}*/
