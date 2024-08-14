package com.example.umc_6th.Retrofit.Response

data class LogoutResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: String
)