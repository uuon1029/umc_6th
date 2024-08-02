package com.example.umc_6th.Retrofit

data class FindAllFavoriteResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: List<Result>
) {
    data class Result(
        val id: Int,
        val problem: String,
        val answer: String,
        val tag: String
    )
}