package com.example.realxz.realxzdemo.network.adapter

import com.example.realxz.realxzdemo.network.ApiResponse
import com.example.realxz.realxzdemo.network.rx.RealXzBodyFlowable
import com.example.realxz.realxzdemo.network.rx.RealXzBodyObservable
import com.example.realxz.realxzdemo.network.rx.RealXzCallExecuteObservable
import io.reactivex.BackpressureStrategy
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

/**
 * @author real xz
 * @date 2019-06-03
 */
class RealXzRxJava2CallAdapter<R>(private val responseType: Type, private val isFlowable: Boolean) : CallAdapter<ApiResponse<R>, Any> {
    override fun responseType(): Type {
        return responseType
    }

    override fun adapt(call: Call<ApiResponse<R>>): Any {
        val responseObservable = RealXzCallExecuteObservable(call)
        return if (isFlowable) {
            RealXzBodyFlowable(responseObservable.toFlowable(BackpressureStrategy.LATEST))
        } else {
            RealXzBodyObservable(responseObservable)
        }
    }


}