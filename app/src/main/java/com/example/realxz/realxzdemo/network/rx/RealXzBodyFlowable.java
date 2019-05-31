package com.example.realxz.realxzdemo.network.rx;

import com.example.realxz.realxzdemo.network.ApiResponse;
import com.example.realxz.realxzdemo.network.exception.ServerException;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Flowable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.plugins.RxJavaPlugins;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * @author real xz
 * @date 2019-05-30
 */
public class RealXzBodyFlowable<T> extends Flowable<T> {
    private final Flowable<Response<ApiResponse<T>>> upstream;

    public RealXzBodyFlowable(Flowable<Response<ApiResponse<T>>> upstream) {
        this.upstream = upstream;
    }

    @Override
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        upstream.subscribe(new BodySubscriber<>(subscriber));
    }

    private static class BodySubscriber<R> implements Subscriber<Response<ApiResponse<R>>> {
        private final Subscriber<? super R> subscriber;
        private boolean terminated;

        public BodySubscriber(Subscriber<? super R> subscriber) {
            this.subscriber = subscriber;
        }

        @Override
        public void onSubscribe(Subscription s) {
            subscriber.onSubscribe(s);
        }

        @Override
        public void onNext(Response<ApiResponse<R>> response) {
            if (response.isSuccessful()) {
                ApiResponse<R> apiResponse = response.body();
                if (apiResponse.isOK()) {
                    //业务 OK
                    subscriber.onNext(apiResponse.getContent());
                } else {
                    String apiErrorCode = apiResponse.getErrorCode();
                    String apiErrorMessage = apiResponse.getErrorMsg();
                    //业务失败
                    Throwable t = new ServerException(apiErrorCode, apiErrorMessage, apiResponse);
                    try {
                        subscriber.onError(t);
                    } catch (Throwable inner) {
                        Exceptions.throwIfFatal(inner);
                        RxJavaPlugins.onError(new CompositeException(t, inner));
                    }
                }
            } else {
                terminated = true;
                Throwable t = new HttpException(response);
                try {
                    subscriber.onError(t);
                } catch (Throwable inner) {
                    Exceptions.throwIfFatal(inner);
                    RxJavaPlugins.onError(new CompositeException(t, inner));
                }
            }

        }

        @Override
        public void onComplete() {
            if (!terminated) {
                subscriber.onComplete();
            }
        }

        @Override
        public void onError(Throwable throwable) {
            if (!terminated) {
                subscriber.onError(throwable);
            } else {
                // This should never happen! onNext handles and forwards errors automatically.
                Throwable broken = new AssertionError(
                        "This should never happen! Report as a bug with the full stacktrace.");
                //noinspection UnnecessaryInitCause Two-arg AssertionError constructor is 1.7+ only.
                broken.initCause(throwable);
                RxJavaPlugins.onError(broken);
            }

        }


    }
}
