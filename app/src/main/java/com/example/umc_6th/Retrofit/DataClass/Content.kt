package com.example.umc_6th.Retrofit.DataClass

import com.google.gson.annotations.SerializedName

data class Content(
    @SerializedName("boardId") val boardId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("createdAtbin") val createdAt: String
)