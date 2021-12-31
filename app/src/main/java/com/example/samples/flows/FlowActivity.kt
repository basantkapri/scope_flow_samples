package com.example.samples.flows

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.samples.R
import kotlinx.coroutines.launch
import kotlin.time.ExperimentalTime

class FlowActivity : AppCompatActivity() {

    @ExperimentalTime
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //launchFun()

        //lifecycleScope.launch { onCompletionTryFinally() }

        //lifecycleScope.launch { onCompletionScope() }

        lifecycleScope.launch { funSlatMapLatest() }



    }
}