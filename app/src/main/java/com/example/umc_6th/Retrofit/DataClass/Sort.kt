package com.example.umc_6th.Retrofit.DataClass

import com.google.gson.annotations.SerializedName

data class Sort(
    @SerializedName("empty") val empty: Boolean,
    @SerializedName("sorted") val sorted: Boolean,
    @SerializedName("unsorted") val unsorted: Boolean
)