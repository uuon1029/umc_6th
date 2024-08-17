package com.example.umc_6th.Retrofit.Response

data class SuspendResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: Result
) {
    data class Result(
        val userId: Int,
        val nickName: String,
        val message: String
    )
}