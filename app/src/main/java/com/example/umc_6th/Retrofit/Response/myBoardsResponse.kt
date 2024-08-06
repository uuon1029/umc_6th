package com.example.umc_6th.Retrofit

import com.google.gson.annotations.SerializedName

data class myBoardsResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: Result
) {
    data class Result(
        val totalElements: Int,
        val totalPages: Int,
        val size: Int,
        val content: List<Content>,
        val number: Int,
        val sort: Sort,
        val first: Boolean,
        val last: Boolean,
        val numberOfElements: Int,
        val pageable: Pageable,
        val empty: Boolean
    ) {
        data class Content(
            val boardId: Int,
            val header: String,
            val createdAt: String,
            val title: String,
            val content: String
        )

        data class Pageable(
            val pageNumber: Int,
            val pageSize: Int,
            val sort: Sort,
            val offset: Int,
            val paged: Boolean,
            val unpaged: Boolean
        ) {
            data class Sort(
                val empty: Boolean,
                val sorted: Boolean,
                val unsorted: Boolean
            )
        }

        data class Sort(
            val empty: Boolean,
            val sorted: Boolean,
            val unsorted: Boolean
        )
    }
}