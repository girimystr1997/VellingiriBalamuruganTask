package com.vb.vellingiribalamurugantask.viewmodel

import androidx.lifecycle.viewModelScope
import com.vb.vellingiribalamurugantask.BaseViewModel
import com.vb.vellingiribalamurugantask.model.UserHoldingData
import com.vb.vellingiribalamurugantask.usecase.UserHoldingUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val userHoldingUseCases: UserHoldingUseCases) :
    BaseViewModel() {


    var userHoldingData = userHoldingUseCases.getUserHolding()
        .map {
            userHoldingUseCases.calculateHoldingValues(it)
        }.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed((5000)), UserHoldingData(
                userHolding = emptyList(),
                currentValue = 0.0,
                totalInvestment = 0.0,
                todayPl = 0.0,
                totalPl = 0.0
            )
        )

}