package com.example.umc_6th.Retrofit.Request

data class warnRequest(
    val boardId: String,
    val targetUserId: String,
    val message: String,
    val division: String
)