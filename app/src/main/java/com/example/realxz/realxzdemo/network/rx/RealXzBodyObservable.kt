package com.example.realxz.realxzdemo.network.rx

import com.example.realxz.realxzdemo.network.ApiResponse
import com.example.realxz.realxzdemo.network.exception.ServerException
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.exceptions.CompositeException
import io.reactivex.exceptions.Exceptions
import io.reactivex.plugins.RxJavaPlugins
import retrofit2.HttpException
import retrofit2.Response

/**
 * @author real xz
 * @date 2019-06-03
 */
class RealXzBodyObservable<T>(private val upstream: Observable<Response<ApiResponse<T>>>) : Observable<T>() {
    override fun subscribeActual(observer: Observer<in T>) {
        upstream.subscribe(BodyObserver(observer))
    }


    private class BodyObserver<R>(private val observer: Observer<in R>) : Observer<Response<ApiResponse<R>>> {
        private var terminated: Boolean = false


        override fun onSubscribe(d: Disposable) {
            observer.onSubscribe(d)
        }

        override fun onNext(response: Response<ApiResponse<R>>) {
            if (response.isSuccessful) {
                val apiResponse = response.body()
                if (apiResponse != null && apiResponse.isOK()) {
                    observer.onNext(apiResponse.content!!)
                } else {
                    val apiErrorCode = apiResponse?.errorCode
                    val apiErrorMessage = apiResponse?.errorMsg
                    //业务失败
                    val t = ServerException(apiErrorCode, apiErrorMessage, apiResponse
                            ?: ApiResponse<Any>())

                    try {
                        observer.onError(t)
                    } catch (inner: Throwable) {
                        Exceptions.throwIfFatal(inner)
                        RxJavaPlugins.onError(CompositeException(t, inner))
                    }
                }


            } else {
                terminated = true
                val t = HttpException(response)
                try {
                    observer.onError(t)
                } catch (inner: Throwable) {
                    Exceptions.throwIfFatal(inner)
                    RxJavaPlugins.onError(CompositeException(t, inner))
                }
            }
        }

        override fun onComplete() {
            if (!terminated) {
                observer.onComplete()
            }
        }

        override fun onError(e: Throwable) {
            if (!terminated) {
                observer.onError(e)
            } else {
                // This should never happen! onNext handles and forwards errors automatically.
                val broken = AssertionError(
                        "This should never happen! Report as a bug with the full stacktrace.")

                broken.initCause(e)
                RxJavaPlugins.onError(broken)
            }
        }


    }
}