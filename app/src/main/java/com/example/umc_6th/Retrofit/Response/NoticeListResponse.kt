package com.example.umc_6th.Retrofit

import com.example.umc_6th.Retrofit.DataClass.Announcement

data class NoticeListResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: ArrayList<Announcement>
)