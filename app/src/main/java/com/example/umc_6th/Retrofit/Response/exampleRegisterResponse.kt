package com.example.umc_6th.Retrofit.Response

import androidx.core.app.NotificationCompat.MessagingStyle.Message
import com.google.gson.annotations.SerializedName

data class exampleRegisterResponse(
    @SerializedName("isSuccess")val isSuccess: Boolean,
    @SerializedName("code")val code: String,
    @SerializedName("message")val message: String,
    @SerializedName("result")val result: Result
) {
    data class Result(
        @SerializedName("exampleId")val exampleId: Int,
        @SerializedName("answerId")val answerId: Int,
        @SerializedName("problem")val problem: String,
        @SerializedName("answer")val answer: String,
        @SerializedName("tag")val tag: String,
        @SerializedName("question")val question: String,
        @SerializedName("content")val content: String
    )
}
