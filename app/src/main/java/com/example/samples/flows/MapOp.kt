package com.example.samples.flows

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import timber.log.Timber

val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
val age = listOf(10, 20, 30, 40, 50, 60, 70, 80, 90)
val user = listOf("User1", "User2", "User3", "User4", "User5", "User6", "User7", "User8", "User9")

fun sumpOp() {
    // Start a coroutine
    //CoroutineScope(Dispatchers.Default).
    CoroutineScope(Dispatchers.Default).launch {
        val sum = flowOfTemp()
        Timber.d("sum -> sum data: $sum")
    }
}

suspend fun onCompletionNewScope() = try {
    CoroutineScope(Dispatchers.Default).launch {
        flowOfData()
            .onCompletion { Timber.d("onCompletionNewScope -> onCompletion") }
            .collect {
                Timber.d("onCompletionNewScope -> collect data: $it")
            }
    }
} finally {
    Timber.d("onCompletionNewScope -> finally")
}

suspend fun onCompletionSameScope() = try {
    flowOfData()
        .onCompletion { Timber.d("onCompletionSameScope -> onCompletion") }
        .collect {
            Timber.d("onCompletionSameScope -> collect data: $it")
        }
} finally {
    Timber.d("onCompletionSameScope -> finally")
}


fun mapOp() {
    /*CoroutineScope(Dispatchers.Default).launch {
        flowOfData()
            .filter { it < 65 }
            .map { if (it < 50) "String -> $it" else "Greater....$it" }
            .collectLatest {
                delay(200)
                Timber.d("mapOp -> collect data: $it")
            }
    }*/
}

suspend fun flowOfTemp() = flow {
    list.onEach { count ->
        delay(100)
        emit(count)
    }
}.onStart { "Test" }.first()

suspend fun flowOfData() = flow {
    list.onEach { count ->
        delay(500)
        emit(count)
    }
}

suspend fun ageFlow() = flow {
    age.onEach { count ->
        delay(900)
        emit(count)
    }
}

suspend fun userFlow() = flow {
    user.onEach { count ->
        delay(100)
        emit(count)
    }
}

suspend fun flatMap() {
    val ageFlow = ageFlow()
    val userFlow = userFlow()
    ageFlow.flatMapLatest { age ->
        userFlow.map { name ->
            "Age -> $age || name -> $name"
        }
    }.collect {
        Timber.d("flatMap -> $it")
    }
}

suspend fun logData(it: Any) {
    delay(200)
    Timber.d("mapOp -> collect data: $it")
}

fun getThreadName(): String = Thread.currentThread().name