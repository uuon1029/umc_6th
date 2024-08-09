package com.example.umc_6th.Retrofit.Response

import com.google.gson.annotations.SerializedName

data class ReissueResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: Result
) {
    data class Result(
        @SerializedName("nickName") val nickName: String,
        @SerializedName("majorId") val majorId: Int,
        @SerializedName("userId") val userId: Int,
        @SerializedName("accessToken") val accessToken: String
    )
}