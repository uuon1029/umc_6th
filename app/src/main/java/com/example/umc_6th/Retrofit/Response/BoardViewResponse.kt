package com.example.umc_6th.Retrofit

data class BoardViewResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: Result
) {
    data class Result(
        val userId: Int,
        val userNickname: String,
        val userProfilePic: String,
        val title: String,
        val content: String,
        val pinCount: Int,
        val likeCount: Int,
        val boardDate: String,
        val boardPic: List<String>,
        val pinList: List<Pin>
    ) {
        data class Pin(
            val id: Int,
            val userId: Int,
            val userNickname: String,
            val comment: String,
            val pinDate: String,
            val pinLikeCount: Int,
            val pinCommentCount: Int,
            val pinCommentList: List<PinComment>,
            val pinPictureList: List<String>
        ) {
            data class PinComment(
                val id: Int,
                val userId: Int,
                val userNickname: String,
                val comment: String,
                val pinCommentDate: String,
                val pinLikeCount: Int,
                val pinPicList: List<String>
            )
        }
    }
}