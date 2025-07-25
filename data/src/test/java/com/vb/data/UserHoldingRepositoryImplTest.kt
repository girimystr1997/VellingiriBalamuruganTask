package com.vb.data

import com.vb.vellingiribalamurugantask.UserHoldingRepositoryImpl
import com.vb.vellingiribalamurugantask.local.UserHoldingDao
import com.vb.vellingiribalamurugantask.local.UserHoldingEntity
import com.vb.vellingiribalamurugantask.model.UserHolding
import com.vb.vellingiribalamurugantask.remote.UserHoldingApi
import com.vb.vellingiribalamurugantask.remote.model.ResponseModel
import com.vb.vellingiribalamurugantask.remote.model.UserHoldingDto
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.check
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class UserHoldingRepositoryImplTest {
    private lateinit var repository: UserHoldingRepositoryImpl
    private val dao: UserHoldingDao = mock()
    private val api: UserHoldingApi = mock()
    private val sampleDto = UserHoldingDto(
        avgPrice = 100.0,
        close = 110.0,
        ltp = 115.0,
        quantity = 5,
        symbol = "ABC"
    )
    private val sampleEntity = UserHoldingEntity(
        avgPrice = 100.0,
        close = 110.0,
        ltp = 115.0,
        quantity = 5,
        symbol = "ABC"
    )
    private val sampleDomain = UserHolding(
        avgPrice = 100.0,
        close = 110.0,
        ltp = 115.0,
        quantity = 5,
        symbol = "ABC"
    )

    @Before
    fun setUp() {
        repository = UserHoldingRepositoryImpl(dao, api)
    }

    /*
    * To Make this test case passed make api to interface non suspend
    * */
    @Test
    fun `getUserHolding fetch from API and store in DB`() = runTest {



        whenever(api.getUserHoldings()).thenReturn(
            ResponseModel(data = ResponseModel.Data(listOf(sampleDto)))
        )

        whenever(dao.getUserHoldings()).thenReturn(flowOf(listOf(sampleEntity)))

        val result = repository.getUserHolding().first()

        verify(api).getUserHoldings()
        verify(dao).clearUserHolding()
        verify(dao).insertUserHolding(check { assert(it.first().symbol == "ABC") })
        assert(result == listOf(sampleDomain))
    }

    @Test
    fun `getUserHolding - API failure back to DB`() = runTest {

        whenever(api.getUserHoldings()).thenThrow(RuntimeException("API failed"))
        whenever(dao.getUserHoldings()).thenReturn(flowOf(listOf(sampleEntity)))

        val result = repository.getUserHolding().first()
        verify(api).getUserHoldings()
        verify(dao, never()).clearUserHolding()
        verify(dao, never()).insertUserHolding(any())
        assert(result == listOf(sampleDomain))
    }
}
