package com.example.umc_6th.Retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class RetrofitClient {

    companion object {
        private const val BASE_URL = "https://yesol.githyeon.shop/"

        val retrofit: Retrofit = Retrofit.Builder().baseUrl(this.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: RetrofitService = retrofit.create(RetrofitService::class.java)

    }
}