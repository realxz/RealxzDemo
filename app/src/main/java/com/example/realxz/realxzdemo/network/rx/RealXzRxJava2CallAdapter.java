package com.example.realxz.realxzdemo.network.rx;


import com.example.realxz.realxzdemo.network.ApiResponse;
import com.example.realxz.realxzdemo.network.RealxzFlowable;

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

    RealXzRxJava2CallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public Object adapt(Call<ApiResponse<R>> call) {
        Observable<Response<ApiResponse<R>>> responseObservable = new RealXzCallExecuteObservable<>(call);
        RealXzBodyObservable<R> bodyObservable = new RealXzBodyObservable<>(responseObservable);
        return new RealxzFlowable<>(bodyObservable.toFlowable(BackpressureStrategy.LATEST));
    }
}

