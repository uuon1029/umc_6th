package com.example.umc_6th.Retrofit

import com.example.umc_6th.Retrofit.DataClass.Pin
import com.google.gson.annotations.SerializedName

data class BoardViewResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: Result
) {
    data class Result(
        @SerializedName("userId") val userId: Int,
        @SerializedName("userNickname") val userNickname: String,
        @SerializedName("userProfilePic") val userProfilePic: String,
        @SerializedName("title") val title: String,
        @SerializedName("content") val content: String,
        @SerializedName("pinCount") val pinCount: Int,
        @SerializedName("likeCount") val likeCount: Int,
        @SerializedName("boardDate") val boardDate: String,
        @SerializedName("boardPic") val boardPic: ArrayList<String>,
        @SerializedName("pinList") val pinList: ArrayList<Pin>
    )
}