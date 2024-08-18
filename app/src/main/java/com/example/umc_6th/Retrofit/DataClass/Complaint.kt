package com.example.umc_6th.Retrofit.DataClass

import com.google.gson.annotations.SerializedName

data class Complaint(
    @SerializedName("userId")val userId : Int,
    @SerializedName("nickname")val nickname : String,
    @SerializedName("userPic")val userPic : Int,
    @SerializedName("createdAt")val createdAt : String,
    @SerializedName("complaintContent")val complaintContent : String
)
