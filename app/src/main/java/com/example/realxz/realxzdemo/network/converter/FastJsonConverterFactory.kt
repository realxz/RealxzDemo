package com.example.realxz.realxzdemo.network.converter

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * @author real xz
 * @date 2019-06-03
 */
class FastJsonConverterFactory : Converter.Factory() {
    companion object {
        fun create(): FastJsonConverterFactory = FastJsonConverterFactory()
    }

    override fun responseBodyConverter(type: Type, annotations: Array<Annotation>, retrofit: Retrofit): Converter<ResponseBody, *> {
        return FastJsonResponseBodyConverter<Any>(type)
    }

    override fun requestBodyConverter(type: Type, parameterAnnotations: Array<Annotation>, methodAnnotations: Array<Annotation>, retrofit: Retrofit): Converter<*, RequestBody> {
        return FastJsonRequestBodyConverter<Any>()
    }


}