package com.example.umc_6th.Retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class FindAccountResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @Expose
    @SerializedName("result") val result: Result
) {
    data class Result(
        @SerializedName("account") val account: String,
        @SerializedName("createdAt") val createdAt: String
    )
}