package com.example.umc_6th.Retrofit.Request

import com.google.gson.annotations.SerializedName

data class SignupRequest(
    @SerializedName("name") val name: String,
    @SerializedName("nickName") val nickName: String,
    @SerializedName("account") val account: String,
    @SerializedName("password") val password: String,
    @SerializedName("passwordCheck") val passwordCheck: String,
    @SerializedName("major") val major: Int,
    @SerializedName("phone") val phone: String
)