package com.example.umc_6th.Retrofit.Request

data class BoardRegisterRequest(
    val title: String,
    val content: String,
    val major: Int,
    val pic: List<String>
)