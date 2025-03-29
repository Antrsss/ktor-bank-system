package com.example.domain.abstracts

import com.example.domain.Role
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
abstract class User (
    open val FIO: String,
    open val phone: String,
    open var email: String,
    open val password: String,
    @Contextual open val bankUBN: UUID,
    @Contextual open val userId: UUID,
    open val role: Role
)