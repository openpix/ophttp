package com.openpix.ophttp.retrofit

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.openpix.ophttp.OPHttp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

/**
 * Copyright (C), 2020-2020, guagua
 * Author: pix
 * Date: 2020/4/14 16:29
 * Version: 1.0.0
 * Description: Retrofit封装
 * History:
 * <author> <time> <version> <desc>
 */
object Rest {
    /**
     * 请求集合
     * service 名称做key，restAPI value
     *  Retrofit 生成一个基础url的对象
     *
     */
    private val restMap = HashMap<String,Any>()
    private var opHttp:OPHttp?=null
    private var retrofit:Retrofit? = null

    /**
     * OPHttp对象
     */
    fun ophttp(opHttp: OPHttp?): Rest {
        Rest.opHttp = opHttp
        retrofit =  Retrofit.Builder()
            .baseUrl(opHttp?.domain)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(opHttp?.okHttpClient)
            .build()

        return this
    }

    fun <T> getRestApi(service: Class<T>): T {
        if (null == restMap[service.canonicalName]) {
            val restAPI = retrofit?.create(service)
            if (null != service.canonicalName && null != restAPI) {
                restMap.put(service.canonicalName.toString(), restAPI)
            }
        }
        return restMap[service.canonicalName] as T
    }
}