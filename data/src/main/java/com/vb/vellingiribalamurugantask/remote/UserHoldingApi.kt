package com.vb.vellingiribalamurugantask.remote

import com.vb.vellingiribalamurugantask.remote.model.ResponseModel
import retrofit2.http.GET

interface UserHoldingApi {
    @GET("/")
    suspend fun getUserHoldings(): ResponseModel
}