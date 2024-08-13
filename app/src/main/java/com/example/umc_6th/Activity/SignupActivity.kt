package com.example.umc_6th

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_6th.Adapter.CollegeSelectRVAdapter
import com.example.umc_6th.Adapter.MajorSelectRVAdapter
import com.example.umc_6th.Data.CollegeID
import com.example.umc_6th.Data.MajorID
import com.example.umc_6th.Data.colleges
import com.example.umc_6th.Data.majors
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
    private lateinit var collegeAdapter : CollegeSelectRVAdapter
    private lateinit var majorAdapter : MajorSelectRVAdapter

    companion object {
        var signupActivity: SignupActivity? = null
        var name = ""
    }

    var major_id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initSetOnClickListener()
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)

        initEtFocus()

        signupActivity = this
    }

    private fun editTextStatus(view: EditText, status: Int) {
        // status - { 0 : 기본, 1 : 입력 중, 2 : 에러 }
        when(status) {
            0 -> view.setBackgroundResource(R.drawable.bg_rectangle_gray20_radius_20_stroke_gray40_2)
            1 -> view.setBackgroundResource(R.drawable.bg_rectangle_gray20_radius_20_stroke_black_5)
            2 -> view.setBackgroundResource(R.drawable.bg_rectangle_gray20_radius_20_stroke_error_4)
        }
    }

    private fun initEtFocus(){
        // 이름
        binding.signupEditNameEt.setOnFocusChangeListener { v, hasFocus ->
            val view = binding.signupEditNameEt
            when(hasFocus){
                true -> {
                    view.isSelected = false
                    editTextStatus(view,1)
                }
                false -> {
                    binding.signupNameErrorTv.visibility = View.VISIBLE
                    binding.signupNameErrorIv.visibility = View.VISIBLE
                    binding.signupNameErrorTv.setTextColor(ContextCompat.getColor(this@SignupActivity,R.color.gray60))

                    if(view.text.isEmpty()) {
                        binding.signupNameErrorTv.text = "이름을 입력해 주세요."
                        binding.signupNameErrorIv.setImageResource(R.drawable.ic_check_error)
                        binding.signupNameErrorTv.setTextColor(ContextCompat.getColor(this@SignupActivity,R.color.error_color))

                        view.isSelected = true
                    } else {
                        binding.signupNameErrorTv.text = "이름이 확인 되었습니다."
                        binding.signupNameErrorIv.setImageResource(R.drawable.ic_check)
                        binding.signupNameErrorTv.setTextColor(ContextCompat.getColor(this@SignupActivity,R.color.gray60))

                        view.isSelected = false
                    }
                    when (view.isSelected) {
                        true -> editTextStatus(view,2)
                        false -> editTextStatus(view,0)
                    }
                }
            }
        }

        // 닉네임
        binding.signupEditNameEt.setOnFocusChangeListener { v, hasFocus ->
            val view = binding.signupEditNameEt
            when(hasFocus){
                true -> {
                    view.isSelected = false
                    editTextStatus(view,1)
                }
                false -> {
                    binding.signupNickErrorTv.visibility = View.VISIBLE
                    binding.signupNickErrorIv.visibility = View.VISIBLE

                    if(view.text.isEmpty()) {
                        binding.signupNickErrorTv.text = "닉네임을 입력해 주세요."
                        binding.signupNickErrorIv.setImageResource(R.drawable.ic_check_error)
                        binding.signupNickErrorTv.setTextColor(ContextCompat.getColor(this@SignupActivity,R.color.error_color))

                        view.isSelected = true
                    }
                    else {
                        binding.signupNickErrorTv.text = "닉네임 중복을 확인해 주세요."
                        binding.signupNickErrorIv.setImageResource(R.drawable.ic_check_error)
                        binding.signupNickErrorTv.setTextColor(ContextCompat.getColor(this@SignupActivity,R.color.error_color))

                        view.isSelected = true
                    }
                    when (view.isSelected) {
                        true -> editTextStatus(view,2)
                        false -> editTextStatus(view,0)
                    }
                }
            }
        }

        // 아이디
        binding.signupEditIdEt.setOnFocusChangeListener { v, hasFocus ->
            val view = binding.signupEditIdEt
            when(hasFocus){
                true -> {
                    view.isSelected = false
                    editTextStatus(view,1)
                }
                false -> {
                    binding.signupIdErrorTv.visibility = View.VISIBLE
                    binding.signupIdErrorIv.visibility = View.VISIBLE

                    if(view.text.isEmpty()) {
                        binding.signupIdErrorTv.text = "아이디를 입력해 주세요."
                        binding.signupIdErrorIv.setImageResource(R.drawable.ic_check_error)
                        binding.signupIdErrorTv.setTextColor(ContextCompat.getColor(this@SignupActivity,R.color.error_color))

                        view.isSelected = true
                    }
                    else {
                        binding.signupIdErrorTv.text = "아이디 중복을 확인해 주세요."
                        binding.signupIdErrorIv.setImageResource(R.drawable.ic_check_error)
                        binding.signupIdErrorTv.setTextColor(ContextCompat.getColor(this@SignupActivity,R.color.error_color))

                        view.isSelected = true
                    }
                    when (view.isSelected) {
                        true -> editTextStatus(view,2)
                        false -> editTextStatus(view,0)
                    }
                }
            }
        }

        // 비밀번호
        binding.signupEditPwEt.setOnFocusChangeListener { v, hasFocus ->
            val view = binding.signupEditPwEt
            when(hasFocus){
                true -> {
                    view.isSelected = false
                    editTextStatus(view,1)
                }
                false -> {
                    binding.signupPwErrorTv.visibility = View.VISIBLE
                    binding.signupPwErrorIv.visibility = View.VISIBLE

                    if(view.text.isEmpty()) {
                        binding.signupPwErrorTv.text = "비밀번호를 입력해 주세요."
                        binding.signupPwErrorIv.setImageResource(R.drawable.ic_check_error)
                        binding.signupPwErrorTv.setTextColor(ContextCompat.getColor(this@SignupActivity,R.color.error_color))

                        view.isSelected = true
                    }
                    else if(view.text.length < 8 || view.text.length > 20){
                        binding.signupPwErrorTv.text = "다시 입력해 주세요."
                        binding.signupPwErrorIv.setImageResource(R.drawable.ic_check_error)
                        binding.signupPwErrorTv.setTextColor(ContextCompat.getColor(this@SignupActivity,R.color.error_color))

                        view.isSelected = true
                    } else {
                        binding.signupPwErrorTv.text = "사용 가능한 비밀번호입니다."
                        binding.signupPwErrorIv.setImageResource(R.drawable.ic_check)
                        binding.signupPwErrorTv.setTextColor(ContextCompat.getColor(this@SignupActivity,R.color.error_color))

                        view.isSelected = false
                    }
                    when (view.isSelected) {
                        true -> editTextStatus(view,2)
                        false -> editTextStatus(view,0)
                    }
                }
            }
        }

        // 비밀번호 체크
        binding.signupEditCheckPwEt.setOnFocusChangeListener { v, hasFocus ->
            val view = binding.signupEditCheckPwEt
            when(hasFocus){
                true -> {
                    view.isSelected = false
                    editTextStatus(view,1)
                }
                false -> {
                    binding.signupCheckPwErrorTv.visibility = View.VISIBLE
                    binding.signupCheckPwErrorIv.visibility = View.VISIBLE

                    if(view.text.isEmpty()) {
                        binding.signupCheckPwErrorTv.text = "닉네임을 입력해 주세요."
                        binding.signupCheckPwErrorIv.setImageResource(R.drawable.ic_check_error)
                        binding.signupCheckPwErrorTv.setTextColor(ContextCompat.getColor(this@SignupActivity,R.color.error_color))

                        view.isSelected = true
                    }
                    else {
                        binding.signupCheckPwErrorTv.text = "닉네임 중복을 확인해 주세요."
                        binding.signupCheckPwErrorIv.setImageResource(R.drawable.ic_check_error)
                        binding.signupCheckPwErrorTv.setTextColor(ContextCompat.getColor(this@SignupActivity,R.color.error_color))

                        view.isSelected = true
                    }
                    when (view.isSelected) {
                        true -> editTextStatus(view,2)
                        false -> editTextStatus(view,0)
                    }
                }
            }
        }

        // 휴대폰 번호
//        binding.signupEditPhoneEt.setOnFocusChangeListener { v, hasFocus ->
//            val view = binding.signupEditPhoneEt
//            when(hasFocus){
//                true -> {
//                    view.isSelected = false
//                    editTextStatus(view,1)
//                }
//                false -> {
//                    binding.signupPhoneErrorTv.visibility = View.VISIBLE
//                    binding.signupPhoneErrorTv2.visibility = View.VISIBLE
//
//                    if(view.text.isEmpty()) {
//                        binding.signupPhoneErrorTv.text = "남은 시간:"
//                        binding.signupPhoneErrorTv2.text = ""
//                        view.isSelected = true
//                    }
//                    when (view.isSelected) {
//                        true -> editTextStatus(view,2)
//                        false -> editTextStatus(view,0)
//                    }
//                }
//            }
//        }

        // 인증번호
        binding.signupEditAuthEt.setOnFocusChangeListener { v, hasFocus ->
            val view = binding.signupEditAuthEt
            when(hasFocus){
                true -> {
                    view.isSelected = false
                    editTextStatus(view,1)
                }
                false -> {
                    binding.signupAuthErrorTv.visibility = View.VISIBLE
                    binding.signupAuthErrorIv.visibility = View.VISIBLE

                    if(view.text.isEmpty()) {
                        binding.signupAuthErrorTv.text = "닉네임을 입력해 주세요."
                        binding.signupAuthErrorIv.setImageResource(R.drawable.ic_check_error)
                        binding.signupAuthErrorTv.setTextColor(ContextCompat.getColor(this@SignupActivity,R.color.error_color))

                        view.isSelected = true
                    }
                    else {
                        binding.signupAuthErrorTv.text = "닉네임 중복을 확인해 주세요."
                        binding.signupAuthErrorIv.setImageResource(R.drawable.ic_check_error)
                        binding.signupAuthErrorTv.setTextColor(ContextCompat.getColor(this@SignupActivity,R.color.error_color))

                        view.isSelected = true
                    }
                    when (view.isSelected) {
                        true -> editTextStatus(view,2)
                        false -> editTextStatus(view,0)
                    }
                }
            }
        }
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

                        }
                    }
                }

                override fun onFailure(call: Call<ResultBooleanResponse>, t: Throwable) {

                }
            })
        }

        binding.signupAuthBtn.setOnClickListener {
            if(binding.signupEditPhoneEt.text.length != 11) {
                binding.signupEditPhoneEt.isSelected = true
            }
        }

        binding.signupMajorTv.setOnClickListener {
            if(binding.signupRvLayout.visibility == View.GONE) {
                binding.signupRvLayout.visibility = View.VISIBLE
                collegeAdapter = CollegeSelectRVAdapter(colleges)
                collegeAdapter.setClickListener(object : CollegeSelectRVAdapter.MyOnClickeListener{
                    override fun itemClick(college:CollegeID) {
                        binding.signupCollegeTv.text = college.name
                        binding.signupCollegeTv.visibility = View.VISIBLE

                        val majorList = majors.filter { (it.collegeId == college.id) }
                        majorAdapter = MajorSelectRVAdapter(majorList)
                        majorAdapter.setClickListener(object : MajorSelectRVAdapter.MyOnClickeListener{
                            override fun itemClick(major:MajorID) {
                                binding.signupCollegeTv.visibility = View.GONE
                                binding.signupRvLayout.visibility = View.GONE
                                binding.signupMajorTv.text = major.name
                                binding.signupMajorTv.setTextColor(ContextCompat.getColor(this@SignupActivity,R.color.black))
                                major_id = major.id
                            }
                        })
                        binding.signupMajorRv.adapter = majorAdapter
                        binding.signupMajorRv.layoutManager=
                            LinearLayoutManager(this@SignupActivity, LinearLayoutManager.VERTICAL, false)
                    }
                })
                binding.signupMajorRv.adapter = collegeAdapter
                binding.signupMajorRv.layoutManager=
                    LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            } else {
                binding.signupRvLayout.visibility = View.GONE
                binding.signupCollegeTv.visibility =View.GONE
            }
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
                        Log.d("retrofit_signup_request", request.toString())
                        if(response.body()?.code == "COMMON200"){
                            val i = Intent(this@SignupActivity, SignupCompleteActivity::class.java)
                            i.putExtra("name",binding.signupEditNameEt.text)
                            name = binding.signupEditNameEt.text.toString()
                            startActivity(i)
                        }
                    }

                    override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                    }
                })
            }
        }
    }
}