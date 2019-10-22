package com.gexiiiii.base.http

import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 * author : Gexiiiii
 * date : 2019/10/22 10:05
 * description :
 */
interface XHttpInterface {
    fun initHttp(): OkHttpClient
    fun init(baseUrl: String): Retrofit
}