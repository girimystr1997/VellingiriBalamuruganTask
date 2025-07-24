package com.vb.vellingiribalamurugantask.model

data class UserHoldingData(
    val userHolding: List<UserHolding>,
    val currentValue: Double,
    val totalInvestment: Double,
    val totalPl: Double,
    val todayPl: Double
)