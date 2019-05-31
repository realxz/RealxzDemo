package com.example.realxz.realxzdemo.network

import com.example.realxz.realxzdemo.network.adapter.RealXzRxJava2CallAdapterFactory
import com.example.realxz.realxzdemo.network.converter.FastJsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

/**
 * @author real xz
 * @date 2019/5/15
 */
class ApiContainer private constructor() {
    var apiService: ApiService

    companion object {
        val instance = SingletonHolder.holder
    }

    private object SingletonHolder {
        val holder = ApiContainer()
    }

    init {
        val builder = OkHttpClient.Builder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)

        //retrofit2中使用OKHttp拦截的方式打印网络日志
        val logging2 = HttpLoggingInterceptor()
        logging2.level = HttpLoggingInterceptor.Level.BODY

        builder.addInterceptor(logging2)

        val retrofit = Retrofit.Builder()
                .baseUrl("http://rap2api.taobao.org/app/mock/")
                .addCallAdapterFactory(RealXzRxJava2CallAdapterFactory.create())
                .addConverterFactory(FastJsonConverterFactory.create())
                .client(builder.build())
                .build()

        apiService = retrofit.create(ApiService::class.java)
    }

}