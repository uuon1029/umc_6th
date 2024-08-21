package com.example.umc_6th.Retrofit.Response

data class PhoneAuthResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: Result
) {
    data class Result(
        val phone: String,
        val authNum: String
    )
}