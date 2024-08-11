package com.example.umc_6th.Retrofit

import okhttp3.OkHttpClient //추가
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class RetrofitClient {

    companion object {
        private const val BASE_URL = "https://yesol.githyeon.shop/"

        //토큰 추가
        private var accessToken: String? = null

        //추가
        private val loggingInterceptor =HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        //추가
        private val okHttpClient = OkHttpClient.Builder()
            //.addInterceptor(ContentTypeInterceptor())
            .addInterceptor(loggingInterceptor)
            .build()



        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(this.BASE_URL)
            .client(okHttpClient) // 추가
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: RetrofitService = retrofit.create(RetrofitService::class.java)


    }



}