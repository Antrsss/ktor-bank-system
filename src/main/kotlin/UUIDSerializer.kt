package com.example

import com.example.domain.abstracts.Loan
import com.example.domain.abstracts.Request
import com.example.domain.abstracts.User
import com.example.domain.entities.loan_obligations.Credit
import com.example.domain.entities.loan_obligations.DeferredPayment
import com.example.domain.entities.requests.*
import com.example.domain.entities.users.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import java.util.*

val serializersModule = SerializersModule {
    polymorphic(Loan::class) {
        subclass(Credit::class)
        subclass(DeferredPayment::class)
    }
    polymorphic(Request::class) {
        subclass(ClientRegistrationRequest::class)
        subclass(CreditRequest::class)
        subclass(DeferredPaymentRequest::class)
        subclass(SalaryProjectRequest::class)
        subclass(TransactionRequest::class)
    }
    polymorphic(User::class) {
        subclass(Client::class)
        subclass(ForeignClient::class)
        subclass(Admin::class)
        subclass(Manager::class)
        subclass(Operator::class)
        subclass(OutsideSpecialist::class)
    }
    contextual(UUIDSerializer)
}

object UUIDSerializer : KSerializer<UUID> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("UUID", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: UUID) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): UUID {
        return UUID.fromString(decoder.decodeString())
    }
}