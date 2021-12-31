package com.example.samples.scopefun

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.samples.R
import kotlin.time.ExperimentalTime

class ScopeActivity : AppCompatActivity() {

    @ExperimentalTime
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val user1 = User("user1", 35, "Software Eng-I...")

        /************************  !! -> Non null assertion operator   ******************/
        /************************  ? null safety operator              ******************/
        /************************  with, let, apply, also, run, copy   ******************/

        /************************ with : Non Nullable************************/
        var new1 = with(user1) {
            name = "new1"
            age += 30
            age
            //returns lambda result(means you can return anything)
        }

        user1.print()
        new1.print()

        /************************ let : Nullable or Non Nullable************************/
        val user10= User("user10", 35, "Software Eng-I...")
        var new10 = user10.let {
            it.name = "new10"
            it.age += 30
            it.age
            //returns lambda result(means you can return anything)
        }

        user10.print()
        new10.print()

        /************************ run : combine (with + let)************************/
        val user11= User("user11", 35, "Software Eng-I...")
        var new11 = user11.run {
            name = "new11"
            age += 30
            age + 20
            //returns lambda result(means you can return anything)
        }

        user11.print()
        new11.print()

        /************************ apply : to create/modify an object************************/
        val user22 = User("user22", 35, "Software Eng-II...")
        var new22 = user22.apply {
            name = "new22"
            age = 1
            age
            //returns context (this always)
        }

        user22.print()
        new22.print()

        /************************ also : modify/perform action(calculation) on an object************************/
        val user33 = User("user33", 35, "Software Eng-II...")
        var new33 = user33.also {
            it.name = "new33"
            it.age = 1
            10
            //returns context (this always)
        }

        user33.print()
        new33.print()

        /************************ Happy coding...************************/
    }
}