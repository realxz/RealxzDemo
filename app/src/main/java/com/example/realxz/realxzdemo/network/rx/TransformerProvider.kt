package com.example.realxz.realxzdemo.network.rx

import android.util.Log
import com.example.realxz.realxzdemo.main.MainPresenter
import com.example.realxz.realxzdemo.network.exception.ClientException
import com.example.realxz.realxzdemo.utils.isNetworkValid
import io.reactivex.FlowableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * @author real xz
 * @date 2019/5/21
 */
fun <T> retryIo2MainTransformer(): FlowableTransformer<T, T> {
    return FlowableTransformer { upstream ->
        upstream
                //重试次数与重试间隔
                .retryWhen(RetryWithDelay())
                .subscribeOn(ioThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    if (!isNetworkValid()) {
                        throw ClientException("网络异常，请确保流量或者 Wifi 已打开")
                    }
                    Log.d(MainPresenter.TAG, "doOnSubscribe callback thread is " + Thread.currentThread().toString())
                }
    }
}