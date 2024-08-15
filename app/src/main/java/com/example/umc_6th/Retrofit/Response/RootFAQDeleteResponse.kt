package com.example.umc_6th.Retrofit.Response

data class RootFAQDeleteResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: Result
) {
    data class Result(
        val deletedDate: String,
        val faqid: Int
    )
}