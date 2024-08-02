package com.example.umc_6th.Retrofit

import com.google.gson.annotations.SerializedName

data class SignupResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: Result
) {
    data class Result(
        @SerializedName("id") val id: Int,
        @SerializedName("nickName") val nickName: String
    )
}