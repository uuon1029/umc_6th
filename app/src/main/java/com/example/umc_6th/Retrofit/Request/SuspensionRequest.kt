package com.example.umc_6th.Retrofit.Request

data class SuspensionRequest(
    val boardId: String,
    val targetUserId: Int,
    val message: String,
    val division: String,
    val suspensionDueInt: Int
)