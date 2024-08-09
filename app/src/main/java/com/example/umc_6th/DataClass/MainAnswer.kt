package com.example.umc_6th

data class MainAnswer(
    var main_answer_img: Int? = R.drawable.ic_circle_main_40,
    var main_answer_name: String,
    var main_answer_date: Int,
    var main_answer_body: String,
    var main_answer_imglist:ArrayList<Int>,
    var main_answer_sublist:ArrayList<SubAnswer>
)
