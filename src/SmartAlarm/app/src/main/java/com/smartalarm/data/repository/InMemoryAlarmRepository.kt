package com.smartalarm.data.repository

import com.smartalarm.domain.model.Alarm
import com.smartalarm.domain.repository.AlarmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class InMemoryAlarmRepository : AlarmRepository {

    private val _alarms = MutableStateFlow(
        listOf(
            Alarm(
                hour = 7,
                minute = 30,
                label = "Normal volume",
                isEnabled = true,
                daysOfWeek = setOf(1, 2, 3, 4, 5)
            ),
            Alarm(
                hour = 9,
                minute = 15,
                label = "Weekends",
                isEnabled = false,
                daysOfWeek = setOf(6, 7)
            ),
            Alarm(
                hour = 8,
                minute = 15,
                label = "Lab Morning",
                isEnabled = true,
                daysOfWeek = setOf(1, 3, 5)
            )
        )
    )

    override suspend fun createAlarm(alarm: Alarm) {
        _alarms.update { it + alarm }
    }

    override fun getAllAlarms(): Flow<List<Alarm>> = _alarms.asStateFlow()

    override suspend fun getAlarmById(id: String): Alarm? = _alarms.value.find { it.id == id }

    override suspend fun updateAlarm(alarm: Alarm) {
        _alarms.update { list -> list.map { if (it.id == alarm.id) alarm else it } }
    }

    override suspend fun toggleAlarmState(id: String, isEnabled: Boolean) {
        _alarms.update { list ->
            list.map { if (it.id == id) it.copy(isEnabled = isEnabled) else it }
        }
    }

    override suspend fun deleteAlarm(id: String) {
        _alarms.update { list -> list.filterNot { it.id == id } }
    }
}