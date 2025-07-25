package com.vb.vellingiribalamurugantask.usecase

import com.vb.vellingiribalamurugantask.model.UserHolding
import com.vb.vellingiribalamurugantask.model.UserHoldingData
import com.vb.vellingiribalamurugantask.repository.UserHoldingRepository


data class UserHoldingUseCases(
    val getUserHolding: GetUserHolding,
    val calculateHoldingValues: CalculateHoldingValues
)

class GetUserHolding(private val userHoldingRepository: UserHoldingRepository) {
    operator fun invoke() = userHoldingRepository.getUserHolding()
}

class CalculateHoldingValues {
    operator fun invoke(userHolding: List<UserHolding>): UserHoldingData {
        if (userHolding.isNotEmpty()) {
            val currentValue = userHolding.sumOf { it.ltp * it.quantity }
            val totalInvestment = userHolding.sumOf { it.avgPrice * it.quantity }
            val totalPL = currentValue - totalInvestment
            val pl = userHolding.sumOf { (it.ltp - it.close) * it.quantity }

            return UserHoldingData(
                userHolding = userHolding,
                currentValue = currentValue,
                totalInvestment = totalInvestment,
                todayPl = pl,
                totalPl =  totalPL
            )
        } else {
            return UserHoldingData(
                userHolding = emptyList(),
                currentValue = 0.0,
                totalInvestment = 0.0,
                todayPl = 0.0,
                totalPl = 0.0
            )
        }
    }
}
