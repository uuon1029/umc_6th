package com.example.umc_6th.Retrofit

data class NoticeDetailResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: Result
) {
    data class Result(
        val id: Int,
        val title: String,
        val content: String,
        val createdAt: String,
        val updatedAt: Any,
        val pictures: List<Picture>
    ) {
        data class Picture(
            val id: Int,
            val pic: String
        )
    }
}