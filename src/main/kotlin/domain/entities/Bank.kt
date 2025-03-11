package com.example.domain.entities

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.*


@Serializable
data class Bank (
    val bankName: String,
    @Contextual val bankUBN: UUID = UUID.randomUUID()
)