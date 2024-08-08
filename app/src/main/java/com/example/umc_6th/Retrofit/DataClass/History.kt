package com.example.umc_6th.Retrofit.DataClass

import com.google.gson.annotations.SerializedName


data class History(
    @SerializedName("boardId") val boardId: Int,
    @SerializedName("header") val header: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String
)