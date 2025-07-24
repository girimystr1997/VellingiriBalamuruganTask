package com.vb.vellingiribalamurugantask.repository

import com.vb.vellingiribalamurugantask.model.UserHolding
import kotlinx.coroutines.flow.Flow

interface UserHoldingRepository {
    fun getUserHolding(): Flow<List<UserHolding>>
}