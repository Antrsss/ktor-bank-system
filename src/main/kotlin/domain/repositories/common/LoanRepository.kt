package com.example.domain.repositories.common

import com.example.domain.abstracts.Loan
import com.example.domain.repositories.base.CRUDRepository
import java.util.*

interface LoanRepository<T : Loan>: CRUDRepository<T> {
    suspend fun getLoansByOwner(ownerId: UUID): List<T>
    suspend fun getLoansByBank(bankUBN: UUID): List<T>
}
