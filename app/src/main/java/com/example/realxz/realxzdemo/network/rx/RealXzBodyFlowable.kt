package com.example.realxz.realxzdemo.network.rx

import com.example.realxz.realxzdemo.network.ApiResponse
import com.example.realxz.realxzdemo.network.exception.ServerException
import io.reactivex.Flowable
import io.reactivex.exceptions.CompositeException
import io.reactivex.exceptions.Exceptions
import io.reactivex.plugins.RxJavaPlugins
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import retrofit2.HttpException
import retrofit2.Response

/**
 * @author real xz
 * @date 2019-06-03
 */
class RealXzBodyFlowable<T>(val upstream: Flowable<Response<ApiResponse<T>>>) : Flowable<T>() {

    override fun subscribeActual(s: Subscriber<in T>) {
        upstream.subscribe(BodySubscriber(s))
    }

}

private class BodySubscriber<R>(val subscriber: Subscriber<in R>) : Subscriber<Response<ApiResponse<R>>> {
    private var terminated: Boolean = false
    override fun onSubscribe(s: Subscription?) {
        subscriber.onSubscribe(s)
    }

    override fun onNext(response: Response<ApiResponse<R>>?) {
        if (response != null && response.isSuccessful) {
            val apiResponse = response.body()
            if (apiResponse != null && apiResponse.isOK()) {
                //业务 OK
                subscriber.onNext(apiResponse.content)
            } else {
                val apiErrorCode = apiResponse?.errorCode
                val apiErrorMessage = apiResponse?.errorMsg
                //业务失败
                val t = ServerException(apiErrorCode, apiErrorMessage, apiResponse
                        ?: ApiResponse<Any>())
                try {
                    subscriber.onError(t)
                } catch (inner: Throwable) {
                    Exceptions.throwIfFatal(inner)
                    RxJavaPlugins.onError(CompositeException(t, inner))
                }

            }
        } else {
            terminated = true
            val t = HttpException(response)
            try {
                subscriber.onError(t)
            } catch (inner: Throwable) {
                Exceptions.throwIfFatal(inner)
                RxJavaPlugins.onError(CompositeException(t, inner))
            }
        }
    }


    override fun onComplete() {
        if (!terminated) {
            subscriber.onComplete()
        }
    }

    override fun onError(t: Throwable?) {
        if (!terminated) {
            subscriber.onError(t)
        } else {
            // This should never happen! onNext handles and forwards errors automatically.
            val broken = AssertionError(
                    "This should never happen! Report as a bug with the full stacktrace.")
            //noinspection UnnecessaryInitCause Two-arg AssertionError constructor is 1.7+ only.
            broken.initCause(t)
            RxJavaPlugins.onError(broken)
        }
    }

}