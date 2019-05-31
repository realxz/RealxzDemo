package com.example.realxz.realxzdemo.network.adapter;

import com.example.realxz.realxzdemo.network.rx.RealXzBodyFlowable;
import com.example.realxz.realxzdemo.network.rx.RealXzBodyObservable;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.CallAdapter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.Result;

/**
 * @author real xz
 * @date 2018/9/7
 */

public class RealXzRxJava2CallAdapterFactory extends CallAdapter.Factory {
    public static RealXzRxJava2CallAdapterFactory create() {
        return new RealXzRxJava2CallAdapterFactory();
    }


    private RealXzRxJava2CallAdapterFactory() {
    }

    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        Class<?> rawType = getRawType(returnType);
        if (rawType != RealXzBodyObservable.class && rawType != RealXzBodyFlowable.class) {
            return null;
        }
        boolean isFlowable = rawType == RealXzBodyFlowable.class;

        // Flowable<String> --> String
        Type responseType;
        if (!(returnType instanceof ParameterizedType)) {
            throw new IllegalStateException("DadaFlowable return type must be parameterized as DadaFlowable<Foo> or DadaFlowable<? extends Foo>");
        }

        //Flowable<? extends String> -- >String
        Type observableType = getParameterUpperBound(0, (ParameterizedType) returnType);
        Class<?> rawObservableType = getRawType(observableType);
        if (rawObservableType == Response.class || rawObservableType == Result.class) {
            return null;
        }
        //一般走到这里 responseType 就是我们声明的业务类型
        responseType = observableType;

        return new RealXzRxJava2CallAdapter<>(responseType, isFlowable);

    }
}

