package com.example.application.usecases.enterprise

import com.example.domain.repositories.EnterpriseRepository
import com.example.domain.EnterpriseType
import com.example.domain.entities.Enterprise
import java.util.UUID

class CreateEnterpriseUseCase(
    private val enterpriseRepository: EnterpriseRepository
) {
    suspend fun execute(enterprise: Enterprise) {
        enterpriseRepository.createEnterprise(enterprise)
    }
}

class GetEnterpriseByIdUseCase(
    private val enterpriseRepository: EnterpriseRepository
) {
    suspend fun execute(enterpriseId: UUID): Enterprise? {
        return enterpriseRepository.getEnterpriseById(enterpriseId)
    }
}

class GetAllBankEnterprisesUseCase(
    private val enterpriseRepository: EnterpriseRepository
) {
    suspend fun execute(bankUBN: UUID): List<Enterprise> {
        return enterpriseRepository.getAllBankEnterprises(bankUBN)
    }
}

class UpdateEnterpriseLegalNameUseCase(
    private val enterpriseRepository: EnterpriseRepository
) {
    suspend fun execute(enterpriseId: UUID, newLegalName: String) {
        val enterprise = enterpriseRepository.getEnterpriseById(enterpriseId)
            ?: throw IllegalArgumentException("Enterprise not found")

        val updatedEnterprise = enterprise.copy(legalName = newLegalName)
        enterpriseRepository.updateEnterprise(updatedEnterprise)
    }
}

class UpdateEnterpriseBankUBNUseCase(
    private val enterpriseRepository: EnterpriseRepository
) {
    suspend fun execute(enterpriseId: UUID, newBankUBN: UUID) {
        val enterprise = enterpriseRepository.getEnterpriseById(enterpriseId)
            ?: throw IllegalArgumentException("Enterprise not found")

        val updatedEnterprise = enterprise.copy(bankUBN = newBankUBN)
        enterpriseRepository.updateEnterprise(updatedEnterprise)
    }
}

class UpdateEnterpriseLegalAdressUseCase(
    private val enterpriseRepository: EnterpriseRepository
) {
    suspend fun execute(enterpriseId: UUID, newLegalAdress: String) {
        val enterprise = enterpriseRepository.getEnterpriseById(enterpriseId)
            ?: throw IllegalArgumentException("Enterprise not found")

        val updatedEnterprise = enterprise.copy(legalAdress = newLegalAdress)
        enterpriseRepository.updateEnterprise(updatedEnterprise)
    }
}

class DeleteEnterpriseUseCase(
    private val enterpriseRepository: EnterpriseRepository
) {
    suspend fun execute(enterpriseId: UUID): Boolean {
        return enterpriseRepository.deleteEnterprise(enterpriseId)
    }
}