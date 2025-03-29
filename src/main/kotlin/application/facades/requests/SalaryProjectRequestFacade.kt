package com.example.application.facades.requests

import com.example.application.facades.base.RequestFacade
import com.example.application.usecases.base.*
import com.example.domain.entities.requests.SalaryProjectRequest

class SalaryProjectRequestFacade(
    createRequestUseCase: CreateUseCase<SalaryProjectRequest>,
    getRequestUseCase: GetUseCase<SalaryProjectRequest>,
    getRequestsByBankUseCase: GetRequestsByBankUseCase<SalaryProjectRequest>,
    updateRequestStatusUseCase: UpdateRequestStatusUseCase<SalaryProjectRequest>,
    deleteRequestUseCase: DeleteUseCase<SalaryProjectRequest>

) : RequestFacade<SalaryProjectRequest>(
    createRequestUseCase,
    getRequestUseCase,
    getRequestsByBankUseCase,
    updateRequestStatusUseCase,
    deleteRequestUseCase
)