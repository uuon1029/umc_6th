package com.example.umc_6th.Retrofit

data class BoardSearchHotResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: Result
) {
    data class Result(
        val boardList: List<Board>,
        val listSize: Int,
        val totalPage: Int,
        val totalElements: Int,
        val isFirst: Boolean,
        val isLast: Boolean
    ) {
        data class Board(
            val title: String,
            val content: String,
            val pinCount: Int,
            val likeCount: Int,
            val boardDate: String,
            val userNickName: String,
            val picPreview: Any
        )
    }
}