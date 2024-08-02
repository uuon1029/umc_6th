package com.example.umc_6th.Retrofit.Request

data class CommentModifyRequest(
    val id: String,
    val comment: String,
    val pic: List<String>
)