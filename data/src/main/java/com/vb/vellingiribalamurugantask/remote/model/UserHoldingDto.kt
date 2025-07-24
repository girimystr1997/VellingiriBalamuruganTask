package com.vb.vellingiribalamurugantask.remote.model

data class UserHoldingDto(
    val avgPrice: Double,
    val close: Double,
    val ltp: Double,
    val quantity: Int,
    val symbol: String
)