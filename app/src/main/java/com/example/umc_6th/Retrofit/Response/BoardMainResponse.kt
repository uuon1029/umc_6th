package com.example.umc_6th.Retrofit

import com.example.umc_6th.Retrofit.Data.MainBoard
import com.google.gson.annotations.SerializedName

data class BoardMainResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: Result
) {
    data class Result(
        @SerializedName("boardMajorList") val boardMajorList: ArrayList<MainBoard>,
        @SerializedName("boardHotList") val boardHotList: ArrayList<MainBoard>,
        @SerializedName("boardAllList") val boardAllList: ArrayList<MainBoard>
    )
}