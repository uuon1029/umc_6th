package com.example.umc_6th.Retrofit.Request

data class PinModifyRequest(
    val id: String,
    val comment: String,
    val pic: List<String>
)