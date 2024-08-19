package com.example.umc_6th.Retrofit.Request

import com.google.gson.annotations.SerializedName

data class majorExampleRequest(
    @SerializedName("majorId") val majorId : Int,
    @SerializedName("question") val question: String
)
