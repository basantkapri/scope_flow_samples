package com.example.samples.scopefun

import timber.log.Timber

//data class User(val name: String, val age: Int, val role: String)
data class User(var name: String, var age: Int, var role: String)

fun User.print()   = Timber.d("User -> $this")
fun Any.print() = Timber.d("User -> $this")