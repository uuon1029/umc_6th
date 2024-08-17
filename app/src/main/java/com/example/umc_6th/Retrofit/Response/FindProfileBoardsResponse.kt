package com.example.umc_6th.Retrofit

import com.example.umc_6th.Retrofit.DataClass.Content
import com.example.umc_6th.Retrofit.DataClass.Pageable
import com.example.umc_6th.Retrofit.DataClass.Sort
import com.google.gson.annotations.SerializedName

data class FindProfileBoardsResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: Result
) {
    data class Result(
        @SerializedName("totalElements") val totalElements: Int,
        @SerializedName("totalPages") val totalPages: Int,
        @SerializedName("size") val size: Int,
        @SerializedName("content") val content: ArrayList<Content>,
        @SerializedName("number") val number: Int,
        @SerializedName("sort") val sort: Sort,
        @SerializedName("first") val first: Boolean,
        @SerializedName("last") val last: Boolean,
        @SerializedName("numberOfElements") val numberOfElements: Int,
        @SerializedName("pageable") val pageable: Pageable,
        @SerializedName("empty") val empty: Boolean
    )
}