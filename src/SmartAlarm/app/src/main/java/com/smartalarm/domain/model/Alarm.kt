package com.smartalarm.domain.model

import java.util.UUID

data class Alarm(
    val id: String = UUID.randomUUID().toString(),
    val hour: Int,
    val minute: Int,
    val label: String = "Smart Alarm",
    val isEnabled: Boolean = true,
    val daysOfWeek: Set<Int> = emptySet(), // 1 = Mon ... 7 = Sun (Empty = One-time)
    val isSpontaneousRandom: Boolean = true
)