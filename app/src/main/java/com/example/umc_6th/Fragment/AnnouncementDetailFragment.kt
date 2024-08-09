package com.example.umc_6th

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.NoticeDetailResponse
import com.example.umc_6th.databinding.FragmentAnnouncementDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AnnouncementDetailFragment : Fragment() {

    lateinit var binding : FragmentAnnouncementDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAnnouncementDetailBinding.inflate(inflater,container,false)
        callGetAnnouncement()
        return binding.root
    }
    private fun callGetAnnouncement() {

        CookieClient.service.getAnnouncement(1).enqueue(object : Callback<NoticeDetailResponse> {
            override fun onFailure(call: Call<NoticeDetailResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<NoticeDetailResponse>?,
                response: Response<NoticeDetailResponse>?
            ) {
//                Log.d("retrofit", response.toString())
//                Log.d("retrofit", response?.code().toString())
//                Log.d("retrofit", response?.message().toString())
//                Log.d("retrofit", response?.body()?.result.toString())

                binding.announcementDetailMainTitleTv.text = response?.body()?.result?.title
                binding.announcementDetailMainBodyTv.text = response?.body()?.result?.content
                binding.announcementDetailMainDateTv.text = response?.body()?.result?.createdAt

            }
        })
    }
}