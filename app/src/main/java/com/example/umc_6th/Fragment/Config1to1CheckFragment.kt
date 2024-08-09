package com.example.umc_6th

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.Response.QNADetailResponse
import com.example.umc_6th.Retrofit.Response.QNAListResponse
import com.example.umc_6th.databinding.FragmentConfig1to1CheckBinding
import com.example.umc_6th.databinding.FragmentConfigPerinfoMajorBinding
import com.example.umc_6th.databinding.FragmentShopBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Config1to1CheckFragment : Fragment() {

    lateinit var binding : FragmentConfig1to1CheckBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConfig1to1CheckBinding.inflate(inflater,container,false)

        binding.inquire1to1CheckBackIv.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,Config1to1CheckListFragment()).commitAllowingStateLoss()
        }
        callGetQNADetailList()
        return binding.root
    }
    private fun callGetQNADetailList() {

        CookieClient.service.getQNADetailList(1).enqueue(object :
            Callback<QNADetailResponse> {
            override fun onFailure(call: Call<QNADetailResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<QNADetailResponse>?,
                response: Response<QNADetailResponse>?
            ) {
//                Log.d("retrofit", response.toString())
//                Log.d("retrofit", response?.code().toString())
//                Log.d("retrofit", response?.message().toString())
//                Log.d("retrofit", response?.body()?.code.toString())

                if (response != null ) {
                    binding.inquire1to1CheckMainTitleTv.text = response.body()?.result?.title
                    binding.inquire1to1CheckMainBodyTv.text = response.body()?.result?.content
                    binding.inquire1to1CheckMainCommentTv.text = response.body()?.result?.answer
                    val check = response.body()?.result!!

                    if (check.picList.size < 3) {
                        binding.inquire1to1CheckMainImg3Iv.visibility = View.GONE
                    }
                    if (check.picList.size < 2) {
                        binding.inquire1to1CheckMainImg2Iv.visibility = View.GONE
                    }
                    if (check.picList.size < 1) {
                        binding.inquire1to1CheckMainImg1Iv.visibility = View.GONE
                    }
                }
            }
        })
    }
}