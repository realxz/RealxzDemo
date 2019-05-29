package com.example.realxz.realxzdemo.network.rx;

import com.example.realxz.realxzdemo.network.ApiResponse;
import com.example.realxz.realxzdemo.network.exception.ServerException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.plugins.RxJavaPlugins;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * @author real xz
 * @date 2018/9/7
 */

final class RealXzBodyObservable<T> extends Observable<T> {
    private final Observable<Response<ApiResponse<T>>> upstream;

    RealXzBodyObservable(Observable<Response<ApiResponse<T>>> upstream) {
        this.upstream = upstream;
    }

    @Override
    protected void subscribeActual(Observer<? super T> observer) {
        upstream.subscribe(new BodyObserver<>(observer));
    }

    private static class BodyObserver<R> implements Observer<Response<ApiResponse<R>>> {
        private final Observer<? super R> observer;
        private boolean terminated;

        BodyObserver(Observer<? super R> observer) {
            this.observer = observer;
        }

        @Override
        public void onSubscribe(Disposable disposable) {
            observer.onSubscribe(disposable);
        }

        @Override
        public void onNext(Response<ApiResponse<R>> response) {
            if (response.isSuccessful()) {
                ApiResponse<R> apiResponse = response.body();
                if (apiResponse.isOK()) {
                    //业务 OK
                    observer.onNext(apiResponse.getContent());
                } else {
                    String apiErrorCode = apiResponse.getErrorCode();
                    String apiErrorMessage = apiResponse.getErrorMsg();
                    //业务失败
                    Throwable t = new ServerException(apiErrorCode, apiErrorMessage, apiResponse);
                    try {
                        observer.onError(t);
                    } catch (Throwable inner) {
                        Exceptions.throwIfFatal(inner);
                        RxJavaPlugins.onError(new CompositeException(t, inner));
                    }
                }
            } else {
                terminated = true;
                Throwable t = new HttpException(response);
                try {
                    observer.onError(t);
                } catch (Throwable inner) {
                    Exceptions.throwIfFatal(inner);
                    RxJavaPlugins.onError(new CompositeException(t, inner));
                }
            }
        }

        @Override
        public void onComplete() {
            if (!terminated) {
                observer.onComplete();
            }
        }

        @Override
        public void onError(Throwable throwable) {
            if (!terminated) {
                observer.onError(throwable);
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
