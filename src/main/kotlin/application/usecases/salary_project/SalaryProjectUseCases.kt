package com.example.application.usecases.salary_project

import com.example.domain.SalaryProjectStatus
import com.example.domain.entities.SalaryProject
import com.example.domain.repositories.SalaryProjectRepository
import java.util.*
import javax.security.auth.login.AccountNotFoundException

class CreateSalaryProjectUseCase(
    private val salaryProjectRepository: SalaryProjectRepository,
) {
    suspend fun execute(salaryProject: SalaryProject) {
        salaryProjectRepository.createSalaryProject(salaryProject)
    }
}

class GetSalaryProjectByIdUseCase(
    private val salaryProjectRepository: SalaryProjectRepository,
) {
    suspend fun execute(salaryProjectId: UUID): SalaryProject? {
        return salaryProjectRepository.getSalaryProjectById(salaryProjectId)
    }
}

class GetSalaryProjectsByBankUseCase(
    private val salaryProjectRepository: SalaryProjectRepository,
) {
    suspend fun execute(bankUBN: UUID): List<SalaryProject> {
        return salaryProjectRepository.getSalaryProjectsByBank(bankUBN)
    }
}

class UpdateSalaryProjectStatusUseCase(
    private val salaryProjectRepository: SalaryProjectRepository,
) {
    suspend fun execute(salaryProjectId: UUID, newStatus: SalaryProjectStatus) {
        val salaryProject = salaryProjectRepository.getSalaryProjectById(salaryProjectId)
            ?: throw AccountNotFoundException("Salary project with id $salaryProjectId not found")

        val updatedSalaryProject = salaryProject.copy(status = newStatus)
        salaryProjectRepository.updateSalaryProject(updatedSalaryProject)
    }
}

class DeleteSalaryProjectUseCase(
    private val salaryProjectRepository: SalaryProjectRepository,
) {
    suspend fun execute(salaryProjectId: UUID): Boolean {
        return salaryProjectRepository.deleteSalaryProject(salaryProjectId)
    }
}