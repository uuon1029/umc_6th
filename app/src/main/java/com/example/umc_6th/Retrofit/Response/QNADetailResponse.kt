package com.example.umc_6th.Retrofit.Response

import com.google.gson.annotations.SerializedName

data class QNADetailResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: Result
) {
    data class Result(
        @SerializedName("id") val id: Int,
        @SerializedName("title") val title: String,
        @SerializedName("content") val content: String,
        @SerializedName("answer") val answer: String,
        @SerializedName("picList") val picList: ArrayList<String>,
        @SerializedName("createdAt") val createdAt: String,
        @SerializedName("answeredAt") val answeredAt: String
    )
}