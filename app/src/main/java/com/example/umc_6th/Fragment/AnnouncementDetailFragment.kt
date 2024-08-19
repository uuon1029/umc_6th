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
    var notice_id = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAnnouncementDetailBinding.inflate(inflater,container,false)

        (activity as MainActivity).window.statusBarColor = (activity as MainActivity).getColor(R.color.white)


        binding.announcementDetailBackIv.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,AnnouncementFragment()).commitAllowingStateLoss()
        }
        callGetAnnouncement(notice_id)
        return binding.root
    }
    override fun onPause() {
        super.onPause()
        (activity as MainActivity).window.statusBarColor = (activity as MainActivity).getColor(R.color.main_color)

    }

    private fun callGetAnnouncement(notice_id: Int) {

        CookieClient.service.getAnnouncement(MainActivity.accessToken,notice_id).enqueue(object : Callback<NoticeDetailResponse> {
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
                if( response?.body() != null){
                    binding.announcementDetailMainTitleTv.text = response.body()?.result?.title
                    binding.announcementDetailMainBodyTv.text = response.body()?.result?.content
                    val date = response.body()?.result?.updatedAt.toString()
                    binding.announcementDetailMainDateTv.text = ( date.substring(2,4) + "."+ date.substring(5,7)
                            + "." + date.substring(8,10))
                }


            }
        })
    }
}