package com.example.umc_6th

import java.sql.Date

data class Config(
    var type : String? = "",
    var title : String? = "",
    var content : String? = "",
    var date : Int = 0
    // date 타입 변경 필요 임시 설정
)
