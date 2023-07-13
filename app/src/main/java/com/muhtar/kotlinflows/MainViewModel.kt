package com.muhtar.kotlinflows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    val countDownValue = flow<Int> {
        val startingValue = 5
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
            val count = countDownValue.filter {time ->
                time % 2 == 0
            }
//                .map {time ->
//                    time * time
//                }
//                .onEach { time ->
//                    println(time)
//                }
                .count{
                    it % 2 == 0
                }
            println("The count is $count")

            val reduceResult = countDownValue.reduce{accumulator, value ->
                println("The reduce accumulator $accumulator")
                println("The reduce value $value")
                accumulator + value
            }
            println("The reduce result is $reduceResult")

            val foldResult = countDownValue.fold(initial = 100){accumulator, value ->
                println("The fold accumulator $accumulator")
                println("The fold value $value")
                accumulator + value
            }
            println("The fold result is $foldResult")
        }
    }
}