package com.example.umc_6th.Retrofit

import com.example.umc_6th.Retrofit.DataClass.Board
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BoardSearchMajorResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: Result
) {
    data class Result(
        @SerializedName("boardList") val boardList: ArrayList<Board>,
        @SerializedName("listSize") val listSize: Int,
        @SerializedName("totalPage") val totalPage: Int,
        @SerializedName("totalElements") val totalElements: Int,
        @SerializedName("isFirst") val isFirst: Boolean,
        @SerializedName("isLast") val isLast: Boolean
    )
}