package com.muhtar.kotlinflows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    val countDownValue = flow<Int> {
        val startingValue = 10
        var currentValue = startingValue
        emit(startingValue)
        while (currentValue > 0) {
            delay(1000L)
            currentValue--
            emit(currentValue)
        }
    }

    init {
        collectFlow()
    }

    private fun collectFlow() {
        viewModelScope.launch {
            countDownValue.filter {time ->
                time % 2 == 0
            }
                .map {time ->
                    time * time
                }
                .onEach { time ->
                    println(time)
                }
                .collect{ time ->
                delay(1500L)
                println("The current time is $time")
            }
        }
    }
}