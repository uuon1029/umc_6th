package com.example.umc_6th.Retrofit

import com.example.umc_6th.Retrofit.DataClass.Content
import com.example.umc_6th.Retrofit.DataClass.Pageable
import com.example.umc_6th.Retrofit.DataClass.Sort

data class FindProfileBoardsResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: Result
) {
    data class Result(
        val totalElements: Int,
        val totalPages: Int,
        val size: Int,
        val content: ArrayList<Content>,
        val number: Int,
        val sort: Sort,
        val first: Boolean,
        val last: Boolean,
        val numberOfElements: Int,
        val pageable: Pageable,
        val empty: Boolean
    )
}