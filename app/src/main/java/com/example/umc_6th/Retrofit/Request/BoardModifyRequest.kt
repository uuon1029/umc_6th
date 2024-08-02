package com.example.umc_6th.Retrofit.Request

data class BoardModifyRequest(
    val title: String,
    val content: String,
    val pic: List<String>
)