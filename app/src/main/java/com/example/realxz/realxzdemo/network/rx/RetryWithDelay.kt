package com.example.realxz.realxzdemo.network.rx

import android.util.Log
import com.example.realxz.realxzdemo.main.MainPresenter
import com.example.realxz.realxzdemo.network.exception.ServerException
import io.reactivex.Flowable
import io.reactivex.functions.Function
import org.reactivestreams.Publisher
import java.util.concurrent.TimeUnit

/**
 * @author real xz
 * @date 2019/5/16
 */
class RetryWithDelay(private val maxRetries: Int = DEFAULT_RETRY_COUNT, private var retryDelayMillis: Long = DEFAULT_RETRY_DELAY_MILLIS)
    : Function<Flowable<Throwable>, Publisher<*>> {
    companion object {
        const val DEBOUNCE_TIME = 200L
        const val DEFAULT_RETRY_COUNT = 2
        const val DEFAULT_RETRY_DELAY_MILLIS = 200L
    }

    private var retryCount = 0;


    init {
        retryDelayMillis = if (retryDelayMillis < DEBOUNCE_TIME) DEBOUNCE_TIME else retryDelayMillis
    }

    override fun apply(throwable: Flowable<Throwable>): Publisher<*> {
        return throwable
                .flatMap(Function<Throwable, Publisher<*>> { throwable ->
                    //todo 业务失败不需要重试
                    if (throwable is ServerException) {
                        return@Function Flowable.error<Throwable>(throwable)
                    }
                    if (++retryCount <= maxRetries) {
                        Log.d(MainPresenter.TAG, "当前是第 $retryCount 次重试")
                        return@Function Flowable.timer(retryDelayMillis, TimeUnit.MILLISECONDS)
                    }
                    return@Function Flowable.error<Throwable>(throwable)
                })
    }


}
