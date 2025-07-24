package com.vb.vellingiribalamurugantask.viewmodel

import app.cash.turbine.test
import com.vb.vellingiribalamurugantask.model.UserHolding
import com.vb.vellingiribalamurugantask.model.UserHoldingData
import com.vb.vellingiribalamurugantask.usecase.UserHoldingUseCases
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    private lateinit var viewModel: MainViewModel
    private val userHoldingUseCases: UserHoldingUseCases = mockk()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `userHoldingData for calculated`() = runTest {
        val fakeHoldings = listOf(
            UserHolding(
                avgPrice = 10.00,
                close = 100.00,
                ltp = 110.00,
                quantity = 1,
                symbol = "ABC"
            ),
            UserHolding(
                avgPrice = 20.00,
                close = 200.00,
                ltp = 210.00,
                quantity = 2,
                symbol = "XYZ"
            )
        )

        val expectedData = UserHoldingData(
            userHolding = fakeHoldings,
            currentValue = (110.00 * 1) + (210.00 * 2),
            totalInvestment = (10.00 * 1) + (20.00 * 2),
            totalPl = ((110.00 * 1) + (210.00 * 2)) - ((10.00 * 1) + (20.00 * 2)),
            todayPl = ((100.00 - 110.00) * (1)) + ((200.00 - 210.00) * (2)),
        )
        coEvery { userHoldingUseCases.getUserHolding() } returns flowOf(fakeHoldings)
        coEvery { userHoldingUseCases.calculateHoldingValues(fakeHoldings) } returns expectedData
        viewModel = MainViewModel(userHoldingUseCases)

        viewModel.userHoldingData.test {
            skipItems(1)
            val item = awaitItem()
            assertEquals(expectedData.currentValue, item.currentValue, 0.1)
            assertEquals(expectedData.totalInvestment, item.totalInvestment, 0.1)
            assertEquals(expectedData.userHolding, item.userHolding)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `userHoldingData for empty`() = runTest {
        val fakeHoldings = emptyList<UserHolding>()

        val expectedData = UserHoldingData(
            userHolding = fakeHoldings,
            currentValue = 0.0,
            totalInvestment = 0.0,
            totalPl = 0.0,
            todayPl = 0.0,
        )
        coEvery { userHoldingUseCases.getUserHolding() } returns flowOf(fakeHoldings)
        coEvery { userHoldingUseCases.calculateHoldingValues(fakeHoldings) } returns expectedData
        viewModel = MainViewModel(userHoldingUseCases)

        viewModel.userHoldingData.test {
            val item = awaitItem()
            assertEquals(expectedData.currentValue, item.currentValue, 0.1)
            assertEquals(expectedData.totalInvestment, item.totalInvestment, 0.1)
            assertEquals(expectedData.userHolding, item.userHolding)
            cancelAndIgnoreRemainingEvents()
        }
    }


}