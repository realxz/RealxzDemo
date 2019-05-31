package com.example.realxz.realxzdemo.network.rx

import io.reactivex.subscribers.DisposableSubscriber


/**
 * @author real xz
 * @date 2019/5/20
 */
abstract class ApiSubscriber<T> : DisposableSubscriber<T>(), IApiProcess<T> {

    override fun onNext(t: T) {
        onRealXzSuccess(t)
    }

    final override fun onError(e: Throwable) {
        testError(e)
    }

    override fun onComplete() {

    }
}