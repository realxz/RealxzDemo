package com.example.realxz.realxzdemo.network.converter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.realxz.realxzdemo.network.ApiResponse;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * @author real xz
 * @date 2018/9/7
 */

public class FastJsonResponseBodyConverter<T> implements Converter<ResponseBody, ApiResponse<T>> {
    private final Type type;

    public FastJsonResponseBodyConverter(Type type) {
        this.type = type;
    }

    @Override
    public ApiResponse<T> convert(ResponseBody value) throws IOException {
        // TODO: 2018/8/29 能否避免使用 Raw Type
        ApiResponse apiResponse = JSON.parseObject(value.string(), ApiResponse.class);
        //JSONObject和JSONArray不需要转换
        if (apiResponse.getContent() == null) {
            apiResponse.setContent(new JSONObject());
        }
        Object content = apiResponse.getContent();

        if (apiResponse.isOK()) {
            if (JSONObject.class != type && JSONArray.class != type) {
                if (String.class != type || !(content instanceof String)) {
                    apiResponse.setContent(JSON.parseObject(content.toString(), type));
                }
            }
        }
        return apiResponse;
    }
}

