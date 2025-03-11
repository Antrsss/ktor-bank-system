package com.example.domain.repositories

import com.example.domain.entities.SalaryProject
import com.example.domain.entities.requests.SalaryProjectRequest
import java.util.*


interface SalaryProjectRepository {
    suspend fun createSalaryProject(salaryProject: SalaryProject) //у предприятия только один зар. проект
    suspend fun getSalaryProjectById(salaryProjectId: UUID): SalaryProject?
    suspend fun getSalaryProjectsByBank(bankUBN: UUID): List<SalaryProject>
    suspend fun updateSalaryProject(salaryProject: SalaryProject)
    suspend fun deleteSalaryProject(salaryProjectId: UUID): Boolean
}

interface SalaryProjectRequestRepository {
    suspend fun createRequest(request: SalaryProjectRequest)
    suspend fun getRequestById(requestId: UUID): SalaryProjectRequest?
    //fun getRequestsByClientId(clientId: UUID): List<SalaryProjectRequest>
    suspend fun updateRequest(request: SalaryProjectRequest)
    suspend fun deleteRequest(requestId: UUID): Boolean
}