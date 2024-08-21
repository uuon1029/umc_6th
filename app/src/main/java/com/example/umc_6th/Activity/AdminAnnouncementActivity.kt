package com.example.umc_6th.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_6th.Adapter.AdminAnnouncementRVAdapter
import com.example.umc_6th.AnnouncementDetailFragment
import com.example.umc_6th.AnnouncementRVAdapter
import com.example.umc_6th.DataClass.DialogAdminAnnouncemenetDelete
import com.example.umc_6th.DialogQuestRemoveConfirm
import com.example.umc_6th.MainActivity
import com.example.umc_6th.OtherProfileActivity
import com.example.umc_6th.R
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.DataClass.Announcement
import com.example.umc_6th.Retrofit.NoticeListResponse
import com.example.umc_6th.Retrofit.Response.RootNoticeResponse
import com.example.umc_6th.databinding.ActivityAdmin1to1Binding
import com.example.umc_6th.databinding.ActivityAdminAnnouncementBinding
import com.example.umc_6th.databinding.DialogAdminAnnouncementDeleteBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AdminAnnouncementActivity : AppCompatActivity(){
    lateinit var binding: ActivityAdminAnnouncementBinding

    private lateinit var adapter : AdminAnnouncementRVAdapter
    var announcementDatas = ArrayList<Announcement>()
    private var page = 1
    private var notice_id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminAnnouncementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.adminAnnouncemnetBackIv.setOnClickListener {
            finish()
        }
        callGetAnnouncement(page)
        initSetOnClickListener()
    }

    private fun initSetOnClickListener() {
        binding.adminAnnouncementModifyIv.setOnClickListener {
            val intent = Intent(this, AdminAnnouncementWriteActivity::class.java)

            startActivity(intent)
        }
    }

    private fun callGetAnnouncement(page: Int) {

        CookieClient.service.getAnnouncementsList(MainActivity.accessToken, page).enqueue(object :
            Callback<NoticeListResponse> {
            override fun onFailure(call: Call<NoticeListResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<NoticeListResponse>?,
                response: Response<NoticeListResponse>?
            ) {
                if (response != null ) {
                    if(response.body()?.result != null) {
                        announcementDatas = response.body()!!.result
                        initannouncementRecyclerView()
                    }
                }
                Log.d("retrofit", announcementDatas.toString())

            }
        })
    }

    fun initannouncementRecyclerView(){
        adapter = AdminAnnouncementRVAdapter(announcementDatas)
        adapter.adminAnnouncementlist = announcementDatas
        adapter.setMyItemClickListener(object : AdminAnnouncementRVAdapter.MyItemClickListener {
            override fun onItemClick(announcement: Announcement) {
                // 아이템 클릭 시 AdminAnnouncementWriteActivity로 이동
                val intent = Intent(this@AdminAnnouncementActivity, AdminAnnouncementWriteActivity::class.java)
                intent.putExtra("announcement_id", announcement.id) // 필요한 데이터 전달
                startActivity(intent)
            }

            override fun onDeleteClick(position: Int) {
                // 삭제 클릭 처리
            }
        })


        binding.adminAnnouncemnetRv.adapter=adapter
        binding.adminAnnouncemnetRv.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }
}