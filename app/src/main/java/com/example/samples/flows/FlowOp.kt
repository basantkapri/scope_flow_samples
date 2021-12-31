package com.example.samples.flows

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
val age = listOf(10, 20, 30, 40, 50, 60, 70, 80, 90)
val user = listOf("User1", "User2", "User3", "User4", "User5", "User6", "User7", "User8", "User9")

fun launchFun() {
    CoroutineScope(Dispatchers.Default).launch {
        Timber.d("sum -> sum data: $this")
    }
}

suspend fun onCompletionTryFinally() = try {
    flowOfData()
        .onCompletion { Timber.d("onCompletionTryFinally -> onCompletion") }
        .collect { Timber.d("onCompletionTryFinally -> collect data: $it") }
} finally {
    Timber.d("onCompletionTryFinally -> finally")
}

suspend fun onCompletionScope() = flowOfData()
    .onCompletion { Timber.d("onCompletionScope -> onCompletion") }
    .collect { Timber.d("onCompletionScope -> collect data: $it") }

suspend fun funFlatMapLatest() {
    val ageFlow  = ageFlow()
    val userFlow = userFlow()

    ageFlow.flatMapLatest { age ->
        userFlow.map { name -> "name -> $name : Age -> $age" }
    }.collect {
        Timber.d("flatMapLatest -> $it")
    }
}

suspend fun funFlatMapMerge() {
    val ageFlow  = ageFlow()
    val userFlow = userFlow()

    ageFlow.flatMapMerge { age ->
        userFlow.map { name -> "name -> $name : Age -> $age" }
    }.collect {
        Timber.d("funFlatMapMerge -> $it")
    }
}

suspend fun funMap() {
    val ageFlow  = ageFlow()
    val userFlow = userFlow()

    ageFlow.map { age ->
        userFlow.map { name -> "name -> $name : Age -> $age" }
    }.collect {
        Timber.d("flatMap -> $it")
    }
}

suspend fun flowOfTemp() = flow {
    list.onEach { count ->
        delay(100)
        emit(count)
    }
}.onStart { "Test" }.first()

suspend fun flowOfData() = flow {
    list.onEach { count ->
        delay(50)
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



suspend fun logData(it: Any) {
    delay(200)
    Timber.d("mapOp -> collect data: $it")
}

fun getThreadName(): String = Thread.currentThread().name