package com.example.umc_6th

import java.time.LocalDate

data class Config(
    var type: String? = "",
    var title: String? = "",
    var content: String? = "",
    var date: String? = ""
    // date 타입 변경 필요 임시 설정
)
