package com.example.umc_6th.Retrofit.DataClass

import com.google.gson.annotations.SerializedName

data class Faq(
    @SerializedName("category") val category: String,
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("updateAt") val updateAt: String,
    @SerializedName("faqid") val faqid: Int
)
