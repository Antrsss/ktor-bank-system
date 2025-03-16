package com.example.domain.repositories.common

import com.example.domain.abstracts.Loan
import java.util.*

interface LoanRepository<T : Loan> {
    suspend fun getLoansByOwner(ownerId: UUID): List<T>
    suspend fun getLoansByBank(bankUBN: UUID): List<T>
}
