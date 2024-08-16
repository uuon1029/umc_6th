package com.example.umc_6th.Retrofit.DataClass

import com.google.gson.annotations.SerializedName

data class Suspension(
    @SerializedName("nickName") val nickName: String,
    @SerializedName("message") val message: String,
    @SerializedName("boardId") val boardId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String
)