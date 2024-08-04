package com.example.umc_6th.Retrofit.Data

import com.google.gson.annotations.SerializedName


data class MainBoard(
    @SerializedName("title") val title: String,
    @SerializedName("likeCount") val likeCount: Int,
    @SerializedName("pinCount") val pinCount: Int,
    @SerializedName("isHavingPic") val isHavingPic: Boolean
)

