package com.example.umc_6th.Retrofit.Request

import com.google.gson.annotations.SerializedName

data class NickNameDupRequest(
    @SerializedName("nickName") val nickName: String
)