package com.smartalarm.domain.repository

import com.smartalarm.domain.model.Challenge
import kotlinx.coroutines.flow.Flow

interface ChallengeRepository {
    fun getAllChallenges(): Flow<List<Challenge>>
    suspend fun toggleChallengeActive(id: String, isActive: Boolean)
    suspend fun getRandomActiveChallenge(): Challenge?
}