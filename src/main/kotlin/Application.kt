package com.example

import com.example.adapters.repositories.*
import com.example.adapters.repositories.loans.CreditRepositoryImpl
import com.example.adapters.repositories.loans.DeferredPaymentRepositoryImpl
import com.example.adapters.repositories.requests.*
import com.example.adapters.repositories.users.ClientRepositoryImpl
import com.example.adapters.repositories.users.EmployeeRepositoryImpl
import com.example.adapters.repositories.users.ForeignClientRepositoryImpl
import com.example.adapters.repositories.users.OutsideSpecialistRepositoryImpl
import com.example.application.facades.*
import com.example.application.facades.loans.CreditFacade
import com.example.application.facades.loans.DeferredPaymentFacade
import com.example.application.facades.requests.*
import com.example.application.facades.users.*
import com.example.application.usecases.*
import com.example.application.usecases.GetSalaryProjectsByBankUseCase
import com.example.application.usecases.base.*
import com.example.application.usecases.loan_obligation.GetLoansByBankUseCase
import com.example.application.usecases.loan_obligation.GetLoansByOwnerUseCase
import com.example.application.usecases.loan_obligation.UpdateLoanUseCase
import com.example.application.usecases.loans.*
import com.example.application.usecases.requests.*
import com.example.application.usecases.users.*
import com.example.domain.RequestStatus
import com.example.domain.TransactionType
import com.example.domain.abstracts.User
import com.example.domain.entities.*
import com.example.domain.entities.loan_obligations.Credit
import com.example.domain.entities.loan_obligations.DeferredPayment
import com.example.domain.entities.requests.*
import com.example.domain.entities.users.*
import com.example.domain.repositories.*
import com.example.domain.repositories.base.CRUDRepository
import com.example.domain.repositories.base.ImmutableRepository
import com.example.domain.repositories.common.LoanRepository
import com.example.domain.repositories.common.RequestRepository
import com.example.domain.repositories.common.UsersRepository
import com.example.usecases.*
import io.ktor.server.application.*
import io.ktor.server.netty.*
import kotlinx.serialization.json.Json

import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.util.*

fun main(args: Array<String>) {
    EngineMain.main(args)
}

val json = Json {
    serializersModule = com.example.serializersModule
}

fun Application.module() {
    startKoin {
        modules(appModule)
    }

    configureSerialization()
    configureDatabases()
    configureRouting()

    val transaction = Transaction(
        senderAccountId = UUID.randomUUID(),
        amount = 5.0,
        transactionType = TransactionType.DEPOSIT,
        bankUBN = UUID.randomUUID(),
        receiverAccountId = UUID.randomUUID(),
        transactionId = UUID.randomUUID(),
    )

    val admin = Admin(
        FIO = "Zgirskaya D D",
        phone = "+375 25 944 23 11",
        email = "zgdashay@gmail.com",
        password = "625100",
        bankUBN = UUID.randomUUID(),
        userId = UUID.randomUUID(),
    )

    val client = Client(
        FIO = "Ivanov I I",
        passportSeries = "KH",
        passportNumber = 62532732,
        phone = "+37525900133",
        email = "ivanov@gmail.com",
        password = "123456789",
        bankUBN = UUID.randomUUID(),
        userId = UUID.randomUUID(),
    )

    val salaryRequest = SalaryProjectRequest(
        bankUBN = UUID.randomUUID(),
        applicantId = UUID.randomUUID(),
        enterpriseId = UUID.randomUUID(),
        requestStatus = RequestStatus.APPROVED,
        requestId = UUID.randomUUID()
    )

    println(json.encodeToString(client))
    println(json.encodeToString(admin))
}

val appModule = module {

    val accountRepositoryQualifier = named("accountRepositoryQualifier")
    //Account
    single<AccountRepository>(accountRepositoryQualifier) { AccountRepositoryImpl() }
    single<CRUDRepository<Account>>(accountRepositoryQualifier) { AccountRepositoryImpl() }
    single(accountRepositoryQualifier) { CreateAccountUseCase(get(accountRepositoryQualifier)) }
    single(accountRepositoryQualifier) { GetAccountUseCase(get(accountRepositoryQualifier)) }
    single(accountRepositoryQualifier) { GetAccountsByOwnerUseCase(get(accountRepositoryQualifier)) }
    single(accountRepositoryQualifier) { GetAccountsByBankUseCase(get(accountRepositoryQualifier)) }
    single(accountRepositoryQualifier) { UpdateAccountUseCase(get(accountRepositoryQualifier)) }
    single(accountRepositoryQualifier) { DeleteAccountUseCase(get(accountRepositoryQualifier)) }
    single {
        AccountFacade(
            get(accountRepositoryQualifier),
            get(accountRepositoryQualifier),
            get(accountRepositoryQualifier),
            get(accountRepositoryQualifier),
            get(accountRepositoryQualifier),
            get(accountRepositoryQualifier),
            )
    }

    //Bank
    val bankRepositoryQualifier = named("bankRepositoryQualifier")

    single<BankRepository>(bankRepositoryQualifier) { BankRepositoryImpl() }
    single<CRUDRepository<Bank>>(bankRepositoryQualifier) { BankRepositoryImpl() }
    single(bankRepositoryQualifier) { CreateBankUseCase(get(bankRepositoryQualifier)) }
    single(bankRepositoryQualifier) { GetBankUseCase(get(bankRepositoryQualifier)) }
    single(bankRepositoryQualifier) { GetAllBanksUseCase(get(bankRepositoryQualifier)) }
    single(bankRepositoryQualifier) { UpdateBankNameUseCase(get(bankRepositoryQualifier)) }
    single(bankRepositoryQualifier) { DeleteBankUseCase(get(bankRepositoryQualifier)) }
    single {
        BankFacade(
            get(bankRepositoryQualifier),
            get(bankRepositoryQualifier),
            get(bankRepositoryQualifier),
            get(bankRepositoryQualifier),
            get(bankRepositoryQualifier)
        )
    }

    //Enterprise
    single<EnterpriseRepository> { EnterpriseRepositoryImpl() }
    single<CRUDRepository<Enterprise>> { EnterpriseRepositoryImpl() }
    single { CreateEnterpriseUseCase(get()) }
    single { GetEnterpriseUseCase(get()) }
    single { GetEnterprisesByBankUseCase(get()) }
    single { UpdateEnterpriseLegalNameUseCase(get()) }
    single { UpdateEnterpriseLegalAdressUseCase(get()) }
    single { DeleteEnterpriseUseCase(get()) }
    single { EnterpriseFacade(get(), get(), get(), get(), get(), get()) }

    //SalaryProject
    single<SalaryProjectRepository> { SalaryProjectRepositoryImpl() }
    single<CRUDRepository<SalaryProject>> { SalaryProjectRepositoryImpl() }
    single { CreateSalaryProjectUseCase(get()) }
    single { GetSalaryProjectUseCase(get()) }
    single { GetSalaryProjectsByBankUseCase(get()) }
    single { UpdateSalaryProjectStatusUseCase(get()) }
    single { DeleteSalaryProjectUseCase(get()) }
    single { SalaryProjectFacade(get(), get(), get(), get(), get()) }

    //Users
    val clientRepositoryQualifier = named("clientRepository")
    val foreignClientRepositoryQualifier = named("foreignClientRepository")
    val outsideSpecialistRepositoryQualifier = named("outsideSpecialistRepository")
    val adminRepositoryQualifier = named("adminRepository")
    val managerRepositoryQualifier = named("managerRepository")
    val operatorRepositoryQualifier = named("operatorRepository")

    //CreateUseCase
    single<CreateUseCase<Client>>(clientRepositoryQualifier) {
        CreateClientUseCase(get(clientRepositoryQualifier))
    }
    single<CreateUseCase<ForeignClient>>(foreignClientRepositoryQualifier) {
        CreateForeignClientUseCase(get(foreignClientRepositoryQualifier))
    }
    single<CreateUseCase<Admin>>(adminRepositoryQualifier) {
        CreateAdminUseCase(get(adminRepositoryQualifier))
    }
    single<CreateUseCase<Manager>>(managerRepositoryQualifier) {
        CreateManagerUseCase(get(managerRepositoryQualifier))
    }
    single<CreateUseCase<Operator>>(operatorRepositoryQualifier) {
        CreateOperatorUseCase(get(operatorRepositoryQualifier))
    }
    single<CreateUseCase<OutsideSpecialist>>(outsideSpecialistRepositoryQualifier) {
        CreateOutsideSpecialistUseCase(get(outsideSpecialistRepositoryQualifier))
    }
    //GetUseCase
    single<GetUseCase<Client>>(clientRepositoryQualifier) {
        GetClientUseCase(get(clientRepositoryQualifier))
    }
    single<GetUseCase<ForeignClient>>(foreignClientRepositoryQualifier) {
        GetForeignClientUseCase(get(foreignClientRepositoryQualifier))
    }
    single<GetUseCase<Admin>>(adminRepositoryQualifier) {
        GetAdminUseCase(get(adminRepositoryQualifier))
    }
    single<GetUseCase<Manager>>(managerRepositoryQualifier) {
        GetManagerUseCase(get(managerRepositoryQualifier))
    }
    single<GetUseCase<Operator>>(operatorRepositoryQualifier) {
        GetOperatorUseCase(get(operatorRepositoryQualifier))
    }
    single<GetUseCase<OutsideSpecialist>>(outsideSpecialistRepositoryQualifier) {
        GetOutsideSpecialistUseCase(get(outsideSpecialistRepositoryQualifier))
    }
    //getByBankUseCase
    single<GetUsersByBankUseCase<Admin>>(adminRepositoryQualifier) { GetAdminsByBankUseCase(get(adminRepositoryQualifier)) }
    single<GetUsersByBankUseCase<Manager>>(managerRepositoryQualifier) { GetManagersByBankUseCase(get(managerRepositoryQualifier)) }
    single<GetUsersByBankUseCase<Operator>>(operatorRepositoryQualifier) { GetOperatorsByBankUseCase(get(operatorRepositoryQualifier)) }
    single<GetUsersByBankUseCase<Client>>(clientRepositoryQualifier) { GetClientsByBankUseCase(get(clientRepositoryQualifier) ) }
    single<GetUsersByBankUseCase<ForeignClient>>(foreignClientRepositoryQualifier) { GetForeignClientsByBankUseCase(get(foreignClientRepositoryQualifier)) }
    single<GetUsersByBankUseCase<OutsideSpecialist>>(outsideSpecialistRepositoryQualifier) { GetOutsideSpecialistsByBankUseCase(get(outsideSpecialistRepositoryQualifier)) }
    //GetByEmailUseCase
    single<GetUserByEmailUseCase<Admin>>(adminRepositoryQualifier) { GetAdminByEmailUseCase(get(adminRepositoryQualifier)) }
    single<GetUserByEmailUseCase<Manager>>(managerRepositoryQualifier) { GetManagerByEmailUseCase(get(managerRepositoryQualifier)) }
    single<GetUserByEmailUseCase<Operator>>(operatorRepositoryQualifier) { GetOperatorByEmailUseCase(get(operatorRepositoryQualifier)) }
    single<GetUserByEmailUseCase<Client>>(clientRepositoryQualifier) { GetClientByEmailUseCase(get(clientRepositoryQualifier) ) }
    single<GetUserByEmailUseCase<ForeignClient>>(foreignClientRepositoryQualifier) { GetForeignClientByEmailUseCase(get(foreignClientRepositoryQualifier)) }
    single<GetUserByEmailUseCase<OutsideSpecialist>>(outsideSpecialistRepositoryQualifier) { GetOutsideSpecialistByEmailUseCase(get(outsideSpecialistRepositoryQualifier)) }
    //UpdateUseCase
    single<UpdateUserEmailUseCase<Client>>(clientRepositoryQualifier) {
        UpdateClientEmailUseCase(get(clientRepositoryQualifier) )
    }
    single<UpdateUserEmailUseCase<ForeignClient>>(foreignClientRepositoryQualifier) {
        UpdateForeignClientEmailUseCase(get(foreignClientRepositoryQualifier) )
    }
    single<UpdateUserEmailUseCase<Admin>>(adminRepositoryQualifier) {
        UpdateAdminEmailUseCase(get(adminRepositoryQualifier) )
    }
    single<UpdateUserEmailUseCase<Manager>>(managerRepositoryQualifier) {
        UpdateManagerEmailUseCase(get(managerRepositoryQualifier) )
    }
    single<UpdateUserEmailUseCase<Operator>>(operatorRepositoryQualifier) {
        UpdateOperatorEmailUseCase(get(operatorRepositoryQualifier) )
    }
    single<UpdateUserEmailUseCase<OutsideSpecialist>>(outsideSpecialistRepositoryQualifier) {
        UpdateOutsideSpecialistEmailUseCase(get(outsideSpecialistRepositoryQualifier) )
    }
    //DeleteUseCase
    single<DeleteUseCase<Client>>(clientRepositoryQualifier) {
        DeleteClientUseCase(get(clientRepositoryQualifier) )
    }
    single<DeleteUseCase<ForeignClient>>(foreignClientRepositoryQualifier) {
        DeleteForeignClientUseCase(get(foreignClientRepositoryQualifier) )
    }
    single<DeleteUseCase<Admin>>(adminRepositoryQualifier) {
        DeleteAdminUseCase(get(adminRepositoryQualifier) )
    }
    single<DeleteUseCase<Manager>>(managerRepositoryQualifier) {
        DeleteManagerUseCase(get(managerRepositoryQualifier) )
    }
    single<DeleteUseCase<Operator>>(operatorRepositoryQualifier) {
        DeleteOperatorUseCase(get(operatorRepositoryQualifier) )
    }
    single<DeleteUseCase<OutsideSpecialist>>(outsideSpecialistRepositoryQualifier) {
        DeleteOutsideSpecialistUseCase(get(outsideSpecialistRepositoryQualifier) )
    }

    //Client
    single<UsersRepository<Client>>(clientRepositoryQualifier) { ClientRepositoryImpl() }
    single<CRUDRepository<Client>>(clientRepositoryQualifier) { ClientRepositoryImpl() }
    single {
        ClientFacade(
            get(clientRepositoryQualifier),
            get(clientRepositoryQualifier),
            get(clientRepositoryQualifier),
            get(clientRepositoryQualifier),
            get(clientRepositoryQualifier),
            get(clientRepositoryQualifier)
        )
    }

    //Foreign client
    single<UsersRepository<ForeignClient>>(foreignClientRepositoryQualifier) {
        ForeignClientRepositoryImpl()
    }
    single<CRUDRepository<ForeignClient>>(foreignClientRepositoryQualifier) {
        ForeignClientRepositoryImpl()
    }
    single {
        ForeignClientFacade(
            get(foreignClientRepositoryQualifier),
            get(foreignClientRepositoryQualifier),
            get(foreignClientRepositoryQualifier),
            get(foreignClientRepositoryQualifier),
            get(foreignClientRepositoryQualifier),
            get(foreignClientRepositoryQualifier)
        )
    }

    //Outside specialist
    single<UsersRepository<OutsideSpecialist>>(outsideSpecialistRepositoryQualifier) {
        OutsideSpecialistRepositoryImpl()
    }
    single<CRUDRepository<OutsideSpecialist>>(outsideSpecialistRepositoryQualifier) {
        OutsideSpecialistRepositoryImpl()
    }
    single {
        OutsideSpecialistFacade(
            get(outsideSpecialistRepositoryQualifier),
            get(outsideSpecialistRepositoryQualifier),
            get(outsideSpecialistRepositoryQualifier),
            get(outsideSpecialistRepositoryQualifier),
            get(outsideSpecialistRepositoryQualifier),
            get(outsideSpecialistRepositoryQualifier)
        )
    }

    //Employees
    single<UsersRepository<User>>(adminRepositoryQualifier) { EmployeeRepositoryImpl() }
    single<UsersRepository<User>>(managerRepositoryQualifier) { EmployeeRepositoryImpl() }
    single<UsersRepository<User>>(operatorRepositoryQualifier) { EmployeeRepositoryImpl() }

    //Admin
    single {
        AdminFacade(
            get(adminRepositoryQualifier),
            get(adminRepositoryQualifier),
            get(adminRepositoryQualifier),
            get(adminRepositoryQualifier),
            get(adminRepositoryQualifier),
            get(adminRepositoryQualifier),
        )
    }

    //Manager
    single {
        ManagerFacade(
            get(managerRepositoryQualifier),
            get(managerRepositoryQualifier),
            get(managerRepositoryQualifier),
            get(managerRepositoryQualifier),
            get(managerRepositoryQualifier),
            get(managerRepositoryQualifier)
        )
    }

    //Operator
    single {
        OperatorFacade(
            get(operatorRepositoryQualifier),
            get(operatorRepositoryQualifier),
            get(operatorRepositoryQualifier),
            get(operatorRepositoryQualifier),
            get(operatorRepositoryQualifier),
            get(operatorRepositoryQualifier)
        )
    }

    //Loans
    single<UpdateLoanUseCase<Credit>> { UpdateCreditUseCase(get()) }
    single<UpdateLoanUseCase<DeferredPayment>> { UpdateDeferredPaymentUseCase(get()) }
    single<GetLoansByOwnerUseCase<Credit>> { GetCreditsByOwnerUseCase(get()) }
    single<GetLoansByOwnerUseCase<DeferredPayment>> { GetDeferredPaymentsByOwnerUseCase(get()) }
    single<GetLoansByBankUseCase<Credit>> { GetCreditsByBankUseCase(get()) }
    single<GetLoansByBankUseCase<DeferredPayment>> { GetDeferredPaymentsByBankUseCase(get()) }

    //Credits
    single<LoanRepository<Credit>> { CreditRepositoryImpl() }
    single<CRUDRepository<Credit>> { CreditRepositoryImpl() }

    single { CreateCreditUseCase(get()) }
    single { GetCreditUseCase(get()) }
    single { DeleteCreditUseCase(get()) }

    single { CreditFacade(get(), get(), get(), get(), get(), get()) }

    //DeferredPayments
    single<LoanRepository<DeferredPayment>> { DeferredPaymentRepositoryImpl() }
    single<CRUDRepository<DeferredPayment>> { DeferredPaymentRepositoryImpl() }

    single { CreateDeferredPaymentUseCase(get()) }
    single { GetDeferredPaymentUseCase(get()) }
    single { DeleteDeferredPaymentUseCase(get()) }

    single { DeferredPaymentFacade(get(), get(), get(), get(), get(), get()) }

    //Transactions
    val transactionRepositoryQualifier = named("transactionRepository")

    single<TransactionRepository>(transactionRepositoryQualifier) { TransactionRepositoryImpl() }
    single<ImmutableRepository<Transaction>>(transactionRepositoryQualifier) { TransactionRepositoryImpl() }

    single(transactionRepositoryQualifier) { CreateTransactionUseCase(get(transactionRepositoryQualifier)) }
    single(transactionRepositoryQualifier) { GetTransactionUseCase(get(transactionRepositoryQualifier)) }
    single(transactionRepositoryQualifier) { GetTransactionsByAccountUseCase(get(transactionRepositoryQualifier)) }
    single(transactionRepositoryQualifier) { GetTransactionsByBankUseCase(get(transactionRepositoryQualifier)) }
    single(transactionRepositoryQualifier) { DeleteTransactionUseCase(get(transactionRepositoryQualifier)) }

    single { TransactionFacade(
        get(transactionRepositoryQualifier),
        get(transactionRepositoryQualifier),
        get(transactionRepositoryQualifier),
        get(transactionRepositoryQualifier),
        get(transactionRepositoryQualifier)
    ) }

    //Requests
    val clientRegistrationRequestRepositoryQualifier = named("clientRegistrationRequestRepository")
    val creditRequestRepositoryQualifier = named("creditRequestRepository")
    val deferredPaymentRequestRepositoryQualifier = named("deferredPaymentRequestRepository")
    val salaryProjectRequestRepositoryQualifier = named("salaryProjectRequestRepository")
    val transactionRequestRepositoryQualifier = named("transactionRequestRepository")

    //ClientRegistrationRequest
    single<RequestRepository<ClientRegistrationRequest>>(clientRegistrationRequestRepositoryQualifier) {
        ClientRegistrationRequestRepositoryImpl()
    }

    single<CreateUseCase<ClientRegistrationRequest>>(clientRegistrationRequestRepositoryQualifier) {
        CreateClientRegistrationRequestUseCase(get(clientRegistrationRequestRepositoryQualifier))
    }
    single<GetUseCase<ClientRegistrationRequest>>(clientRegistrationRequestRepositoryQualifier) {
        GetClientRegistrationRequestUseCase(get(clientRegistrationRequestRepositoryQualifier))
    }
    single<GetRequestsByBankUseCase<ClientRegistrationRequest>>(clientRegistrationRequestRepositoryQualifier) {
        GetClientRegistrationRequestsByBankUseCase(get(clientRegistrationRequestRepositoryQualifier))
    }
    single<UpdateRequestStatusUseCase<ClientRegistrationRequest>>(clientRegistrationRequestRepositoryQualifier) {
        UpdateClientRegistrationRequestStatusUseCase(get(clientRegistrationRequestRepositoryQualifier))
    }
    single<DeleteUseCase<ClientRegistrationRequest>>(clientRegistrationRequestRepositoryQualifier) {
        DeleteClientRegistrationRequestUseCase(get(clientRegistrationRequestRepositoryQualifier))
    }

    single {
        ClientRegistrationRequestFacade(
            get(clientRegistrationRequestRepositoryQualifier),
            get(clientRegistrationRequestRepositoryQualifier),
            get(clientRegistrationRequestRepositoryQualifier),
            get(clientRegistrationRequestRepositoryQualifier),
            get(clientRegistrationRequestRepositoryQualifier)
        )
    }

    //CreditRequest
    single<RequestRepository<CreditRequest>>(creditRequestRepositoryQualifier) {
        CreditRequestRepositoryImpl()
    }

    single<CreateUseCase<CreditRequest>>(creditRequestRepositoryQualifier) {
        CreateCreditRequestUseCase(get(creditRequestRepositoryQualifier))
    }
    single<GetUseCase<CreditRequest>>(creditRequestRepositoryQualifier) {
        GetCreditRequestUseCase(get(creditRequestRepositoryQualifier))
    }
    single<GetRequestsByBankUseCase<CreditRequest>>(creditRequestRepositoryQualifier) {
        GetCreditRequestsByBankUseCase(get(creditRequestRepositoryQualifier))
    }
    single<UpdateRequestStatusUseCase<CreditRequest>>(creditRequestRepositoryQualifier) {
        UpdateCreditRequestStatusUseCase(get(creditRequestRepositoryQualifier))
    }
    single<DeleteUseCase<CreditRequest>>(creditRequestRepositoryQualifier) {
        DeleteCreditRequestUseCase(get(creditRequestRepositoryQualifier))
    }

    single {
        CreditRequestFacade(
            get(creditRequestRepositoryQualifier),
            get(creditRequestRepositoryQualifier),
            get(creditRequestRepositoryQualifier),
            get(creditRequestRepositoryQualifier),
            get(creditRequestRepositoryQualifier)
        )
    }

    //DeferredPaymentRequest
    single<RequestRepository<DeferredPaymentRequest>>(deferredPaymentRequestRepositoryQualifier) {
        DeferredPaymentRequestRepositoryImpl()
    }

    single<CreateUseCase<DeferredPaymentRequest>>(deferredPaymentRequestRepositoryQualifier) {
        CreateDeferredPaymentRequestUseCase(get(deferredPaymentRequestRepositoryQualifier))
    }
    single<GetUseCase<DeferredPaymentRequest>>(deferredPaymentRequestRepositoryQualifier) {
        GetDeferredPaymentRequestUseCase(get(deferredPaymentRequestRepositoryQualifier))
    }
    single<GetRequestsByBankUseCase<DeferredPaymentRequest>>(deferredPaymentRequestRepositoryQualifier) {
        GetDeferredPaymentRequestsByBankUseCase(get(deferredPaymentRequestRepositoryQualifier))
    }
    single<UpdateRequestStatusUseCase<DeferredPaymentRequest>>(deferredPaymentRequestRepositoryQualifier) {
        UpdateDeferredPaymentRequestStatusUseCase(get(deferredPaymentRequestRepositoryQualifier))
    }
    single<DeleteUseCase<DeferredPaymentRequest>>(deferredPaymentRequestRepositoryQualifier) {
        DeleteDeferredPaymentRequestUseCase(get(deferredPaymentRequestRepositoryQualifier))
    }

    single {
        DeferredPaymentRequestFacade(
            get(deferredPaymentRequestRepositoryQualifier),
            get(deferredPaymentRequestRepositoryQualifier),
            get(deferredPaymentRequestRepositoryQualifier),
            get(deferredPaymentRequestRepositoryQualifier),
            get(deferredPaymentRequestRepositoryQualifier)
        )
    }

    //SalaryProjectRequest
    single<RequestRepository<SalaryProjectRequest>>(salaryProjectRequestRepositoryQualifier) {
        SalaryProjectRequestRepositoryImpl()
    }

    single<CreateUseCase<SalaryProjectRequest>>(salaryProjectRequestRepositoryQualifier) {
        CreateSalaryProjectRequestUseCase(get(salaryProjectRequestRepositoryQualifier))
    }
    single<GetUseCase<SalaryProjectRequest>>(salaryProjectRequestRepositoryQualifier) {
        GetSalaryProjectRequestUseCase(get(salaryProjectRequestRepositoryQualifier))
    }
    single<GetRequestsByBankUseCase<SalaryProjectRequest>>(salaryProjectRequestRepositoryQualifier) {
        GetSalaryProjectRequestsByBankUseCase(get(salaryProjectRequestRepositoryQualifier))
    }
    single<UpdateRequestStatusUseCase<SalaryProjectRequest>>(salaryProjectRequestRepositoryQualifier) {
        UpdateSalaryProjectRequestUseCase(get(salaryProjectRequestRepositoryQualifier))
    }
    single<DeleteUseCase<SalaryProjectRequest>>(salaryProjectRequestRepositoryQualifier) {
        DeleteSalaryProjectRequestUseCase(get(salaryProjectRequestRepositoryQualifier))
    }

    single {
        SalaryProjectRequestFacade(
            get(salaryProjectRequestRepositoryQualifier),
            get(salaryProjectRequestRepositoryQualifier),
            get(salaryProjectRequestRepositoryQualifier),
            get(salaryProjectRequestRepositoryQualifier),
            get(salaryProjectRequestRepositoryQualifier)
        )
    }

    //TransactionRequest
    single<RequestRepository<TransactionRequest>>(transactionRequestRepositoryQualifier) { TransactionRequestRepositoryImpl() }

    single<CreateUseCase<TransactionRequest>>(transactionRequestRepositoryQualifier) {
        CreateTransactionRequestUseCase(get(transactionRequestRepositoryQualifier))
    }
    single<GetUseCase<TransactionRequest>>(transactionRequestRepositoryQualifier) {
        GetTransactionRequestUseCase(get(transactionRequestRepositoryQualifier))
    }
    single<GetRequestsByBankUseCase<TransactionRequest>>(transactionRequestRepositoryQualifier) {
        GetTransactionRequestsByBankUseCase(get(transactionRequestRepositoryQualifier))
    }
    single<UpdateRequestStatusUseCase<TransactionRequest>>(transactionRequestRepositoryQualifier) {
        UpdateTransactionRequestStatusUseCase(get(transactionRequestRepositoryQualifier))
    }
    single<DeleteUseCase<TransactionRequest>>(transactionRequestRepositoryQualifier) {
        DeleteTransactionRequestUseCase(get(transactionRequestRepositoryQualifier))
    }

    single { TransactionRequestFacade(
        get(transactionRequestRepositoryQualifier),
        get(transactionRequestRepositoryQualifier),
        get(transactionRequestRepositoryQualifier),
        get(transactionRequestRepositoryQualifier),
        get(transactionRequestRepositoryQualifier)
    ) }
}