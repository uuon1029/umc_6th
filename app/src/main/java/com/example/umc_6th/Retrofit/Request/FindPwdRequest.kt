package com.example.umc_6th.Retrofit.Request

import com.google.gson.annotations.SerializedName

data class FindPwdRequest(
    @SerializedName("account") val account: String,
    @SerializedName("name") val name: String,
    @SerializedName("phone") val phone: String
)