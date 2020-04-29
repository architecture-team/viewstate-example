package com.architecture.viewstate

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        startComponents()
    }

    private fun startComponents() {
        minus_btn.setOnClickListener { viewModel.decreaseCounter() }
        plus_btn.setOnClickListener { viewModel.increaseCounter() }
        start_btn.setOnClickListener { viewModel.startCountdown() }

        viewModel.state.observe(this, Observer { state ->
            when (state) {
                is ViewState.DefaultState -> renderDefaultState(state)
                // Handle other states
            }
        })
    }

    private fun renderDefaultState(state: ViewState.DefaultState) {
        counter_tv.text = state.counter.toString()
        countdown_tv.text = state.countdown.toString()
        plus_btn.isEnabled = true
        minus_btn.isEnabled = true
        start_btn.isEnabled = true
        start_btn.visibility = View.VISIBLE
        progress.visibility = View.GONE
        countdown_tv.visibility = View.VISIBLE
    }
}
