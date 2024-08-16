package com.example.umc_6th.Retrofit.DataClass

import com.google.gson.annotations.SerializedName

data class ProfileBoard(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("likeCount") val likeCount: Int,
    @SerializedName("pinCount") val pinCount: Int
)
