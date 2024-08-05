package com.example.umc_6th.Retrofit.DataClass

import com.google.gson.annotations.SerializedName

data class Board(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("pinCount") val pinCount: Int,
    @SerializedName("likeCount") val likeCount: Int,
    @SerializedName("boardDate") val boardDate: String,
    @SerializedName("userNickName") val userNickName: String,
    @SerializedName("picPreview") val picPreview: String?
)
