package com.example.umc_6th.Retrofit

data class FindPwdResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: Result
) {
    data class Result(
        val password: String,
        val createdAt: String
    )
}