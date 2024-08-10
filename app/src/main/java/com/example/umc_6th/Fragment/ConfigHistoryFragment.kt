package com.example.umc_6th

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_6th.Activity.HistorySearchActivity
import com.example.umc_6th.Adapter.ConfigHistoryRVAdapter
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.DataClass.History
import com.example.umc_6th.Retrofit.HistoryResponse
import com.example.umc_6th.Retrofit.Response.AgreementChangeResponse
import com.example.umc_6th.Retrofit.RetrofitClient
import com.example.umc_6th.databinding.DialogOpenBinding
import com.example.umc_6th.databinding.FragmentConfigHistoryBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConfigHistoryFragment : Fragment() {

    lateinit var binding: FragmentConfigHistoryBinding
    private var isOpened : Boolean = false
    private var  configDatas = arrayListOf<History>()
    private var page: Int = 1
    val accessToken = MainActivity.accessToken
    companion object {
        //private val tagList : List<String> = listOf("전체","내가 쓴 글","댓글단 글","좋아요")
        var key_word : String = ""
        var tag_id : Int = 0
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConfigHistoryBinding.inflate(inflater,container,false)

        selectedAll(page)

        Log.d("retrofit_history", configDatas.toString())
        initStatus()
        initClickListener()
        setupDropdown()

        return binding.root
    }

    private fun initStatus() {
        CookieClient.service.patchHistoryOpen(MainActivity.accessToken).enqueue(object :
            Callback<AgreementChangeResponse> {
            override fun onFailure(call: Call<AgreementChangeResponse>, t: Throwable) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<AgreementChangeResponse>,
                response: Response<AgreementChangeResponse>
            ) {
                CookieClient.service.patchHistoryOpen(MainActivity.accessToken).enqueue(object :
                    Callback<AgreementChangeResponse> {
                    override fun onFailure(call: Call<AgreementChangeResponse>, t: Throwable) {
                        Log.e("retrofit", t.toString())
                    }

                    override fun onResponse(
                        call: Call<AgreementChangeResponse>,
                        response: Response<AgreementChangeResponse>
                    ) {
                        if(response.body() != null) {
                            isOpened = when(response.body()!!.result.agreement) {
                                "AGREE" -> true
                                else -> false
                            }
                            Log.d("retrofit_open", response.body()!!.result.agreement)
                            setOpenSwitch()
                        }
                    }
                })
            }
        })

//        selectedAll()
    }

    private fun initClickListener() {
        binding.configHistoryPreviousBtnIv.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,ConfigFragment()).commitAllowingStateLoss()
        }

        binding.configHistorySearchIv.setOnClickListener {
            val i = Intent(activity, HistorySearchActivity::class.java)
            startActivity(i)
        }

        binding.configHistoryOpenBtnIv.setOnClickListener {
//            callDialog()
            changeStatus()
        }
    }

    private fun initRV() {
        val configHistoryRVAdapter = ConfigHistoryRVAdapter(configDatas)
        binding.configHistoryHistoryRv.adapter = configHistoryRVAdapter
        binding.configHistoryHistoryRv.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL,false)
    }

    private fun setOpenSwitch() {
        if(isOpened) {
            binding.configHistorySwitchImgIv.setImageResource(R.drawable.ic_switch_open)
            binding.configHistorySwitchTv.text = "공개"
        } else {
            binding.configHistorySwitchImgIv.setImageResource(R.drawable.ic_switch_close)
            binding.configHistorySwitchTv.text = "비공개"
        }
    }

    private fun setupDropdown() {
        binding.configHistorySelectOptionCl.setOnClickListener {
            binding.configHistorySelectOptionCl.visibility = View.GONE
            binding.configHistoryDropdownCl.visibility = View.VISIBLE
        }
        binding.configHistorySelectedCl.setOnClickListener {
            binding.configHistorySelectOptionCl.visibility = View.VISIBLE
            binding.configHistoryDropdownCl.visibility = View.GONE
        }

        binding.configHistoryDropdownWrittingCl.setOnClickListener {
            binding.configHistoryDropdownSelectedTv.text = "내가 쓴 글"
            binding.configHistorySelectOptionSelectedTv.text = "내가 쓴 글"
            binding.configHistorySelectOptionCl.visibility = View.VISIBLE
            binding.configHistoryDropdownCl.visibility = View.GONE
            
            selectedBoard(page)
        }

        binding.configHistoryDropdownCommentCl.setOnClickListener {
            binding.configHistoryDropdownSelectedTv.text = "댓글 단 글"
            binding.configHistorySelectOptionSelectedTv.text = "댓글 단 글"
            binding.configHistorySelectOptionCl.visibility = View.VISIBLE
            binding.configHistoryDropdownCl.visibility = View.GONE
            
            selectedComment(page)
        }

        binding.configHistoryDropdownBookmarkCl.setOnClickListener {
            binding.configHistoryDropdownSelectedTv.text = "좋아요"
            binding.configHistorySelectOptionSelectedTv.text = "좋아요"
            binding.configHistorySelectOptionCl.visibility = View.VISIBLE
            binding.configHistoryDropdownCl.visibility = View.GONE
            
            selectedLike(page)
        }

        binding.configHistoryDropdownAllCl.setOnClickListener {
            binding.configHistoryDropdownSelectedTv.text = "전체"
            binding.configHistorySelectOptionSelectedTv.text = "전체"
            binding.configHistorySelectOptionCl.visibility = View.VISIBLE
            binding.configHistoryDropdownCl.visibility = View.GONE
            
            selectedAll(page)
        }
    }



    fun changeStatus() {
        CookieClient.service.patchHistoryOpen(MainActivity.accessToken).enqueue(object :
            Callback<AgreementChangeResponse> {
            override fun onFailure(call: Call<AgreementChangeResponse>, t: Throwable) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<AgreementChangeResponse>,
                response: Response<AgreementChangeResponse>
            ) {
                if(response.body() != null) {
                    isOpened = when(response.body()!!.result.agreement) {
                        "AGREE" -> true
                        else -> false
                    }
                    Log.d("retrofit_open", response.body()!!.result.agreement)
                    setOpenSwitch()
                }
            }
        })
    }

    private fun callDialog() {
        val dialog = DialogOpen(activity as MainActivity)
        dialog.show()
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
                    configDatas = response.body()!!.result.content
                    initRV()
                }
            }
        })

        
    }

    fun searchBoard(page: Int, key_word: String){
        

        RetrofitClient.service.getMyBoardsSearch(accessToken, page, key_word).enqueue(object :
            retrofit2.Callback<HistoryResponse> {
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
            retrofit2.Callback<HistoryResponse> {
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
            retrofit2.Callback<HistoryResponse> {
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

    fun selectedAll(page: Int){
        
        CookieClient.service.getHistory(accessToken,page).enqueue(object :
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

    fun selectedBoard(page: Int){
        
        CookieClient.service.getMyBoards(accessToken,page).enqueue(object :
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

    fun selectedComment(page: Int){
        
        CookieClient.service.getMyComments(accessToken,page).enqueue(object :
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

    fun selectedLike(page: Int){
        
        CookieClient.service.getMyLikes(accessToken,page).enqueue(object :
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
