package com.example.umc_6th.Retrofit.Request

import com.google.gson.annotations.SerializedName

data class IdRestoreRequest(
    @SerializedName("account") val account: String
)