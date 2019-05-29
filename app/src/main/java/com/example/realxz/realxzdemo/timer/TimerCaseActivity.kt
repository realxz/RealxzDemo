package com.example.realxz.realxzdemo.timer

import android.os.Bundle
import android.util.Log
import com.example.realxz.realxzdemo.R
import com.example.realxz.realxzdemo.base.BaseActivity
import com.uber.autodispose.FlowableSubscribeProxy
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class TimerCaseActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer_case)


        Observable.timer(500, TimeUnit.MILLISECONDS).subscribe(object : Observer<Long> {
            override fun onNext(t: Long) {
                refreshRoute()
            }

            override fun onSubscribe(disposable: Disposable) {

            }

            override fun onError(throwable: Throwable) {

            }

            override fun onComplete() {

            }
        })


    }

    fun refreshRoute() {
        Flowable.create(FlowableOnSubscribe<String> { flowableEmitter ->
            flowableEmitter.onNext("test")
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .`as`<FlowableSubscribeProxy<String>>(bindAutoDisposable())
                .subscribe({
                    Log.d("TimerCaseActivity", "onNext")

                }, {
                    Log.d("TimerCaseActivity", "onError")

                })
    }
}
