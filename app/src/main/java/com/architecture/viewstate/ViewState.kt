package com.architecture.viewstate

sealed class ViewState {

    internal class DefaultState(
        val counter: Int,
        val countdown: Int
    ) : ViewState()

    internal object LoadingState : ViewState()

    internal class CountingState(
        val counter: Int,
        val countdown: Int
    ) : ViewState()

}