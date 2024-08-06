package com.example.umc_6th.Retrofit

data class CommentFindReponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: Result
) {
    data class Result(
        val id: Int,
        val userId: Int,
        val boardId: Int,
        val comment: String,
        val pic: List<String>
    )
}