package com.example.umc_6th

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.umc_6th.Adapter.SignupCategorySpinnerAdapter
import com.example.umc_6th.Retrofit.CheckNicknameResponse
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.Response.NickNameDupResponse
import com.example.umc_6th.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignupBinding

    val outCategoryList = listOf(
        "전공을 선택해 주세요.",
        "경영대학",
        "공과대학",
        "자연과학대학•과학기술융합대학",
        "디자인•건축융합대학",
        "사회과학대학",
        "생활과학대학",
        "예술대학",
        "융합대학",
        "의과대학",
        "인문대학",
    )

    val categoryMap = mapOf(
        "경영대학" to listOf("경영정보학전공", "경영학전공", "공공경영•복지", "글로벌경영학전공", "앙트러프러너십", "회계학전공"),
        "공과대학" to listOf("건설환경공학전공", "기계자동차공학전공", "나노반도체공학전공", "미래형자동차전공", "반도체특화전공", "산업경영공학전공", "산업경영•산업안전공학부", "수소•에너지융합", "안전공학전공", "의공학전공", "재료공학전공", "전기전자공학전공", "조선해양공학전공", "항공우주공학전공", "화학공학전공", "AI융합전공", "IT융합전공"),
        "자연과학대학•과학기술융합대학" to listOf("나노에너지화학전공", "데이터응용수학전공", "물리학전공", "생명과학부", "생명과학전공", "생활체육전공", "수학전공", "스포츠과학부", "운동건강관리전공", "의생명과학전공", "화학전공"),
        "디자인•건축융합대학" to listOf("건축공학전공", "건축학부", "건축학전공", "디지털콘텐츠디자인학전공", "산업디자인학전공", "시각디자인학전공", "실내공간디자인학전공"),
        "사회과학대학" to listOf("경제학전공", "경찰학전공", "국제관계학전공", "국제지역•통상학전공", "글로벌마이스(MICE)", "기후변화융합전공", "법학전공", "사회•복지학전공", "행정학전공"),
        "생활과학대학" to listOf("글로벌스포츠웨어학전공", "식품영양학전공", "아동•가정복지학전공", "아동교육및상담전공", "의류학전공", "주거환경학전공", "청소년상담심리학전공"),
        "예술대학" to listOf("관현악전공", "동양화전공", "문화예술전공", "서양화전공", "섬유디자인학전공", "성악전공", "입체조형예술전공", "조소전공", "피아노전공", "회화•미디어아트전공"),
        "융합대학" to listOf("공동체혁신전공", "미래모빌리티전공", "스마트기계설계해석전공", "스마트도시•건설전공", "스마트제조ICT전공", "이차전지융합전공", "지능로봇전공", "저탄소그린에너지전공", "E-Mobility전공"),
        "의과대학" to listOf("간호학전공", "의예과", "의학과"),
        "인문대학" to listOf("국어국문학전공", "국제상거래커뮤니케이션학전공", "글로벌메디컬마케팅", "스페인•중남미학전공", "역사•문화학전공", "영어영문학전공", "일본어•일본학전공", "중국어•중국학전공", "철학•상담전공", "프랑스어•프랑스학전공", "한국어문학전공")
    )

    companion object {
        var signupActivity: SignupActivity? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initSetOnClickListener()

        initSpinner()

        signupActivity = this
    }

    private fun initSetOnClickListener() {
        binding.signupBackBtn.setOnClickListener {
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
        }

        binding.signupWelcomeBtn.setOnClickListener {
            if (binding.signupCategorySpinner.selectedItemPosition == 0) {
                Toast.makeText(this, "전공을 선택해 주세요.", Toast.LENGTH_SHORT).show()
            } else {
                val i = Intent(this, SignupCompleteActivity::class.java)
                startActivity(i)
            }
        }

        binding.signupNickBtn.setOnClickListener {
            val nickname = binding.signupEditNickEt.text.toString().trim()
            if (nickname.isNotEmpty()) {
                getNickNameDup(nickname)
            } else {
                Toast.makeText(this, "닉네임을 입력해 주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getNickNameDup(nickname: String) {
        CookieClient.service.checkNicknameAvailability(nickname).enqueue(object :
            retrofit2.Callback<NickNameDupResponse> {
            override fun onResponse(call: retrofit2.Call<NickNameDupResponse>, response: retrofit2.Response<NickNameDupResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val responseData = response.body()!!
                    if (responseData.isSuccess) {
                        if (responseData.result) {
                            binding.signupEditNickCheckTv.apply {
                                text = "이미 사용 중이에요"
                                setTextColor(ContextCompat.getColor(context, R.color.error_color))
                                visibility = View.VISIBLE
                            }
                            binding.signupNickFailCheckIg.visibility = View.VISIBLE
                            binding.signupEditNickEt.setBackgroundResource(R.drawable.bg_rectangle_errorcolor_radius_20)
                        } else {
                            binding.signupEditNickCheckTv.apply {
                                text = "사용 가능한 닉네임"
                                setTextColor(ContextCompat.getColor(context, R.color.gray60))
                                visibility = View.VISIBLE
                            }
                            binding.signupNickCheckIg.visibility = View.VISIBLE
                            binding.signupNickFailCheckIg.visibility = View.GONE
                            binding.signupEditNickEt.setBackgroundResource(R.drawable.bg_rectangle_gray20_radius_20_stroke_gray40_2)
                        }
                    } else {
                        // 응답 실패 처리
                        Toast.makeText(this@SignupActivity, "응답 실패: ${responseData.message}", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // 응답이 성공적이지 않음
                    Toast.makeText(this@SignupActivity, "서버 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: retrofit2.Call<NickNameDupResponse>, t: Throwable) {
                // 네트워크 오류 처리
                Log.e("SignupActivity", "Network error: ${t.message}")
                Toast.makeText(this@SignupActivity, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initSpinner() {
        val gray60 = ContextCompat.getColor(this, R.color.gray60)
        val textSizeSp = 13f

        val categorySpinnerAdapter = SignupCategorySpinnerAdapter(this, R.layout.item_spinner, outCategoryList)
        binding.signupCategorySpinner.adapter = categorySpinnerAdapter

        binding.signupCategorySpinner.setSelection(0, false)

        binding.signupCategorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedCategory = parent.getItemAtPosition(position) as String
                if (selectedCategory != "전공을 선택해 주세요.") {
                    updateDepartmentSpinner(selectedCategory)
                } else {
                    binding.signupDepartmentSpinner.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // 선택된 전공이 없을 때 처리
            }
        }
    }

    private fun updateDepartmentSpinner(category: String) {
        val departments = categoryMap[category] ?: emptyList()

        val departmentSpinnerAdapter = SignupCategorySpinnerAdapter(this, R.layout.item_spinner, departments)
        binding.signupDepartmentSpinner.adapter = departmentSpinnerAdapter

        binding.signupDepartmentSpinner.visibility = if (departments.isNotEmpty()) View.VISIBLE else View.GONE
    }
}