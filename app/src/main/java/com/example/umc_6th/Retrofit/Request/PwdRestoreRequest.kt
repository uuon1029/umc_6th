package com.example.umc_6th.Retrofit.Request

import com.google.gson.annotations.SerializedName

data class PwdRestoreRequest(
    @SerializedName("id") val id: Int,
    @SerializedName("password") val password: String,
    @SerializedName("passwordCheck") val passwordCheck: String
)