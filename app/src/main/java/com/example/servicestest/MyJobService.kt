package com.example.servicestest

import android.app.Service
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyJobService : JobService() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
    }

    override fun onStopJob(p0: JobParameters?): Boolean {
        log("onStopJob")
        return true // true если запланирован на выполнение после уничтожения
    }

    override fun onStartJob(p0: JobParameters?): Boolean {
        log("onStartJob")

        coroutineScope.launch {
            for (i in 0 until 100) {
                delay(1000)
                log("Timer: $i")
            }
            jobFinished(p0, true)
        }

        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
        log("onDestroy")
    }

    private fun log(message: String) {
        Log.d("SERVICE_TAG", "MyService: $message")
    }

}