package com.example.umc_6th.Retrofit.DataClass

import com.google.gson.annotations.SerializedName

data class PinContent(
    @SerializedName("complaintId") val complaintId: Int,
    @SerializedName("comment") val comment: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("pinOrComment") val pinOrComment: String
)