package com.example.umc_6th.Retrofit

import com.google.gson.annotations.SerializedName

data class BoardMainResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: Result
) {
    data class Result(
        @SerializedName("boardMajorList") val boardMajorList: List<BoardMajor>,
        @SerializedName("boardHotList") val boardHotList: List<BoardHot>,
        @SerializedName("boardAllList") val boardAllList: List<BoardAll>
    ) {
        data class BoardMajor(
            @SerializedName("title") val title: String,
            @SerializedName("likeCount") val likeCount: Int,
            @SerializedName("pinCount") val pinCount: Int,
            @SerializedName("isHavingPic") val isHavingPic: Boolean
        )

        data class BoardHot(
            @SerializedName("title") val title: String,
            @SerializedName("likeCount") val likeCount: Int,
            @SerializedName("pinCount") val pinCount: Int,
            @SerializedName("isHavingPic") val isHavingPic: Boolean
        )

        data class BoardAll(
            @SerializedName("title") val title: String,
            @SerializedName("likeCount") val likeCount: Int,
            @SerializedName("pinCount") val pinCount: Int,
            @SerializedName("isHavingPic") val isHavingPic: Boolean
        )
    }
}