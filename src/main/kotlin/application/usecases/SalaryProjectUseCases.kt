package com.example.application.usecases

import com.example.application.usecases.base.CreateUseCase
import com.example.application.usecases.base.DeleteUseCase
import com.example.application.usecases.base.GetUseCase
import com.example.domain.SalaryProjectStatus
import com.example.domain.entities.SalaryProject
import com.example.domain.repositories.SalaryProjectRepository
import java.util.*

class CreateSalaryProjectUseCase(
    salaryProjectRepository: SalaryProjectRepository,
) : CreateUseCase<SalaryProject>(salaryProjectRepository)

class GetSalaryProjectUseCase(
    salaryProjectRepository: SalaryProjectRepository,
) : GetUseCase<SalaryProject>(salaryProjectRepository)

class UpdateSalaryProjectStatusUseCase(
    private val salaryProjectRepository: SalaryProjectRepository,
) {
    suspend fun execute(salaryProjectId: UUID, newStatus: SalaryProjectStatus): SalaryProject? {
        val salaryProject = salaryProjectRepository.get(salaryProjectId)
            ?: return null

        val updatedSalaryProject = salaryProject.copy(status = newStatus)
        return salaryProjectRepository.update(updatedSalaryProject)
    }
}

class DeleteSalaryProjectUseCase(
    salaryProjectRepository: SalaryProjectRepository,
) : DeleteUseCase<SalaryProject>(salaryProjectRepository)

class GetSalaryProjectsByBankUseCase(
    private val salaryProjectRepository: SalaryProjectRepository,
) {
    suspend fun execute(bankUBN: UUID): List<SalaryProject> {
        return salaryProjectRepository.getSalaryProjectsByBank(bankUBN)
    }
}