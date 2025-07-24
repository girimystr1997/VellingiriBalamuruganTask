package com.vb.vellingiribalamurugantask

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

open class BaseViewModel: ViewModel() {
    val showLoader = MutableStateFlow(true)
    val showError = MutableLiveData("")

    val exceptionMessage = ""

    private val _internetAvailable = MutableStateFlow(false)
    val internetAvailable = _internetAvailable.asStateFlow()
}