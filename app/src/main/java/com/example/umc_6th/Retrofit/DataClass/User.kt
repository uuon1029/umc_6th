package com.example.umc_6th.Retrofit.DataClass

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("userId")val userId : Int,
    @SerializedName("name")val name : String,
    @SerializedName("nickName")val nickName : String,
    @SerializedName("account")val account : String,
    @SerializedName("createdAt")val createdAt : String,
    @SerializedName("pic")val pic : String,
    @SerializedName("status")val status : String,
    @SerializedName("warn")val warn : Int,
    @SerializedName("report")val report : Int,
    @SerializedName("stop")val stop : Int
)
