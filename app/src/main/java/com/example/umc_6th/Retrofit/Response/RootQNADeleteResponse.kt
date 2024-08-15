package com.example.umc_6th.Retrofit.Response

import com.google.gson.annotations.SerializedName

data class RootQNADeleteResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: Result
) {
    data class Result(
        @SerializedName("qnaId") val qnaId: Int,
        @SerializedName("answeredAdminId") val answeredAdminId: Int,
        @SerializedName("deletedAnswerAt") val deletedAnswerAt: String
    )
}