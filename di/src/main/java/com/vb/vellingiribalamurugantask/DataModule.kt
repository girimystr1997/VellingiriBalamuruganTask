package com.vb.vellingiribalamurugantask

import android.content.Context
import androidx.room.Room
import com.vb.vellingiribalamurugantask.local.UserHoldingDao
import com.vb.vellingiribalamurugantask.local.UserHoldingDataBase
import com.vb.vellingiribalamurugantask.remote.UserHoldingApi
import com.vb.vellingiribalamurugantask.repository.UserHoldingRepository
import com.vb.vellingiribalamurugantask.usecase.CalculateHoldingValues
import com.vb.vellingiribalamurugantask.usecase.GetUserHolding
import com.vb.vellingiribalamurugantask.usecase.UserHoldingUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        val clientBuilder = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS).build()

        return Retrofit.Builder()
            .baseUrl("https://35dee773a9ec441e9f38d5fc249406ce.api.mockbin.io/")
            .client(clientBuilder)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    @Singleton
    fun provideUseCases(userHoldingRepository: UserHoldingRepository): UserHoldingUseCases {
        return UserHoldingUseCases(GetUserHolding(userHoldingRepository), CalculateHoldingValues())
    }

    @Provides
    @Singleton
    fun provideUserHoldingRepository(
        dao: UserHoldingDao,
        api: UserHoldingApi
    ): UserHoldingRepository {
        return UserHoldingRepositoryImpl(dao, api)
    }

    @Provides
    @Singleton
    fun provideUserHoldingDao(dataBase: UserHoldingDataBase): UserHoldingDao {
        return dataBase.userHoldingDao()
    }

    @Provides
    @Singleton
    fun provideUserHoldingApi(retrofit: Retrofit): UserHoldingApi {
        return retrofit.create(UserHoldingApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserHoldingDatabase(@ApplicationContext context: Context): UserHoldingDataBase {
        return Room.databaseBuilder(context, UserHoldingDataBase::class.java, "user_holding_db")
            .build()
    }

}

