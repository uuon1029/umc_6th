package com.example.umc_6th.Retrofit.DataClass

import com.google.gson.annotations.SerializedName

data class PinComment(
    @SerializedName("id") val id: Int,
    @SerializedName("userId") val userId: Int,
    @SerializedName("userNickname") val userNickname: String,
    @SerializedName("comment") val comment: String,
    @SerializedName("pinCommentDate") val pinCommentDate: String,
    @SerializedName("pinLikeCount") val pinLikeCount: Int,
    @SerializedName("isLiked") val isLiked: Boolean,
    @SerializedName("pinPicList") val pinPicList: ArrayList<String>
)