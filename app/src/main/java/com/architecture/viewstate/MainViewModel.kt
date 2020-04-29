package com.architecture.viewstate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainViewModel : ViewModel() {

    private val _state: MutableLiveData<ViewState> = MutableLiveData(ViewState.DefaultState(0, 0))

    val state: LiveData<ViewState>
        get() = _state

    private var counter: Int = 0

    fun increaseCounter() {
        _state.value = ViewState.DefaultState(++counter, 0)
    }

    fun decreaseCounter() {
        if (counter == 0) {
            return
        }
        _state.value = ViewState.DefaultState(--counter, 0)
    }

    // Don't worry about the Coroutines logic! Focus on the LiveData updates.
    fun startCountdown() =
        viewModelScope.launch(Dispatchers.Default) {
            _state.postValue(ViewState.LoadingState)
            randomDelay()

            for (i in counter downTo 0) {
                _state.postValue(ViewState.CountingState(counter = counter, countdown = i))
                delay(1000)
            }
            _state.postValue(ViewState.DefaultState(counter, 0))
        }

    private suspend fun randomDelay() {
        val duration = 1 + Random.nextInt(4)
        for (i in 0..duration) {
            delay(1000)
        }
    }
}
