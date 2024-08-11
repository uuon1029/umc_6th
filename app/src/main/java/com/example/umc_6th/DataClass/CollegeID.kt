package com.example.umc_6th.Data

data class CollegeID(val id: Int, val name: String)

// 전공 목록 정의
val colleges = listOf(
    CollegeID(1,"경영대학"),
    CollegeID(2,"공과대학"),
    CollegeID(3,"자연과학대학·과학기술융합대학"),
    CollegeID(4,"디자인·건축융합대학"),
    CollegeID(5,"사회과학대학"),
    CollegeID(6,"생활과학대학"),
    CollegeID(7,"예술대학"),
    CollegeID(8,"융합대학"),
    CollegeID(9,"의과대학"),
    CollegeID(10,"인문대학")
)
