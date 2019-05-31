package com.example.realxz.realxzdemo.network.rx

import android.util.Log
import com.example.realxz.realxzdemo.context.RealxzApplication.Companion.TAG
import com.example.realxz.realxzdemo.network.exception.ServerException
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import org.reactivestreams.Publisher
import java.util.concurrent.TimeUnit

/**
 * @author real xz
 * @date 2019-05-31
 */
private const val DEBOUNCE_TIME = 200L
private const val DEFAULT_RETRY_COUNT = 2
private const val DEFAULT_RETRY_DELAY_MILLIS = 200L

class FlowableRetryDelay(private val maxRetries: Int = DEFAULT_RETRY_COUNT, private var retryDelayMillis: Long = DEFAULT_RETRY_DELAY_MILLIS)
    : Function<Flowable<Throwable>, Publisher<*>> {
    private var retryCount = 0

    init {
        retryDelayMillis = if (retryDelayMillis < DEBOUNCE_TIME) DEBOUNCE_TIME else retryDelayMillis

    }

    override fun apply(t: Flowable<Throwable>): Publisher<*> {
        return t.flatMap {
            if (it is ServerException) {
                return@flatMap Flowable.error<Throwable>(it)
            }
            if (++retryCount <= maxRetries) {
                Log.d(TAG, "当前是第 $retryCount 次重试")
                return@flatMap Flowable.timer(retryDelayMillis, TimeUnit.MILLISECONDS)
            }
            return@flatMap Flowable.error<Throwable>(it)
        }
    }
}

class ObservableRetryDelay(private val maxRetries: Int = DEFAULT_RETRY_COUNT, private var retryDelayMillis: Long = DEFAULT_RETRY_DELAY_MILLIS)
    : Function<Observable<Throwable>, ObservableSource<*>> {

    private var retryCount = 0

    init {
        retryDelayMillis = if (retryDelayMillis < DEBOUNCE_TIME) DEBOUNCE_TIME else retryDelayMillis

    }

    override fun apply(t: Observable<Throwable>): ObservableSource<*> {
        return t.flatMap {
            if (it is ServerException) {
                return@flatMap Observable.error<Throwable>(it)
            }
            if (++retryCount <= maxRetries) {
                Log.d(TAG, "当前是第 $retryCount 次重试")
                return@flatMap Observable.timer(retryDelayMillis, TimeUnit.MILLISECONDS)
            }
            return@flatMap Observable.error<Throwable>(it)
        }
    }
}