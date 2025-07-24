package com.vb.vellingiribalamurugantask.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user_holding_table")
data class UserHoldingEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val avgPrice: Double,
    val close: Double,
    val ltp: Double,
    val quantity: Int,
    val symbol: String
)