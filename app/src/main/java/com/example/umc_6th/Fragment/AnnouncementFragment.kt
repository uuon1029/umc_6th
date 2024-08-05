package com.example.umc_6th

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_6th.Retrofit.BoardMainResponse
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.DataClass.Announcement
import com.example.umc_6th.Retrofit.NoticeListResponse
import com.example.umc_6th.Retrofit.RetrofitClient
import com.example.umc_6th.databinding.FragmentAnnouncementBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AnnouncementFragment : Fragment() {

    lateinit var binding : FragmentAnnouncementBinding

    private lateinit var adapter : AnnouncementRVAdapter
    var announcementDatas = ArrayList<Announcement>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAnnouncementBinding.inflate(inflater,container,false)

        binding.announcementBackIv.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,ConfigFragment()).commitAllowingStateLoss()
        }
        //initializeannouncementlist()
        Log.d("Anno","test")
        callGetAnnouncement(0)

        return binding.root
    }
    private fun callGetAnnouncement(page: Int) {

        CookieClient.service.getAnnouncementsList(page).enqueue(object :
            Callback<NoticeListResponse> {
            override fun onFailure(call: Call<NoticeListResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<NoticeListResponse>?,
                response: Response<NoticeListResponse>?
            ) {
//                Log.d("retrofit_test", response?.code().toString())
//                Log.d("retrofit_test", response?.message().toString())
//                Log.d("retrofit_test", response?.body()?.result.toString())
//
//                Log.d("retrofit", response?.body()?.result?.boardMajorList.toString())
//                Log.d("retrofit", response?.body()?.result?.boardHotList.toString())
//                Log.d("retrofit", response?.body()?.result?.boardAllList.toString())

                if (response != null ) {
                    if(response.body()?.result != null) {
                        announcementDatas = response.body()!!.result
                    }
                    initannouncementRecyclerView()
                }

                Log.d("retrofit", announcementDatas.toString())

            }
        })
    }
    fun initannouncementRecyclerView(){
        adapter = AnnouncementRVAdapter()
        adapter.announcementlist = announcementDatas
        binding.announcementRv.adapter=adapter
        binding.announcementRv.layoutManager= LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

//    fun initializeannouncementlist(){
//        with(announcementDatas){
//            add(Announcement(6,"시스템 점검 및 업데이트 안내", "07.24"))
//            add(Announcement(5,"시스템 점검 및 업데에에이트 안내", "07.20"))
//            add(Announcement(4,"시스템 점검", "07.16"))
//            add(Announcement(3,"업데이트", "07.12"))
//            add(Announcement(2,"시스템 점검 및 업데이트 안내", "07.06"))
//            add(Announcement(1,"시스템 점검 및 업데이트 안내", "07.01"))
//        }
//    }
}