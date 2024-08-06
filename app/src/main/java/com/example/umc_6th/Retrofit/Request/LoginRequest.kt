package com.example.umc_6th.Retrofit.Request

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("account") val account: String,
    @SerializedName("password") val password: String
)