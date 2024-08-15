package com.example.umc_6th.Retrofit.DataClass

import com.google.gson.annotations.SerializedName

data class RootQNA(
    @SerializedName("id") val id: Int,
    @SerializedName("status") val status: String,
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("createdAt") val createdAt: String
)