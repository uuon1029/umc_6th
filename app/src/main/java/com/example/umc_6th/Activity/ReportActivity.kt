package com.example.umc_6th

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_6th.databinding.ActivityReportBinding


class ReportActivity : AppCompatActivity() {

    lateinit var binding: ActivityReportBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //기타 클릭 유무 => boolean
        var etc = false

        //기타 영역 클릭 시 나타나는 기타 사유 작성 기능
        binding.etcButton.setOnClickListener {
            if(etc){
                etc = true
                binding.etcEt.visibility = View.VISIBLE
            }
            else{
                etc = false
                binding.etcEt.visibility = View.INVISIBLE
            }
        }

        //신고하기 버튼 클릭 시 신고 완료 액티비티로 전환
        binding.reportButton.setOnClickListener{
            val intent = Intent(this@ReportActivity, ReportCompleteActivity::class.java)
            startActivity(intent)
        }
    }
}