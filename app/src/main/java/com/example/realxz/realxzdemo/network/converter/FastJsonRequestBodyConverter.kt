package com.example.realxz.realxzdemo.network.converter

import com.alibaba.fastjson.JSON
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Converter

/**
 * @author real xz
 * @date 2019-06-03
 */
class FastJsonRequestBodyConverter<T> : Converter<T, RequestBody> {
    companion object {
        private val MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8")
    }

    override fun convert(value: T): RequestBody {
        return RequestBody.create(MEDIA_TYPE, JSON.toJSONBytes(value))
    }
}