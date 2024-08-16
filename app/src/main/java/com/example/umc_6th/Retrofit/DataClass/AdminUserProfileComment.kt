package com.example.umc_6th.Retrofit.DataClass

import com.google.gson.annotations.SerializedName

data class AdminUserProfileComment(
    @SerializedName("id") val id: Int,
    @SerializedName("boardId") val boardId: Int,
    @SerializedName("comment") val comment: String,
    @SerializedName("createdAt") val createdAt: String
)