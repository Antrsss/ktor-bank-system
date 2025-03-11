package com.example.application.usecases.loan_obligation

import com.example.domain.LoanObligationStatus
import com.example.domain.entities.loan_obligations.DeferredPayment
import java.util.*

/*
class DeferredPaymentFacade(
    private val createDeferredPaymentUseCase: CreateDeferredPaymentUseCase,
    private val getDeferredPaymentByIdUseCase: GetDeferredPaymentByIdUseCase,
    private val getDeferredPaymentsByOwner: GetDeferredPaymentsByOwner,
    private val getDeferredPaymentsByBankUseCase: GetDeferredPaymentsByBankUseCase,
    private val updateDeferredPaymentUseCase: UpdateDeferredPaymentUseCase,
    private val deleteDeferredPaymentUseCase: DeleteDeferredPaymentUseCase
) {
    suspend fun createDeferredPayment(payment: DeferredPayment) {
        createDeferredPaymentUseCase.execute(payment)
    }

    suspend fun getDeferredPaymentById(paymentId: UUID): DeferredPayment? {
        return getDeferredPaymentByIdUseCase.execute(paymentId)
    }

    suspend fun getDeferredPaymentsByOwner(ownerId: UUID): List<DeferredPayment> {
        return getDeferredPaymentsByOwner.execute(ownerId)
    }

    suspend fun getDeferredPaymentsByBank(bankId: UUID): List<DeferredPayment> {
        return getDeferredPaymentsByBankUseCase.execute(bankId)
    }

    suspend fun updateDeferredPaymentStatus(paymentId: UUID, newStatus: LoanObligationStatus) {
        updateDeferredPaymentUseCase.execute(paymentId, newStatus)
    }

    suspend fun payForDeferredPayment(paymentId: UUID, amountToPay: Double) {
        updateDeferredPaymentUseCase.execute(paymentId, amountToPay)
    }

    suspend fun deleteDeferredPayment(paymentId: UUID): Boolean {
        return deleteDeferredPaymentUseCase.execute(paymentId)
    }
}*/
