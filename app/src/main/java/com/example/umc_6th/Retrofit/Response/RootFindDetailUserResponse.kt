package com.example.umc_6th.Retrofit.Response

import com.example.umc_6th.Retrofit.DataClass.AdminUserProfileBoard
import com.example.umc_6th.Retrofit.DataClass.AdminUserProfileComment
import com.google.gson.annotations.SerializedName

data class RootFindDetailUserResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: Result
) {
    data class Result(
        @SerializedName("userId") val userId: Int,
        @SerializedName("nickName") val nickName: String,
        @SerializedName("name") val name: String,
        @SerializedName("pic") val pic: Any,
        @SerializedName("warn") val warn: Int,
        @SerializedName("report") val report: Int,
        @SerializedName("stop") val stop: Int,
        @SerializedName("boards") val boards: ArrayList<AdminUserProfileBoard>,
        @SerializedName("boardReportCount") val boardReportCount: Int,
        @SerializedName("pins") val pins: ArrayList<AdminUserProfileComment>,
        @SerializedName("pinsReportCount") val pinsReportCount: Int
    )
}