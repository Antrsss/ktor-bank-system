package com.example.application.usecases.requests

import com.example.application.usecases.base.*
import com.example.domain.entities.requests.SalaryProjectRequest
import com.example.domain.repositories.common.RequestRepository
import com.example.domain.repositories.base.ImmutableRepository

class CreateSalaryProjectRequestUseCase(
    repository: RequestRepository<SalaryProjectRequest>
) : CreateUseCase<SalaryProjectRequest>(repository)

class GetSalaryProjectRequestUseCase(
    repository: RequestRepository<SalaryProjectRequest>
) : GetUseCase<SalaryProjectRequest>(repository)

class GetSalaryProjectRequestsByBankUseCase(
    repository: RequestRepository<SalaryProjectRequest>
) : GetRequestsByBankUseCase<SalaryProjectRequest>(repository)

class UpdateSalaryProjectRequestUseCase(
    repository: RequestRepository<SalaryProjectRequest>
) : UpdateRequestStatusUseCase<SalaryProjectRequest>(repository)

class DeleteSalaryProjectRequestUseCase(
    repository: RequestRepository<SalaryProjectRequest>
) : DeleteUseCase<SalaryProjectRequest>(repository)