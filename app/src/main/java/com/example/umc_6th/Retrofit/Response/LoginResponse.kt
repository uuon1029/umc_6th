package com.example.umc_6th.Retrofit

data class LoginResponse(
    val message: String,
    val result: Result
) {
    data class Result(
        val userId: String,
        val accessToken: String,
        val createAt: String
    )
}