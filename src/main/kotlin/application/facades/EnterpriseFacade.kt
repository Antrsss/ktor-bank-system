package com.example.application.facades

import com.example.application.usecases.*
import com.example.domain.entities.Enterprise
import java.util.UUID

class EnterpriseFacade(
    private val createEnterpriseUseCase: CreateEnterpriseUseCase,
    private val getEnterpriseUseCase: GetEnterpriseUseCase,
    private val getEnterprisesByBankUseCase: GetEnterprisesByBankUseCase,
    private val updateEnterpriseLegalNameUseCase: UpdateEnterpriseLegalNameUseCase,
    private val updateEnterpriseLegalAdressUseCase: UpdateEnterpriseLegalAdressUseCase,
    private val deleteEnterpriseUseCase: DeleteEnterpriseUseCase,
) {
    suspend fun createEnterprise(enterprise: Enterprise) {
        createEnterpriseUseCase.execute(enterprise)
    }
    suspend fun getEnterprise(enterpriseId: UUID): Enterprise? {
        return getEnterpriseUseCase.execute(enterpriseId)
    }

    suspend fun getEnterprisesByBank(bankUBN: UUID): List<Enterprise> {
        return getEnterprisesByBankUseCase.execute(bankUBN)
    }

    suspend fun updateEnterpriseLegalName(enterpriseId: UUID, newLegalName: String): Enterprise? {
        return updateEnterpriseLegalNameUseCase.execute(enterpriseId, newLegalName)
    }

    suspend fun updateEnterpriseLegalAdress(enterpriseId: UUID, newLegalAdress: String): Enterprise? {
        return updateEnterpriseLegalAdressUseCase.execute(enterpriseId, newLegalAdress)
    }

    suspend fun deleteEnterprise(enterpriseId: UUID): Boolean {
        return deleteEnterpriseUseCase.execute(enterpriseId)
    }
}