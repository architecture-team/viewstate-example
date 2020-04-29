package com.architecture.viewstate

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlin.random.Random

class MainViewModel : ViewModel() {

    var counter: Int = 0
    val state: MutableLiveData<ViewState> = MutableLiveData(ViewState.DefaultState(0, 0))
    var job: Job? = null

    fun increaseCounter() {
        state.value = ViewState.DefaultState(++counter, 0)
    }

    fun decreaseCounter() {
        state.value = ViewState.DefaultState(--counter, 0)
    }

    fun startCountdown() {
        if (job == null) {
            job = viewModelScope.launch(Dispatchers.Default) {
                state.postValue(ViewState.LoadingState)
                randomDelay()

                for(i in counter downTo 0) {
                    state.postValue(ViewState.CountingState(counter = counter, countdown = i))
                    delay(1000)
                }
                state.postValue(ViewState.DefaultState(counter, 0))
                job = null
            }
        }
    }

    fun stopCountdown() {
        job?.cancel()
        job = null
        state.value = ViewState.DefaultState(counter, 0)
    }

    suspend fun randomDelay() {
        val duration = 1 + Random.nextInt(4)
        for (i in 0..duration) { delay(1000) }
    }
}