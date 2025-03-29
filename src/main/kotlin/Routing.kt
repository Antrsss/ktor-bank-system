package com.example

import com.example.adapters.controllers.*
import com.example.adapters.controllers.common.loansController
import com.example.adapters.controllers.common.requestsController
import com.example.adapters.controllers.common.usersController
import com.example.application.facades.*
import com.example.application.facades.loans.CreditFacade
import com.example.application.facades.loans.DeferredPaymentFacade
import com.example.application.facades.requests.*
import com.example.application.facades.users.*
import io.ktor.server.application.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val accountFacade by inject<AccountFacade>()
    val bankFacade by inject<BankFacade>()
    val enterpriseFacade by inject<EnterpriseFacade>()
    val salaryProjectFacade by inject<SalaryProjectFacade>()
    val creditFacade by inject<CreditFacade>()
    val deferredPaymentFacade by inject<DeferredPaymentFacade>()

    val clientFacade by inject<ClientFacade>()
    val foreignClientFacade by inject<ForeignClientFacade>()
    val outsideSpecialistFacade by inject<OutsideSpecialistFacade>()
    val adminFacade by inject<AdminFacade>()
    val managerFacade by inject<ManagerFacade>()
    val operatorFacade by inject<OperatorFacade>()

    val transactionFacade by inject<TransactionFacade>()
    val clientRegistrationRequestFacade by inject<ClientRegistrationRequestFacade>()
    val creditRequestFacade by inject<CreditRequestFacade>()
    val deferredPaymentRequestFacade by inject<DeferredPaymentRequestFacade>()
    val salaryProjectRequestFacade by inject<SalaryProjectRequestFacade>()
    val transactionRequestFacade by inject<TransactionRequestFacade>()

    accountController(accountFacade)
    bankController(bankFacade)
    enterpriseController(enterpriseFacade)
    salaryProjectController(salaryProjectFacade)
    loansController(creditFacade, deferredPaymentFacade)
    usersController(
        clientFacade = clientFacade,
        foreignClientFacade = foreignClientFacade,
        adminFacade = adminFacade,
        managerFacade = managerFacade,
        operatorFacade = operatorFacade,
        outsideSpecialistFacade = outsideSpecialistFacade,
    )
    transactionController(transactionFacade)
    requestsController(
        clientFacade,
        clientRegistrationRequestFacade,
        creditRequestFacade,
        deferredPaymentRequestFacade,
        salaryProjectRequestFacade,
        transactionRequestFacade,
    )
}
