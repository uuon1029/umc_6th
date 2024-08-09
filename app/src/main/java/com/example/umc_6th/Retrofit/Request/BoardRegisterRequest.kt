package com.example.umc_6th.Retrofit.Request

import com.google.gson.annotations.SerializedName

data class BoardRegisterRequest(
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("majorId") val major: Int
)