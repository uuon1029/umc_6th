package com.example.umc_6th.Retrofit.Request

import com.google.gson.annotations.SerializedName

data class FindIdRequest(
    @SerializedName("phone") val phone: String
)