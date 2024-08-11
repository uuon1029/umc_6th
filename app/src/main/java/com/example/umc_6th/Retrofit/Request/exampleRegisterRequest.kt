package com.example.umc_6th.Retrofit.Request

import com.google.gson.annotations.SerializedName

data class exampleRegisterRequest(
    @SerializedName("tag")val tag: String,
    @SerializedName("exampleQuestion")val exampleQuestion: String,
    @SerializedName("correctAnswer")val correctAnswer: String
)
