package com.smartalarm.domain.model

import java.util.UUID

enum class ChallengeType(val displayName: String) {
    VLM_OBJECT_SCAN("Scan"),
    OPTICAL_CODE("Code"),
    PHYSICAL_MOTION("Motion"),
    COGNITIVE_PUZZLE("Brain")
}

data class Challenge(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val type: ChallengeType,
    val isActiveInPool: Boolean = true
)