package com.example.umc_6th.Retrofit.Response

import com.google.gson.annotations.SerializedName

data class getExampleBoardResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: Result
) {
    data class Result(
        @SerializedName("id") val id: Int,
        @SerializedName("problem") val problem: String,
        @SerializedName("answer") val answer: String,
        @SerializedName("tag") val tag: String
    )
}