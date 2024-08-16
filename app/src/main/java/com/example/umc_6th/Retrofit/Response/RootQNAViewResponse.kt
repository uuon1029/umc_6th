package com.example.umc_6th.Retrofit.Response

import com.google.gson.annotations.SerializedName

data class RootQNAViewResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("isSuccess") val code: String,
    @SerializedName("isSuccess") val message: String,
    @SerializedName("isSuccess") val result: Result
) {
    data class Result(
        @SerializedName("isSuccess") val id: Int,
        @SerializedName("isSuccess") val userId: Int,
        @SerializedName("isSuccess") val userNickname: String,
        @SerializedName("isSuccess") val userprofile: String,
        @SerializedName("isSuccess") val createdAt: String,
        @SerializedName("isSuccess") val title: String,
        @SerializedName("isSuccess") val content: String,
        @SerializedName("isSuccess") val picList: ArrayList<String>,
        @SerializedName("isSuccess") val answer: String
    )
}