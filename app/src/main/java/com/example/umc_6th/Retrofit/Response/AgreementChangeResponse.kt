package com.example.umc_6th.Retrofit.Response

data class AgreementChangeResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: Result
) {
    data class Result(
        val agreement: String
    )
}