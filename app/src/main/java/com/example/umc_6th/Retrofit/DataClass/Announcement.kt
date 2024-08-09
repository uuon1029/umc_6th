package com.example.umc_6th.Retrofit.DataClass

import com.google.gson.annotations.SerializedName

data class Announcement(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String
)
