package com.example.umc_6th.Retrofit

import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okio.Buffer

class   okHttpClient {

    companion object {
        private const val BASE_URL = "https://yesol.githyeon.shop/"

        private val client = OkHttpClient.Builder().addInterceptor { chain ->
            val originalRequest = chain.request()
            if (originalRequest.method == "GET" && originalRequest.body != null) {
                val buffer = Buffer()
                originalRequest.body?.writeTo(buffer)
                val bodyString = buffer.readUtf8()

                val urlWithBody = originalRequest.url.newBuilder().query(bodyString).build()
                val newRequest = originalRequest.newBuilder()
                    .method("GET", null) // Remove the body from the GET request
                    .url(urlWithBody)
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
