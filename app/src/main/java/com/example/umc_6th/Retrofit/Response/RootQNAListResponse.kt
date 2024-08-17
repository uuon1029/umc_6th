package com.example.umc_6th.Retrofit.Response

import com.example.umc_6th.Retrofit.DataClass.RootQnA
import com.google.gson.annotations.SerializedName

data class RootQnAListResponse(
    @SerializedName("isSuccess")val isSuccess : Boolean,
    @SerializedName("code")val code : String,
    @SerializedName("message")val message : String,
    @SerializedName("result")val result : Result
) {
    data class Result(
        @SerializedName("rootQNAList")val rootQNAList : ArrayList<RootQnA>,
        @SerializedName("listSize")val listSize : Int,
        @SerializedName("totalPage")val totalPage : Int,
        @SerializedName("totalElements")val totalElements : Int,
        @SerializedName("isFirst")val isFirst : Boolean,
        @SerializedName("isLast")val isLast : Boolean,
    )
}
