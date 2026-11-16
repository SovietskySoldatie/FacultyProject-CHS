package com.smartalarm.domain.repository

import com.smartalarm.domain.model.Alarm
import kotlinx.coroutines.flow.Flow

interface AlarmRepository {
    suspend fun createAlarm(alarm: Alarm)
    fun getAllAlarms(): Flow<List<Alarm>>
    suspend fun getAlarmById(id: String): Alarm?
    suspend fun updateAlarm(alarm: Alarm)
    suspend fun toggleAlarmState(id: String, isEnabled: Boolean)
    suspend fun deleteAlarm(id: String)
}