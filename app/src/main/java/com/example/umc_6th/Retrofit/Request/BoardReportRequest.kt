package com.example.umc_6th.Retrofit.Request

import com.google.gson.annotations.SerializedName

data class BoardReportRequest(
    @SerializedName("content") val content: String
)