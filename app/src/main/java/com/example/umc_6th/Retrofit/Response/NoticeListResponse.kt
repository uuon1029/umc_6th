package com.example.umc_6th.Retrofit

data class NoticeListResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: List<Result>
) {
    data class Result(
        val id: Int,
        val title: String,
        val createdAt: String,
        val updatedAt: String
    )
}