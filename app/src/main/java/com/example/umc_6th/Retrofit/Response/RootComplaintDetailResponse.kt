package com.example.umc_6th.Retrofit.Response

import com.example.umc_6th.Retrofit.DataClass.Complaint
import com.google.gson.annotations.SerializedName

data class RootComplaintDetailResponse(
    @SerializedName("isSuccess")val isSuccess : Boolean,
    @SerializedName("code")val code : String,
    @SerializedName("message")val message : String,
    @SerializedName("result")val result : Result
) {
    data class Result(
        @SerializedName("userId")val userId : Int,
        @SerializedName("boardId")val boardId : Int,
        @SerializedName("nickname")val nickname : String,
        @SerializedName("userPic")val userPic : String,
        @SerializedName("createdAt")val createdAt : String,
        @SerializedName("report")val report : Int,
        @SerializedName("commentContent")val commentContent : String,
        @SerializedName("commentPic")val commentPic : List<String>,
        @SerializedName("complaint")val complaint : ArrayList<Complaint>
    )
}
