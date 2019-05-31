package com.example.realxz.realxzdemo.network.rx

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * @author real xz
 * @date 2019/5/21
 */
fun ioThread() = Schedulers.from(ThreadPoolExecutor(2, 30, 30L, TimeUnit.SECONDS, LinkedBlockingQueue(128), ThreadPoolExecutor.DiscardOldestPolicy()))

fun mainThread() = AndroidSchedulers.mainThread()