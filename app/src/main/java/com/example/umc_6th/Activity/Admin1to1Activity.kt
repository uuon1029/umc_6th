package com.example.umc_6th.Activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_6th.Adapter.Admin1to1RVAdapter
import com.example.umc_6th.MainActivity
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.DataClass.RootQnA
import com.example.umc_6th.Retrofit.Response.RootQnAListResponse
import com.example.umc_6th.databinding.ActivityAdmin1to1Binding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class Admin1to1Activity : AppCompatActivity(){
    lateinit var binding: ActivityAdmin1to1Binding

    private var  rootQNADatas = arrayListOf<RootQnA>()
    private var page: Int = 0
    private var qna_id : Int = 0
    val accessToken = MainActivity.accessToken

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdmin1to1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setupDropdown()

        selectedAll(page)

        binding.admin1to1BackIv.setOnClickListener {
            finish()
        }

        binding.admin1to1SearchIv.setOnClickListener {
            val i = Intent(this,Admin1to1SearchActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        when(binding.admin1to1SelectOptionSelectedTv.text.toString()){
            "전체" -> selectedAll(page)
            "답변 완료" -> selectedAnswer(page)
            "대기 중" -> selectedWaiting(page)
        }
        Log.d("Admin1to1","initRV")
    }

    private fun initRV() {
        val Admin1to1RVAdapter = Admin1to1RVAdapter(rootQNADatas)
        binding.admin1to1Rv.adapter = Admin1to1RVAdapter
        Admin1to1RVAdapter.setClickListener(object : Admin1to1RVAdapter.MyOnClickeListener{
            override fun itemClick(item: RootQnA) {
                when(item.status){
                    "대기 중" -> {
                        val i = Intent(this@Admin1to1Activity, Admin1to1EditActivity::class.java)
                        i.putExtra("QnA_id", item.id)
                        startActivity(i)
                    }

                    "답변 완료" -> {
                        val i = Intent(this@Admin1to1Activity, Admin1to1AnswerActivity::class.java)
                        i.putExtra("QnA_id", item.id)
                        startActivity(i)
                    }

                }
            }
        })
        binding.admin1to1Rv.layoutManager = LinearLayoutManager(this)
    }

    private fun setupDropdown() {
        binding.admin1to1SelectOptionCl.setOnClickListener {
            binding.admin1to1SelectOptionCl.visibility = View.GONE
            binding.admin1to1DropdownCl.visibility = View.VISIBLE
        }
        binding.admin1to1DropdownCl.setOnClickListener {
            binding.admin1to1SelectOptionCl.visibility = View.VISIBLE
            binding.admin1to1DropdownCl.visibility = View.GONE
        }

        binding.admin1to1DropdownAnswerCl.setOnClickListener {
            binding.admin1to1DropdownSelectedTv.text = "답변 완료"
            binding.admin1to1SelectOptionSelectedTv.text = "답변 완료"
            binding.admin1to1SelectOptionCl.visibility = View.VISIBLE
            binding.admin1to1DropdownCl.visibility = View.GONE

            selectedAnswer(page)
        }

        binding.admin1to1DropdownWaitingCl.setOnClickListener {
            binding.admin1to1DropdownSelectedTv.text = "대기 중"
            binding.admin1to1SelectOptionSelectedTv.text = "대기 중"
            binding.admin1to1SelectOptionCl.visibility = View.VISIBLE
            binding.admin1to1DropdownCl.visibility = View.GONE

            selectedWaiting(page)
        }

        binding.admin1to1DropdownTotalCl.setOnClickListener {
            binding.admin1to1DropdownSelectedTv.text = "전체"
            binding.admin1to1SelectOptionSelectedTv.text = "전체"
            binding.admin1to1SelectOptionCl.visibility = View.VISIBLE
            binding.admin1to1DropdownCl.visibility = View.GONE

            selectedAll(page)
        }
    }
    fun selectedAll(page: Int){

        CookieClient.service.getRootQNAAllList(accessToken,page).enqueue(object :
            Callback<RootQnAListResponse> {
            override fun onFailure(call: Call<RootQnAListResponse>, t: Throwable) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<RootQnAListResponse>,
                response: Response<RootQnAListResponse>
            ) {
                Log.d("retrofit_code", response.code().toString())
                if(response.body() != null) {
                    Log.d("retrofit_history", response.body().toString())
                    rootQNADatas = response.body()!!.result.rootQNAList
                    initRV()
                }
            }
        })
    }
    fun selectedAnswer(page: Int) {

        CookieClient.service.getRootQNAAnsweredList(accessToken, page).enqueue(object :
            Callback<RootQnAListResponse> {
            override fun onFailure(call: Call<RootQnAListResponse>, t: Throwable) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<RootQnAListResponse>,
                response: Response<RootQnAListResponse>
            ) {
                Log.d("retrofit_code", response.code().toString())
                if (response.body() != null) {
                    Log.d("retrofit_history", response.body().toString())
                    rootQNADatas = response.body()!!.result.rootQNAList
                    initRV()
                }
            }
        })
    }

    fun selectedWaiting(page: Int) {

        CookieClient.service.getRootQNAWaitingList(accessToken, page).enqueue(object :
            Callback<RootQnAListResponse> {
            override fun onFailure(call: Call<RootQnAListResponse>, t: Throwable) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<RootQnAListResponse>,
                response: Response<RootQnAListResponse>
            ) {
                Log.d("retrofit_code", response.code().toString())
                if (response.body() != null) {
                    Log.d("retrofit_history", response.body().toString())
                    rootQNADatas = response.body()!!.result.rootQNAList
                    initRV()
                }
            }
        })
    }

}