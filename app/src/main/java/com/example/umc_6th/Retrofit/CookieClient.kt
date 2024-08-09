package com.example.umc_6th.Retrofit

import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager

class CookieClient {
    companion object {
        private const val BASE_URL = "https://yesol.githyeon.shop/"

        var builder = OkHttpClient().newBuilder()
        var okHttpClient = builder
            .cookieJar(JavaNetCookieJar(CookieManager()))
            .build()


        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: RetrofitService = retrofit.create(RetrofitService::class.java)
    }
}