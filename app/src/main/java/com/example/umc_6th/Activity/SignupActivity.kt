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
        initFocusET()
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)

//        initSpinner()

        signupActivity = this
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
                            initErrorView(binding.signupEditNickEt)
                            Log.d("retrofit/SignUp_nick-dup", response.body()!!.result.toString())
                        }
                        else{
                            binding.signupEditNickEt.isSelected = true
                            initErrorView(binding.signupEditNickEt)
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
                            initErrorView(binding.signupEditIdEt)
                            Log.d("retrofit/SignUp_id-dup", response.body()!!.result.toString())
                        }
                        else{
                            binding.signupEditIdEt.isSelected = true
                            initErrorView(binding.signupEditIdEt)
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
            &&(binding.signupEditCheckPwEt.text.length in 7..20)){
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
                if(View.text == "") {
                    View.isSelected = true
                }

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

    private fun initErrorView(View:TextView) {
        when(View.isSelected){
            true -> View.setBackgroundResource(R.drawable.bg_rectangle_gray20_radius_20_stroke_error_4)
            false -> View.setBackgroundResource(R.drawable.bg_rectangle_gray20_radius_20_stroke_gray40_2)
        }
    }
}