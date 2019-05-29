package com.example.realxz.realxzdemo.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.example.realxz.realxzdemo.context.RealxzApplication

/**
 * @author real xz
 * @date 2019/5/20
 */
fun isNetworkValid(): Boolean {
    try {
        val manager = RealxzApplication.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = manager.allNetworkInfo
        for (i in info.indices) {
            if (info[i].state == NetworkInfo.State.CONNECTED) {
                return true
            }
        }
    } catch (e: Exception) {
        return false
    }

    return false
}