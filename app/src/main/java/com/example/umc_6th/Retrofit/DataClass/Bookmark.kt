package com.example.umc_6th.Retrofit.DataClass

import com.google.gson.annotations.SerializedName

data class Bookmark(
    @SerializedName("exampleId") val exampleId: Int,
    @SerializedName("answerId") val answerId: Int,
    @SerializedName("favoriteId") val favoriteId: Int,

    @SerializedName("problem") val problem: String,
    @SerializedName("answer") val answer: String,
    @SerializedName("tag") val tag: String
)