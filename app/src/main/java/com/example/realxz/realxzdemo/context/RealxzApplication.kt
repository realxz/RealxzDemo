package com.example.realxz.realxzdemo.context

import android.app.Application

/**
 * @author real xz
 * @date 2019/5/17
 */
class RealxzApplication : Application() {
    companion object {
        lateinit var instance: RealxzApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
//        RxJavaPlugins.setErrorHandler {
//            Log.d(MainPresenter.TAG, "RealxzApplication ${it?.toString()}")
//        }
    }
}