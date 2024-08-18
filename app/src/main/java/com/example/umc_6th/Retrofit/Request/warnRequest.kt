package com.example.umc_6th.Retrofit.Request

import com.google.gson.annotations.SerializedName

data class warnRequest(
    @SerializedName("boardId")val boardId: String,
    @SerializedName("targetUserId")val targetUserId: String,
    @SerializedName("message")val message: String,
    @SerializedName("division")val division: String
)