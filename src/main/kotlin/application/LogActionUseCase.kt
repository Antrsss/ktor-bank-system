package com.example.usecases

import com.example.domain.ActionLog
import com.example.domain.entities.users.Client
import com.example.domain.repositories.ActionLogRepository

class LogActionUseCase(
    private val actionLogRepository: ActionLogRepository
) {
    fun execute(client: Client, action: String) {
        val log = ActionLog(client, action)
        actionLogRepository.saveLog(log)
    }
}