package com.example.umc_6th.Retrofit.DataClass

import com.example.umc_6th.Retrofit.BoardViewResponse
import com.google.gson.annotations.SerializedName

data class Pin(
    @SerializedName("id") val id: Int,
    @SerializedName("userId") val userId: Int,
    @SerializedName("userNickname") val userNickname: String,
    @SerializedName("comment") val comment: String,
    @SerializedName("pinDate") val pinDate: String,
    @SerializedName("pinLikeCount") val pinLikeCount: Int,
    @SerializedName("pinCommentCount") val pinCommentCount: Int,
    @SerializedName("pinCommentList") val pinCommentList: ArrayList<PinComment>,
    @SerializedName("pinPictureList") val pinPictureList: ArrayList<String>
)