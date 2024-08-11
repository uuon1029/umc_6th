package com.example.umc_6th.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_6th.Activity.HistorySearchActivity
import com.example.umc_6th.Adapter.ConfigHistoryRVAdapter
import com.example.umc_6th.ConfigFragment
import com.example.umc_6th.ConfigHistoryFragment
import com.example.umc_6th.MainActivity
import com.example.umc_6th.R
import com.example.umc_6th.Retrofit.DataClass.History
import com.example.umc_6th.Retrofit.HistoryResponse
import com.example.umc_6th.Retrofit.RetrofitClient
import com.example.umc_6th.databinding.FragmentConfigHistorySearchResultBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConfigHistorySearchResultFragment : Fragment() {
    lateinit var binding: FragmentConfigHistorySearchResultBinding
    private var  configDatas = arrayListOf<History>()
    private val accessToken = MainActivity.accessToken
    private var page = 1

    var key_word = ConfigHistoryFragment.key_word
    var search_tag = ConfigHistoryFragment.search_tag
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConfigHistorySearchResultBinding.inflate(inflater,container,false)

        binding.historySearchResultPreviousBtnIv.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, ConfigHistoryFragment()).commitAllowingStateLoss()
        }

        binding.historySearchResultBarTv.setOnClickListener {
            val i = Intent(activity, HistorySearchActivity::class.java)
            startActivity(i)
        }

        initFragment()

        Log.d("History_keyWord", key_word)
        Log.d("History_tag", search_tag)


        ConfigHistoryFragment.key_word = ""
        ConfigHistoryFragment.search_tag = "전체"

        return binding.root
    }


    private fun initFragment(){
        binding.historySearchResultBarTv.text = key_word
        when (search_tag) {
            "내가 쓴 글" -> {
                searchBoard(page,key_word)
            }
            "댓글단 글" -> {
                searchComment(page,key_word)
            }
            "좋아요" -> {
                searchLike(page,key_word)
            }
            "전체" -> {
                searchAll(page,key_word)
            }
        }
    }

    private fun initRV() {
        val configHistoryRVAdapter = ConfigHistoryRVAdapter(configDatas)
        binding.historySearchResultRv.adapter = configHistoryRVAdapter
        binding.historySearchResultRv.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL,false)
    }


    fun searchAll(page: Int, key_word: String){
        RetrofitClient.service.getHistorySearch(accessToken, page, key_word).enqueue(object :
            Callback<HistoryResponse> {
            override fun onFailure(call: Call<HistoryResponse>, t: Throwable) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<HistoryResponse>,
                response: Response<HistoryResponse>
            ) {
                Log.d("retrofit_code", response.code().toString())
                if(response.body() != null) {
                    Log.d("retrofit_history", response.body().toString())
                    Log.d("retrofit_history", key_word)
                    configDatas = response.body()!!.result.content
                    initRV()
                }
            }
        })


    }

    fun searchBoard(page: Int, key_word: String){
        RetrofitClient.service.getMyBoardsSearch(accessToken, page, key_word).enqueue(object :
            Callback<HistoryResponse> {
            override fun onFailure(call: Call<HistoryResponse>, t: Throwable) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<HistoryResponse>,
                response: Response<HistoryResponse>
            ) {
                Log.d("retrofit_code", response.code().toString())
                if(response.body() != null) {
                    Log.d("retrofit_history", response.body().toString())
                    configDatas = response.body()!!.result.content
                    initRV()
                }
            }
        })


    }

    fun searchComment(page: Int, key_word: String){
        RetrofitClient.service.getMyCommentsSearch(accessToken,page,key_word).enqueue(object :
            Callback<HistoryResponse> {
            override fun onFailure(call: Call<HistoryResponse>, t: Throwable) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<HistoryResponse>,
                response: Response<HistoryResponse>
            ) {
                Log.d("retrofit_code", response.code().toString())
                if(response.body() != null) {
                    Log.d("retrofit_history", response.body().toString())
                    configDatas = response.body()!!.result.content
                    initRV()
                }
            }
        })


    }

    fun searchLike(page: Int, key_word: String){
        RetrofitClient.service.getMyLikesSeach(accessToken,page,key_word).enqueue(object :
            Callback<HistoryResponse> {
            override fun onFailure(call: Call<HistoryResponse>, t: Throwable) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<HistoryResponse>,
                response: Response<HistoryResponse>
            ) {
                Log.d("retrofit_code", response.code().toString())
                if(response.body() != null) {
                    Log.d("retrofit_history", response.body().toString())
                    configDatas = response.body()!!.result.content
                    initRV()
                }
            }
        })
    }
}