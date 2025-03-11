package com.example.application.usecases.salary_project

import com.example.domain.SalaryProjectStatus
import com.example.domain.entities.SalaryProject
import java.util.*

class SalaryProjectFacade(
    private val createSalaryProjectUseCase: CreateSalaryProjectUseCase,
    private val getSalaryProjectByIdUseCase: GetSalaryProjectByIdUseCase,
    private val getSalaryProjectsByBankUseCase: GetSalaryProjectsByBankUseCase,
    private val updateSalaryProjectStatusUseCase: UpdateSalaryProjectStatusUseCase,
    private val deleteSalaryProjectUseCase: DeleteSalaryProjectUseCase
) {
    suspend fun createSalaryProject(salaryProject: SalaryProject) {
        createSalaryProjectUseCase.execute(salaryProject)
    }

    suspend fun getSalaryProjectById(salaryProjectId: UUID): SalaryProject? {
        return getSalaryProjectByIdUseCase.execute(salaryProjectId)
    }

    suspend fun getSalaryProjectsByBank(bankUBN: UUID): List<SalaryProject> {
        return getSalaryProjectsByBankUseCase.execute(bankUBN)
    }

    suspend fun updateSalaryProjectStatus(salaryProjectId: UUID, newStatus: SalaryProjectStatus) {
        updateSalaryProjectStatusUseCase.execute(salaryProjectId, newStatus)
    }

    suspend fun deleteSalaryProject(salaryProjectId: UUID): Boolean {
        return deleteSalaryProjectUseCase.execute(salaryProjectId)
    }
}