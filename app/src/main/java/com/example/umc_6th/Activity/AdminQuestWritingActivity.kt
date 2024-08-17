package com.example.umc_6th.Activity

import android.R
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_6th.MainActivity
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.Request.AnnouncementRegisterRequest
import com.example.umc_6th.Retrofit.Response.RootQNAViewResponse
import com.example.umc_6th.Retrofit.RetrofitClient
import com.example.umc_6th.databinding.ActivityAdminQuestWritingBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.umc_6th.Retrofit.Request.FAQRegisterRequest
import com.example.umc_6th.Retrofit.Request.FAQModifyRequest


class AdminQuestWritingActivity : AppCompatActivity() {
    lateinit var binding: ActivityAdminQuestWritingBinding

    private var selectedCategory: String = "검색어" //기본 카테고리값 설정
    private var faqId: Int = 0 //임시 faq_id 나중에 추출해야함.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminQuestWritingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.adminQuestWritingBackIv.setOnClickListener {
            finish()
        }

        //intent 통해 수정 내용들 전달 받았는지 확인 - AdminQuestActivity 확인할 것
        val receivedCategory = intent.getStringExtra("category")
        val receivedTitle = intent.getStringExtra("title")
        val receivedContent = intent.getStringExtra("content")
        val receivedFAQId = intent.getIntExtra("faqId", 0)
        if (receivedCategory != null && receivedTitle != null && receivedContent != null) {
            binding.adminQuestWritingBtnTv.text = "수정 완료"
            selectedCategory = receivedCategory
            binding.adminQuestWritingTitleEt.setText(receivedTitle)
            binding.adminQuestWritingBodyEt.setText(receivedContent)
            faqId = receivedFAQId

        }

        //스피너에 들어갈 카테고리
        val items = listOf("검색어", "커뮤니티", "문제")
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, items)
        //드롭다운 리소스 지정 (기본)
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        //스피너에 어댑터 연결
        binding.adminQuestWritingCatregory.adapter = adapter
        // 스피너의 기본값을 receivedCategory로 설정
        val categoryIndex = items.indexOf(receivedCategory)
        if (categoryIndex >= 0) {
            binding.adminQuestWritingCatregory.setSelection(categoryIndex)
        }
        //스피너 선택 이벤트 처리
        binding.adminQuestWritingCatregory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                // 선택된 아이템의 텍스트 추출
                selectedCategory = parent.getItemAtPosition(position).toString()
                // 여기서 원하는 작업 수행 (예: Toast 메시지 출력)
                Toast.makeText(this@AdminQuestWritingActivity, "선택된 항목: $selectedCategory", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // 아무것도 선택되지 않았을 때의 동작 (필요시 구현)
            }
        }



        //자주 묻는 질문 서버에 등록 및 수정 처리
        binding.adminQuestWritingBtnIv.setOnClickListener {
            if(binding.adminQuestWritingBtnTv.text == "수정 완료"){
                FAQModify()
            }
            else{
                FAQregister()
            }
        }

    }

    //자주 묻는 질문 서버에 등록
    private fun FAQregister(){
        val title = binding.adminQuestWritingTitleEt.text.toString()
        val content = binding.adminQuestWritingBodyEt.text.toString()

        val sp = getSharedPreferences("Auth", MODE_PRIVATE)
        val accessToken = sp.getString("AccessToken", "").toString()

        val request = FAQRegisterRequest(selectedCategory, title, content)

        val call = RetrofitClient.service.postRootFAQRegister(
            accessToken, request
        )

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("AdminQuestWritingActivity", "자주 묻는 질문 등록 성공")
                    // 성공 처리 로직 추가
                    finish() // 액티비티 종료
                } else {
                    Log.d("AdminQuestWritingActivity", "자주 묻는 질문 등록 실패: ${response.code()} , 에러메시지: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("AdminQuestWritingActivity", "자주 묻는 질문 등록 에러: ${t.message}")
            }
        })
    }

    //자주 묻는 질문 수정
    private fun FAQModify(){
        val title = binding.adminQuestWritingTitleEt.text.toString()
        val content = binding.adminQuestWritingBodyEt.text.toString()

        val sp = getSharedPreferences("Auth", MODE_PRIVATE)
        val accessToken = sp.getString("AccessToken", "").toString()

        val request = FAQModifyRequest(selectedCategory, title, content)

        val call = RetrofitClient.service.patchRootFAQModify(
            accessToken, faqId, request
        )

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("AdminQuestWritingActivity", "자주 묻는 질문 수정 성공")
                    // 성공 처리 로직 추가
                    finish() // 액티비티 종료
                } else {
                    Log.d("AdminQuestWritingActivity", "자주 묻는 질문 수정 실패: ${response.code()} , 에러메시지: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("AdminQuestWritingActivity", "자주 묻는 질문 수정 에러: ${t.message}")
            }
        })

    }
}