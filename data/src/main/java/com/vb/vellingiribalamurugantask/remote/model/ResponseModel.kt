package com.vb.vellingiribalamurugantask.remote.model

data class ResponseModel(
    val `data`: Data
) {
    data class Data(
        val userHolding: List<UserHoldingDto>
    )
}