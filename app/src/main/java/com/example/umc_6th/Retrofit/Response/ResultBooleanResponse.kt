package com.example.umc_6th.Retrofit.Response

import com.google.gson.annotations.SerializedName

data class ResultBooleanResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: Boolean
)