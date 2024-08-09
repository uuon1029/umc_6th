package com.example.umc_6th.Retrofit.DataClass

import com.google.gson.annotations.SerializedName

data class Qna(
    @SerializedName("id") val id: Int,
    @SerializedName("status") val status: String,
    @SerializedName("title") val title: String,
    @SerializedName("createdAt") val createdAt: String
)