package com.example.realxz.realxzdemo.network

import com.example.realxz.realxzdemo.network.rx.RealXzBodyFlowable
import com.example.realxz.realxzdemo.network.rx.RealXzBodyObservable
import com.example.realxz.realxzdemo.pojo.FakeTask
import com.example.realxz.realxzdemo.pojo.Task
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * 获取天气服务接口
 *
 * @author real xz
 * @date 2019/5/15
 */
interface ApiService {
    @GET("177028/realxz/list")
    fun loadRapTasks(@Query("name") name: String?): RealXzBodyFlowable<List<Task>>

    @GET("177028/")
    fun mockClientErrorRequest(): RealXzBodyFlowable<String>

    @GET("177028/realxz/list")
    fun mockServerErrorRequest(): RealXzBodyFlowable<String>

    @GET("177028/realxz/biz_error")
    fun mockBusinessErrorRequest(): RealXzBodyObservable<String>

    @GET("177028/realxz/list")
    fun mockFormatErrorRequest(@Query("name") name: String?): RealXzBodyObservable<List<FakeTask>>

    @GET("177028/realxz/list")
    fun mockJsonErrorRequest(@Query("name") name: String?): RealXzBodyObservable<FakeTask>
}