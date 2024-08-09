package com.example.umc_6th.Retrofit.DataClass

import com.google.gson.annotations.SerializedName

data class Bookmark(
    @SerializedName("id") val id: Int,
    @SerializedName("problem") val problem: String,
    @SerializedName("answer") val answer: String,
    @SerializedName("tag") val tag: String
)