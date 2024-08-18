package com.example.umc_6th.Retrofit.Response

import com.example.umc_6th.Data.AdminReport
import com.example.umc_6th.Data.AdminReportBoard
import com.google.gson.annotations.SerializedName

data class RootComplaintBoardsResponse (
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: Result
) {
    data class Result(
        @SerializedName("totalElements") val totalElements: Int,
        @SerializedName("totalPage") val totalPage: Int,
        @SerializedName("listSize") val listSize: Int,
        @SerializedName("isFirst") val isFirst: Boolean,
        @SerializedName("isLast") val isLast: Boolean,
        @SerializedName("adminReportList") val adminReportList: ArrayList<AdminReport>
    )
}