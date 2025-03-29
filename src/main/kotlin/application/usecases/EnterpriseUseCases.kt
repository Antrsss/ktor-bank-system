package com.example.application.usecases

import com.example.application.usecases.base.CreateUseCase
import com.example.application.usecases.base.DeleteUseCase
import com.example.application.usecases.base.GetUseCase
import com.example.domain.repositories.EnterpriseRepository
import com.example.domain.entities.Enterprise
import java.util.UUID

class CreateEnterpriseUseCase(
    enterpriseRepository: EnterpriseRepository
) : CreateUseCase<Enterprise>(enterpriseRepository)

class GetEnterpriseUseCase(
    enterpriseRepository: EnterpriseRepository
) : GetUseCase<Enterprise>(enterpriseRepository)

class UpdateEnterpriseLegalNameUseCase(
    private val enterpriseRepository: EnterpriseRepository
) {
    suspend fun execute(enterpriseId: UUID, newLegalName: String): Enterprise? {
        val enterprise = enterpriseRepository.get(enterpriseId)
            ?: return null

        val updatedEnterprise = enterprise.copy(legalName = newLegalName)
        return enterpriseRepository.update(updatedEnterprise)
    }
}

class UpdateEnterpriseLegalAdressUseCase(
    private val enterpriseRepository: EnterpriseRepository
) {
    suspend fun execute(enterpriseId: UUID, newLegalAdress: String): Enterprise? {
        val enterprise = enterpriseRepository.get(enterpriseId)
            ?: return null

        val updatedEnterprise = enterprise.copy(legalAdress = newLegalAdress)
        return enterpriseRepository.update(updatedEnterprise)
    }
}

class DeleteEnterpriseUseCase(
    enterpriseRepository: EnterpriseRepository
) : DeleteUseCase<Enterprise>(enterpriseRepository)

class GetEnterprisesByBankUseCase(
    private val enterpriseRepository: EnterpriseRepository
) {
    suspend fun execute(bankUBN: UUID): List<Enterprise> {
        return enterpriseRepository.getEnterprisesByBank(bankUBN)
    }
}