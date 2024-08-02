package com.example.umc_6th.Retrofit

data class FindProfileResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: Result
) {
    data class Result(
        val userId: Int,
        val nickName: String,
        val description: String,
        val pic: String,
        val board: List<Board>?,
        val pinBoard: List<PinBoard>?
    ) {
        data class Board(
            val id: Int,
            val title: String,
            val likeCount: Int,
            val pinCount: Int
        )

        data class PinBoard(
            val id: Int,
            val title: String,
            val likeCount: Int,
            val pinCount: Int
        )
    }
}