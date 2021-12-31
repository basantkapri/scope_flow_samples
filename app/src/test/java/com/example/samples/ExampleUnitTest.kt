package com.example.samples

import com.example.samples.scopefun.User
import org.junit.Test

import org.junit.Assert.*
import timber.log.Timber

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun printUser() {
        val user = User("Tom", 32, "Developer")
        user.print()
    }
}

data class User(
    val name: String,
    val age: Int,
    val role: String
)

fun User.print() = Timber.d("User -> $this")