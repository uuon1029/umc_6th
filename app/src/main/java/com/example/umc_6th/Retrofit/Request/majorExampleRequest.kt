package com.example.umc_6th.Retrofit.Request

import com.google.gson.annotations.SerializedName

data class majorExampleRequest(
    @SerializedName("major") val major : String,
    @SerializedName("question") val question: String
)
