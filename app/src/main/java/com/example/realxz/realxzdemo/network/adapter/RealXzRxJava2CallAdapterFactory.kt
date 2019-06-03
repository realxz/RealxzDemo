package com.example.realxz.realxzdemo.network.adapter

import com.example.realxz.realxzdemo.network.rx.RealXzBodyFlowable
import com.example.realxz.realxzdemo.network.rx.RealXzBodyObservable
import retrofit2.CallAdapter
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.Result
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * @author real xz
 * @date 2019-06-03
 */
class RealXzRxJava2CallAdapterFactory private constructor() : CallAdapter.Factory() {

    companion object {
        fun create(): RealXzRxJava2CallAdapterFactory {
            return RealXzRxJava2CallAdapterFactory()
        }
    }

    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
        val rawType = getRawType(returnType)
        if (rawType != RealXzBodyObservable::class.java && rawType != RealXzBodyFlowable::class.java) {
            return null
        }

        val isFlowable = rawType == RealXzBodyFlowable::class.java

        if (returnType !is ParameterizedType) {
            throw IllegalStateException("DadaFlowable return type must be parameterized as DadaFlowable<Foo> or DadaFlowable<? extends Foo>")
        }

        val responseType: Type
        val observableType = getParameterUpperBound(0, returnType)
        val rawObservableType = getRawType(observableType)

        if (rawObservableType == Response::class.java || rawObservableType == Result::class.java) {
            return null
        }

        responseType = observableType
        return RealXzRxJava2CallAdapter<Any>(responseType, isFlowable)
    }
}