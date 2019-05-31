package com.example.realxz.realxzdemo.network.rx

import io.reactivex.observers.DisposableObserver

/**
 * @author real xz
 * @date 2019-05-30
 */
abstract class ApiObserver<T> : DisposableObserver<T>(), IApiProcess<T> {
    override fun onNext(t: T) {
        onRealXzSuccess(t)
    }

    override fun onError(e: Throwable) {
       testError(e)
    }

    override fun onComplete() {
    }

}