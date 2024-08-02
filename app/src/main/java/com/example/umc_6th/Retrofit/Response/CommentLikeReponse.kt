package com.example.umc_6th.Retrofit.Response

data class CommentLikeReponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: Int?
)