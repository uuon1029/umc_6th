package com.example.umc_6th.Retrofit

import com.google.gson.annotations.SerializedName

data class NoticeDetailResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: Result
) {
    data class Result(
        @SerializedName("id") val id: Int,
        @SerializedName("title") val title: String,
        @SerializedName("content") val content: String,
        @SerializedName("createdAt") val createdAt: String,
        @SerializedName("updatedAt") val updatedAt: Any,
        @SerializedName("pictures") val pictures: ArrayList<Picture>?
    ) {
        data class Picture(
            @SerializedName("id") val id: Int,
            @SerializedName("pic") val pic: String
        )
    }
}