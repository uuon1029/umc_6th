package com.example.umc_6th.Retrofit.Response

import com.google.gson.annotations.SerializedName

data class getExampleResponse(
    @SerializedName("isSuccess")val isSuccees: Boolean,
    @SerializedName("code")val code: String,
    @SerializedName("message")val message: String,
    @SerializedName("result")val result: Result
) {
    data class Result(
        @SerializedName("question")val question: String,
        @SerializedName("answer")val answer: String,
        @SerializedName("exampleQuestion")val exampleQuestion: String,
        @SerializedName("correctAnswer")val correctAnswer: String
    )
}
