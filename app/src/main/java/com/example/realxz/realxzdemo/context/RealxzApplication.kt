package com.example.realxz.realxzdemo.context

import android.app.Application
import android.util.Log
import io.reactivex.plugins.RxJavaPlugins

/**
 * @author real xz
 * @date 2019/5/17
 */
class RealxzApplication : Application() {
    companion object {
        lateinit var instance: RealxzApplication
        val TAG = "RealxzApplication"
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        RxJavaPlugins.setErrorHandler {
            Log.d(TAG, "RealxzApplication ${it?.toString()}")
        }
    }
}