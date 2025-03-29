package com.example.domain.repositories

import com.example.domain.entities.SalaryProject
import com.example.domain.repositories.base.CRUDRepository
import java.util.*


interface SalaryProjectRepository: CRUDRepository<SalaryProject> {
    suspend fun getSalaryProjectsByBank(bankUBN: UUID): List<SalaryProject>
}