package com.example.domain.repositories

import com.example.domain.ActionLog
import java.util.*

interface ActionLogRepository {
    fun saveLog(log: ActionLog)
    fun findLogById(logId: UUID): ActionLog?
    fun findLogsByClientId(clientId: UUID): List<ActionLog>
    fun deleteLog(logId: UUID)
}