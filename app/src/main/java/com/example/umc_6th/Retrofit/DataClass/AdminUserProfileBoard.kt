package com.example.umc_6th.Retrofit.DataClass

import com.google.gson.annotations.SerializedName

data class AdminUserProfileBoard(
    @SerializedName("boardId") val boardId: Int,
    @SerializedName("title") val title: String
)