package com.example.umc_6th.Retrofit.Response

import com.example.umc_6th.Data.AdminReportBoard
import com.google.gson.annotations.SerializedName

data class AdminReportBoardResponse (
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: Result
) {
    data class Result(
        @SerializedName("listSize") val listSize: Int,
        @SerializedName("totalPage") val totalPage: Int,
        @SerializedName("totalElements") val totalElements: Int,
        @SerializedName("isFirst") val isFirst: Boolean,
        @SerializedName("isLast") val isLast: Boolean,
        @SerializedName("adminReportBoardList") val adminReportBoardList: ArrayList<AdminReportBoard>
    )
}