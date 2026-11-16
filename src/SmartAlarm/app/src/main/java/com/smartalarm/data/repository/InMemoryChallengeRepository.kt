package com.smartalarm.data.repository

import com.smartalarm.domain.model.Challenge
import com.smartalarm.domain.model.ChallengeType
import com.smartalarm.domain.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class InMemoryChallengeRepository : ChallengeRepository {

    private val _challenges = MutableStateFlow(
        listOf(
            Challenge(title = "Coffee Mug", type = ChallengeType.VLM_OBJECT_SCAN, isActiveInPool = true),
            Challenge(title = "Bathroom QR", type = ChallengeType.OPTICAL_CODE, isActiveInPool = true),
            Challenge(title = "15 Squats", type = ChallengeType.PHYSICAL_MOTION, isActiveInPool = true),
            Challenge(title = "Memory Game", type = ChallengeType.COGNITIVE_PUZZLE, isActiveInPool = false)
        )
    )

    override fun getAllChallenges(): Flow<List<Challenge>> = _challenges.asStateFlow()

    override suspend fun toggleChallengeActive(id: String, isActive: Boolean) {
        _challenges.update { list ->
            list.map { if (it.id == id) it.copy(isActiveInPool = isActive) else it }
        }
    }

    override suspend fun getRandomActiveChallenge(): Challenge? {
        return _challenges.value.filter { it.isActiveInPool }.randomOrNull()
    }
}