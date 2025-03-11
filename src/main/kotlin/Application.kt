package com.example

import com.example.adapters.controllers.*
import com.example.adapters.repositories.*
import com.example.application.usecases.account.*
import com.example.application.usecases.bank.BankFacade
import com.example.application.usecases.enterprise.*
import com.example.application.usecases.loan_obligation.*
import com.example.application.usecases.salary_project.*
import com.example.application.usecases.transaction.TransactionFacade
import com.example.domain.abstracts.loanObligationModule
import com.example.usecases.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    install(ContentNegotiation) {
    json(Json {
        prettyPrint = true
        ignoreUnknownKeys = true
        serializersModule = SerializersModule {
            contextual(UUIDSerializer)
            include(loanObligationModule)
        }
    })
    }

    configureSerialization()
    configureSecurity()
    configureSerialization()
    configureDatabases()
    configureRouting()

    val bankRepository = BankRepositoryImpl()
    val accountRepository = AccountRepositoryImpl()
    val enterpriseRepository = EnterpriseRepositoryImpl()
    val salaryProjectRepository = SalaryProjectRepositoryImpl()
    val transactionRepository = TransactionRepositoryImpl()
    val loanObligationRepository = LoanObligationRepositoryImpl()

    val createBankUseCase = CreateBankUseCase(bankRepository)
    val getBankByUBNUseCase = GetBankByUBNUseCase(bankRepository)
    val getBankByNameUseCase = GetBankByNameUseCase(bankRepository)
    val getAllBanksUseCase = GetAllBanksUseCase(bankRepository)
    val updateBankUseCase = UpdateBankUseCase(bankRepository)
    val deleteBankUseCase = DeleteBankUseCase(bankRepository)

    val bankFacade = BankFacade(
        createBankUseCase = createBankUseCase,
        getBankByUBNUseCase = getBankByUBNUseCase,
        getBankByNameUseCase = getBankByNameUseCase,
        getAllBanksUseCase = getAllBanksUseCase,
        updateBankUseCase = updateBankUseCase,
        deleteBankUseCase = deleteBankUseCase,
    )

    val createAccountUseCase = CreateAccountUseCase(accountRepository)
    val getAccountByIdUseCase = GetAccountByIdUseCase(accountRepository)
    val getAccountsByOwnerUseCase = GetAccountsByOwnerUseCase(accountRepository)
    val getAccountsByBankUBNUseCase = GetAccountsByBankUBNUseCase(accountRepository)
    val getAllAccountsUseCase = GetAllAccountsUseCase(accountRepository)
    val updateAccountNameUseCase = UpdateAccountNameUseCase(accountRepository)
    val updateAccountBalanceUseCases = UpdateAccountBalanceUseCases(accountRepository)
    val updateAccountStatusUseCases = UpdateAccountStatusUseCases(accountRepository)
    val deleteAccountUseCase = DeleteAccountUseCase(accountRepository)

    val accountFacade = AccountFacade(
        createAccountUseCase = createAccountUseCase,
        getAccountByIdUseCase = getAccountByIdUseCase,
        getAccountsByOwnerUseCase = getAccountsByOwnerUseCase,
        getAccountsByBankUBNUseCase = getAccountsByBankUBNUseCase,
        getAllAccountsUseCase = getAllAccountsUseCase,
        updateAccountNameUseCase = updateAccountNameUseCase,
        updateAccountBalanceUseCases = updateAccountBalanceUseCases,
        updateAccountStatusUseCases = updateAccountStatusUseCases,
        deleteAccountUseCase = deleteAccountUseCase,
    )

    val createEnterpriseUseCase = CreateEnterpriseUseCase(enterpriseRepository)
    val getEnterpriseByIdUseCase = GetEnterpriseByIdUseCase(enterpriseRepository)
    val getAllBankEnterprisesUseCase = GetAllBankEnterprisesUseCase(enterpriseRepository)
    val updateEnterpriseLegalNameUseCase = UpdateEnterpriseLegalNameUseCase(enterpriseRepository)
    val updateEnterpriseBankUBNUseCase = UpdateEnterpriseBankUBNUseCase(enterpriseRepository)
    val updateEnterpriseLegalAdressUseCase = UpdateEnterpriseLegalAdressUseCase(enterpriseRepository)
    val deleteEnterpriseUseCase = DeleteEnterpriseUseCase(enterpriseRepository)

    val enterpriseFacade = EnterpriseFacade(
        createEnterpriseUseCase = createEnterpriseUseCase,
        getEnterpriseByIdUseCase = getEnterpriseByIdUseCase,
        getAllBankEnterprisesUseCase = getAllBankEnterprisesUseCase,
        updateEnterpriseLegalNameUseCase = updateEnterpriseLegalNameUseCase,
        updateEnterpriseBankUBNUseCase = updateEnterpriseBankUBNUseCase,
        updateEnterpriseLegalAdressUseCase = updateEnterpriseLegalAdressUseCase,
        deleteEnterpriseUseCase = deleteEnterpriseUseCase,
    )

    val createSalaryProjectUseCase = CreateSalaryProjectUseCase(salaryProjectRepository)
    val getSalaryProjectByIdUseCase = GetSalaryProjectByIdUseCase(salaryProjectRepository)
    val getSalaryProjectsByBankUseCase = GetSalaryProjectsByBankUseCase(salaryProjectRepository)
    val updateSalaryProjectStatusUseCase = UpdateSalaryProjectStatusUseCase(salaryProjectRepository)
    val deleteSalaryProjectUseCase = DeleteSalaryProjectUseCase(salaryProjectRepository)

    val salaryProjectFacade = SalaryProjectFacade(
        createSalaryProjectUseCase = createSalaryProjectUseCase,
        getSalaryProjectByIdUseCase = getSalaryProjectByIdUseCase,
        getSalaryProjectsByBankUseCase = getSalaryProjectsByBankUseCase,
        updateSalaryProjectStatusUseCase = updateSalaryProjectStatusUseCase,
        deleteSalaryProjectUseCase = deleteSalaryProjectUseCase,
    )

    val createTransactionUseCase = CreateTransactionUseCase(transactionRepository)
    val getTransactionByIdUseCase = GetTransactionByIdUseCase(transactionRepository)
    val getTransactionsByAccountUseCase = GetTransactionsByAccountUseCase(transactionRepository)
    val getTransactionsByBankUseCase = GetTransactionsByBankUseCase(transactionRepository)
    val deleteTransactionUseCase = DeleteTransactionUseCase(transactionRepository)

    val transactionFacade = TransactionFacade(
        createTransactionUseCase = createTransactionUseCase,
        getTransactionByIdUseCase = getTransactionByIdUseCase,
        getTransactionsByAccountUseCase = getTransactionsByAccountUseCase,
        getTransactionsByBankUseCase = getTransactionsByBankUseCase,
        deleteTransactionUseCase = deleteTransactionUseCase,
    )

    val createLoanUseCase = CreateLoanUseCase(loanObligationRepository)
    val getLoanByIdUseCase = GetLoanByIdUseCase(loanObligationRepository)
    val getLoansByOwnerUseCase = GetLoanByOwnerUseCase(loanObligationRepository)
    val getLoansByBankUseCase = GetLoansByBankUseCase(loanObligationRepository)
    val updateLoanUseCase = UpdateLoanUseCase(loanObligationRepository)
    val deleteLoanUseCase = DeleteLoanUseCase(loanObligationRepository)

    val loanObligationFacade = LoanObligationFacade(
        createLoanUseCase = createLoanUseCase,
        getLoanByIdUseCase = getLoanByIdUseCase,
        getLoansByOwnerUseCase = getLoansByOwnerUseCase,
        getLoansByBankUseCase = getLoansByBankUseCase,
        updateLoanUseCase = updateLoanUseCase,
        deleteLoanUseCase = deleteLoanUseCase,
    )

    // Подключение контроллера
    bankController(bankFacade)
    accountController(accountFacade)
    enterpriseController(enterpriseFacade)
    salaryProjectController(salaryProjectFacade)
    transactionController(transactionFacade)
    loanObligationController(loanObligationFacade)
}