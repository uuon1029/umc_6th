package com.example.umc_6th.Retrofit.DataClass

data class User(
    val userId : Int,
    val name : String,
    val nickName : String,
    val account : String,
    val createdAt : String,
    val pic : Int,
    val status : String,
    val warn : Int,
    val report : Int,
    val stop : Int
)
