package com.example.umc_6th.Retrofit.Response

data class CommentRegisterResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: Int?
)