package com.example.umc_6th

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.Request.SignupRequest
import com.example.umc_6th.Retrofit.Response.ResultBooleanResponse
import com.example.umc_6th.Retrofit.SignupResponse
import com.example.umc_6th.databinding.ActivitySignupBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignupBinding

    val outCategoryList = listOf(
        "전공을 선택해 주세요.",
        "경영대학",
        "공과대학",
        "과학기술융합대학",
        "디자인•건축융합대학",
        "사회과학대학",
        "생활과학대학",
        "예술대학",
        "융합대학",
        "의과대학",
        "인문대학",
        "자연과학대학"
    )

    val categoryMap = mapOf(
        "경영대학" to listOf("경영정보학전공", "경영학전공", "공공경영•복지", "글로벌경영학전공", "앙트러프러너십", "회계학전공"),
        "공과대학" to listOf("건설환경공학전공", "기계자동차공학전공", "나노반도체공학전공", "미래형자동차전공", "반도체특화전공", "산업경영공학전공", "산업경영•산업안전공학부", "수소•에너지융합", "안전공학전공", "의공학전공", "재료공학전공", "전기전자공학전공", "조선해양공학전공", "항공우주공학전공", "화학공학전공", "AI융합전공", "IT융합전공"),
        "과학기술융합대학" to listOf(),
        "디자인•건축융합대학" to listOf("건축공학전공", "건축학부", "건축학전공", "디지털콘텐츠디자인학전공", "산업디자인학전공", "시각디자인학전공", "실내공간디자인학전공"),
        "사회과학대학" to listOf("경제학전공", "경찰학전공", "국제관계학전공", "국제지역•통상학전공", "글로벌마이스(MICE)", "기후변화융합전공", "법학전공", "사회•복지학전공", "행정학전공"),
        "생활과학대학" to listOf("글로벌스포츠웨어학전공", "식품영양학전공", "아동•가정복지학전공", "아동교육및상담전공", "의류학전공", "주거환경학전공", "청소년상담심리학전공"),
        "예술대학" to listOf("관현악전공", "동양화전공", "문화예술전공", "서양화전공", "섬유디자인학전공", "성악전공", "입체조형예술전공", "조소전공", "피아노전공", "회화•미디어아트전공"),
        "융합대학" to listOf("공동체혁신전공", "미래모빌리티전공", "스마트기계설계해석전공", "스마트도시•건설전공", "스마트제조ICT전공", "이차전지융합전공", "지능로봇전공", "저탄소그린에너지전공", "E-Mobility전공"),
        "의과대학" to listOf("간호학전공", "의예과", "의학과"),
        "인문대학" to listOf("국어국문학전공", "국제상거래커뮤니케이션학전공", "글로벌메디컬마케팅", "스페인•중남미학전공", "역사•문화학전공", "영어영문학전공", "일본어•일본학전공", "중국어•중국학전공", "철학•상담전공", "프랑스어•프랑스학전공", "한국어문학전공"),
        "자연과학대학" to listOf("나노에너지화학전공", "데이터응용수학전공", "물리학전공", "생명과학부", "생명과학전공", "생활체육전공", "수학전공", "스포츠과학부", "운동건강관리전공", "의생명과학전공", "화학전공")
    )

    companion object {
        var signupActivity: SignupActivity? = null
    }

    var major_id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initSetOnClickListener()
        initFocusET()

        initSpinner()

        signupActivity = this
    }

    private fun initFocusET() {
        focusView(binding.signupEditNameEt)
        focusView(binding.signupEditNickEt)
        focusView(binding.signupEditIdEt)
        binding.signupEditPwEt.onFocusChangeListener = object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                if(hasFocus) {
                    binding.signupEditPwEt.setBackgroundResource(R.drawable.bg_rectangle_gray20_radius_20_stroke_black_5)
                }else {
                    binding.signupEditPwEt.setBackgroundResource(R.drawable.bg_rectangle_gray20_radius_20_stroke_gray40_2)
                }
                checkPw()
            }
        }
        binding.signupEditCheckPwEt.onFocusChangeListener = object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                if(hasFocus) {
                    binding.signupEditCheckPwEt.setBackgroundResource(R.drawable.bg_rectangle_gray20_radius_20_stroke_black_5)
                }else {
                    binding.signupEditCheckPwEt.setBackgroundResource(R.drawable.bg_rectangle_gray20_radius_20_stroke_gray40_2)
                }
                checkPw()
            }
        }
        focusView(binding.signupEditPhoneEt)
        focusView(binding.signupEditAuthEt)
    }

    private fun checkPw() {
        if((binding.signupEditCheckPwEt.text.toString() == binding.signupEditPwEt.text.toString())
            &&(binding.signupEditCheckPwEt.text.length in 8..20)){
            binding.signupEditCheckPwEt.isSelected = false
        } else {
            binding.signupEditCheckPwEt.isSelected = true
        }
        if(binding.signupEditCheckPwEt.isSelected){
            binding.signupEditCheckPwEt.setBackgroundResource(R.drawable.bg_rectangle_gray20_radius_20_stroke_error_4)
        }else{
            binding.signupEditCheckPwEt.setBackgroundResource(R.drawable.bg_rectangle_gray20_radius_20_stroke_gray40_2)
        }
    }

    private fun focusView(View:TextView) {
        View.onFocusChangeListener = object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                if(View.isSelected){
                    View.setBackgroundResource(R.drawable.bg_rectangle_gray20_radius_20_stroke_error_4)
                }else if(hasFocus) {
                    View.setBackgroundResource(R.drawable.bg_rectangle_gray20_radius_20_stroke_black_5)
                }else {
                    View.setBackgroundResource(R.drawable.bg_rectangle_gray20_radius_20_stroke_gray40_2)
                }
            }
        }
    }

    private fun errorView(View:TextView) {
        View.setBackgroundResource(R.drawable.bg_rectangle_gray20_radius_20_stroke_error_4)
        View.isSelected = true
    }

    private fun initSetOnClickListener() {
        binding.signupBackBtn.setOnClickListener {
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
        }

        binding.signupNickBtn.setOnClickListener {
            CookieClient.service.getNickNameDup(binding.signupEditNickEt.text.toString()).enqueue(object : Callback<ResultBooleanResponse>{
                override fun onResponse(
                    call: Call<ResultBooleanResponse>,
                    response: Response<ResultBooleanResponse>
                ) {
                    Log.d("retrofit/SignUp_nick-dup", response.body().toString())

                    if (response.body() != null){
                        if (!response.body()!!.result){
                            binding.signupEditNickEt.isSelected = false
                            Log.d("retrofit/SignUp_nick-dup", response.body()!!.result.toString())
                        }
                        else{
                            errorView(binding.signupEditNickEt)
                        }
                    }
                }

                override fun onFailure(call: Call<ResultBooleanResponse>, t: Throwable) {

                }
            })
        }

        binding.signupEditIdEt.setOnClickListener {
            CookieClient.service.getAccountDup(binding.signupEditIdEt.text.toString()).enqueue(object : Callback<ResultBooleanResponse>{
                override fun onResponse(
                    call: Call<ResultBooleanResponse>,
                    response: Response<ResultBooleanResponse>
                ) {
                    Log.d("retrofit/SignUp_id-dup", response.body().toString())

                    if (response.body() != null){
                        if (!response.body()!!.result){
                            binding.signupEditIdEt.isSelected = false
                            Log.d("retrofit/SignUp_id-dup", response.body()!!.result.toString())
                        }
                        else{
                            errorView(binding.signupEditIdEt)
                        }
                    }
                }

                override fun onFailure(call: Call<ResultBooleanResponse>, t: Throwable) {

                }
            })
        }

        binding.signupAuthBtn.setOnClickListener {

        }



        binding.signupWelcomeBtn.setOnClickListener {

            if(
                !binding.signupEditNameEt.isSelected
                &&!binding.signupEditNickEt.isSelected
                &&!binding.signupEditIdEt.isSelected
                &&!binding.signupEditPwEt.isSelected
                &&!binding.signupEditCheckPwEt.isSelected
                &&(major_id != 0)
                &&!binding.signupEditPhoneEt.isSelected
                &&!binding.signupEditAuthEt.isSelected
            ){
                val request = SignupRequest(
                    binding.signupEditNameEt.text.toString(),
                    binding.signupEditNickEt.text.toString(),
                    binding.signupEditIdEt.text.toString(),
                    binding.signupEditPwEt.text.toString(),
                    binding.signupEditCheckPwEt.text.toString(),
                    major_id,
                    binding.signupEditPhoneEt.text.toString()
                )
                CookieClient.service.postSignUp(request).enqueue(object : Callback<SignupResponse>{
                    override fun onResponse(
                        call: Call<SignupResponse>,
                        response: Response<SignupResponse>
                    ) {
                        if(response.body()?.code == "COMMON200"){
                            val i = Intent(this@SignupActivity, SignupCompleteActivity::class.java)
                            startActivity(i)
                        }
                    }

                    override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                    }
                })
            }


//            if (binding.signupCategorySpinner.selectedItemPosition == 0){
//                Toast.makeText(this, "전공을 선택해 주세요.", Toast.LENGTH_SHORT).show()
//            } else {
//                val i = Intent(this, SignupCompleteActivity::class.java)
//                startActivity(i)
//            }
        }
    }

    private fun initSpinner() {

        val gray60 = ContextCompat.getColor(this, R.color.gray60)
        val textSizeSp = 13f

        val spinnerAdapter = object : ArrayAdapter<String>(this, R.layout.item_spinner, outCategoryList) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent) as TextView
                if (position == 0) {
                    view.text = "전공을 선택해 주세요."
                    view.setTextColor(gray60)
                    view.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeSp)
                } else{
                    view.setTextColor(gray60)
                }
                return view
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val inflater = layoutInflater
                val view = convertView ?: inflater.inflate(R.layout.item_spinner_dropdown, parent, false)
                val textView = view.findViewById<TextView>(R.id.spinner_item_text)
                textView.text = getItem(position)
                textView.setTextColor(gray60)
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeSp)
                return view
            }
        }

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.signupCategorySpinner.adapter = spinnerAdapter

        binding.signupCategorySpinner.setSelection(0, false)

        binding.signupCategorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedCategory = parent.getItemAtPosition(position) as String
                if (selectedCategory != "전공을 선택해 주세요.") {
                    updateDepartmentSpinner(selectedCategory)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // 선택된 전공이 없을 때 처리
            }
        }
    }

    private fun updateDepartmentSpinner(category: String) {
        val departments = categoryMap[category] ?: emptyList()
        val departmentAdapter = ArrayAdapter(this, R.layout.item_spinner, departments)
        departmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.signupDepartmentSpinner.adapter = departmentAdapter
    }
}