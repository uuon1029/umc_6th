package com.example.umc_6th.Retrofit

import com.example.umc_6th.Retrofit.DataClass.Bookmark
import com.google.gson.annotations.SerializedName

data class FindAllFavoriteResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: ArrayList<Bookmark>
)