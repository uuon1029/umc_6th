package com.example.umc_6th.Retrofit.Response

import com.google.gson.annotations.SerializedName

data class MainPageRes(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: ArrayList<Result>
) {
    data class Result(
        @SerializedName("userId") val userId: Int,
        @SerializedName("answerId") val answerId: Int,
        @SerializedName("majorId") val majorId: Int,
        @SerializedName("question") val question: String,
        @SerializedName("content") val content: String,
        @SerializedName("nickname") val nickname: String,
        @SerializedName("major") val major: String,
        @SerializedName("createdAt") val createdAt: String
    )
}