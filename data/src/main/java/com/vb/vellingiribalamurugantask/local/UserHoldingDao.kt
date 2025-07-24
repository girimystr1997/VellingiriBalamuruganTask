package com.vb.vellingiribalamurugantask.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserHoldingDao {

    @Query("select * from user_holding_table")
    fun getUserHoldings(): Flow<List<UserHoldingEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserHolding(userHolding: List<UserHoldingEntity>)

    @Query("delete from user_holding_table")
    suspend fun clearUserHolding()

}