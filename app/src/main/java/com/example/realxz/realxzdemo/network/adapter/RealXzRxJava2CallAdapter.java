package com.example.realxz.realxzdemo.network.adapter;


import com.example.realxz.realxzdemo.network.ApiResponse;
import com.example.realxz.realxzdemo.network.rx.RealXzBodyFlowable;
import com.example.realxz.realxzdemo.network.rx.RealXzBodyObservable;
import com.example.realxz.realxzdemo.network.rx.RealXzCallExecuteObservable;

import java.lang.reflect.Type;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Response;

/**
 * @author real xz
 * @date 2018/9/7
 */

public class RealXzRxJava2CallAdapter<R> implements CallAdapter<ApiResponse<R>, Object> {
    private final Type responseType;
    private final boolean isFlowable;

    RealXzRxJava2CallAdapter(Type responseType, boolean isFlowable) {
        this.responseType = responseType;
        this.isFlowable = isFlowable;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public Object adapt(Call<ApiResponse<R>> call) {
        Observable<Response<ApiResponse<R>>> responseObservable = new RealXzCallExecuteObservable<>(call);
        if (isFlowable) {
            return new RealXzBodyFlowable<>(responseObservable.toFlowable(BackpressureStrategy.LATEST));
        } else {
            return new RealXzBodyObservable<>(responseObservable);
        }
    }
}

