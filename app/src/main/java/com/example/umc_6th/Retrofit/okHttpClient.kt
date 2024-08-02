package com.example.umc_6th.Retrofit

import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class okHttpClient {

    companion object {
        private const val BASE_URL = "https://yesol.githyeon.shop/"

        private val client = OkHttpClient.Builder().addInterceptor { chain ->
            val originalRequest = chain.request()
            if (originalRequest.method == "GET" && originalRequest.body != null) {
                val body = originalRequest.body
                val buffer = okio.Buffer()
                body?.writeTo(buffer)
                val bodyString = buffer.readUtf8()

                val newRequest = originalRequest.newBuilder()
                    .method("GET", body)
                    .url(originalRequest.url.newBuilder().encodedQuery(bodyString).build())
                    .build()
                chain.proceed(newRequest)
            } else {
                chain.proceed(originalRequest)
            }
        }.build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(this.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: RetrofitService = retrofit.create(RetrofitService::class.java)
    }

}