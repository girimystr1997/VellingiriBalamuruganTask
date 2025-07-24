package com.vb.vellingiribalamurugantask.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [UserHoldingEntity::class], version = 1)
abstract class UserHoldingDataBase: RoomDatabase() {
    abstract fun userHoldingDao(): UserHoldingDao
}