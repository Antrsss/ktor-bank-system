package com.example.application.usecases.enterprise

import com.example.domain.entities.Enterprise
import java.util.UUID

class EnterpriseFacade(
    private val createEnterpriseUseCase: CreateEnterpriseUseCase,
    private val getEnterpriseByIdUseCase: GetEnterpriseByIdUseCase,
    private val getAllBankEnterprisesUseCase: GetAllBankEnterprisesUseCase,
    private val updateEnterpriseLegalNameUseCase: UpdateEnterpriseLegalNameUseCase,
    private val updateEnterpriseBankUBNUseCase: UpdateEnterpriseBankUBNUseCase,
    private val updateEnterpriseLegalAdressUseCase: UpdateEnterpriseLegalAdressUseCase,
    private val deleteEnterpriseUseCase: DeleteEnterpriseUseCase,
) {
    suspend fun createEnterprise(enterprise: Enterprise) {
        createEnterpriseUseCase.execute(enterprise)
    }

    suspend fun getEnterpriseById(enterpriseId: UUID): Enterprise? {
        return getEnterpriseByIdUseCase.execute(enterpriseId)
    }

    suspend fun getAllBankEnterprises(bankUBN: UUID): List<Enterprise> {
        return getAllBankEnterprisesUseCase.execute(bankUBN)
    }

    suspend fun updateEnterpriseLegalName(enterpriseId: UUID, newLegalName: String) {
        return updateEnterpriseLegalNameUseCase.execute(enterpriseId, newLegalName)
    }

    suspend fun updateEnterpriseBankUBN(enterpriseId: UUID, newBankUBN: UUID) {
        return updateEnterpriseBankUBNUseCase.execute(enterpriseId, newBankUBN)
    }

    suspend fun updateEnterpriseLegalAdress(enterpriseId: UUID, newLegalAdress: String) {
        return updateEnterpriseLegalAdressUseCase.execute(enterpriseId, newLegalAdress)
    }

    suspend fun deleteEnterprise(enterpriseId: UUID): Boolean {
        return deleteEnterpriseUseCase.execute(enterpriseId)
    }
}