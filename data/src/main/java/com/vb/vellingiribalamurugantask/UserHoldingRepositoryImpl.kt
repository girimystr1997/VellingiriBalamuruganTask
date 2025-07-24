package com.vb.vellingiribalamurugantask

import com.vb.vellingiribalamurugantask.local.UserHoldingDao
import com.vb.vellingiribalamurugantask.local.UserHoldingEntity
import com.vb.vellingiribalamurugantask.model.UserHolding
import com.vb.vellingiribalamurugantask.remote.UserHoldingApi
import com.vb.vellingiribalamurugantask.remote.model.UserHoldingDto
import com.vb.vellingiribalamurugantask.repository.UserHoldingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class UserHoldingRepositoryImpl(private val dao: UserHoldingDao, private val api: UserHoldingApi) :
    UserHoldingRepository {

    override fun getUserHolding(): Flow<List<UserHolding>> = flow {

        try {
            val remoteData = api.getUserHoldings().data.userHolding.map { it.toDomain() }
            dao.clearUserHolding()
            dao.insertUserHolding(remoteData.map { it.toEntity() })
        } catch (e: Exception) {}
        emitAll(
            dao.getUserHoldings().map { it.map { it.toDomain() } }
        )
    }

    fun UserHoldingEntity.toDomain(): UserHolding = UserHolding(
        avgPrice = avgPrice,
        close = close,
        ltp = ltp,
        quantity = quantity,
        symbol = symbol
    )

    fun UserHolding.toEntity(): UserHoldingEntity = UserHoldingEntity(
        avgPrice = avgPrice,
        close = close,
        ltp = ltp,
        quantity = quantity,
        symbol = symbol
    )

    fun UserHoldingDto.toDomain(): UserHolding = UserHolding(
        avgPrice = avgPrice,
        close = close,
        ltp = ltp,
        quantity = quantity,
        symbol = symbol
    )


}