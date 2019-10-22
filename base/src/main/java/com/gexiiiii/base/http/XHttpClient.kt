package com.gexiiiii.base.http

import android.util.Log
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import okhttp3.Cache
import okhttp3.CookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * author : Gexiiiii
 * date : 2019/10/22 10:06
 * description :
 */
open class XHttpClient : XHttpInterface {

    private val cacheDirectory = File("", "httpCache")
    private val cache = Cache(this.cacheDirectory, 10 * 1024 * 1024)

    override fun initHttp(): OkHttpClient {
        return RetrofitUrlManager.getInstance()
            .with(
                OkHttpClient.Builder()
                    .cookieJar(CookieJar.NO_COOKIES)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(
                        HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                            Log.i("xHttp", it)
                        }).setLevel(HttpLoggingInterceptor.Level.BODY)
                    )
                    .cache(this.cache)
            )
            .build()
    }

    override fun init(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(initHttp()).build()
    }
}