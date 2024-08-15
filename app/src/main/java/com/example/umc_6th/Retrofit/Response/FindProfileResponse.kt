package com.example.umc_6th.Retrofit

import com.example.umc_6th.Retrofit.DataClass.ProfileBoard
import com.google.gson.annotations.SerializedName

data class FindProfileResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: Result
) {
    data class Result(
        @SerializedName("userId") val userId: Int,
        @SerializedName("nickName") val nickName: String,
        @SerializedName("description") val description: String,
        @SerializedName("pic") val pic: String,
        @SerializedName("board") val board: ArrayList<ProfileBoard>?,
        @SerializedName("pinBoard") val pinBoard: ArrayList<ProfileBoard>?
    )
}