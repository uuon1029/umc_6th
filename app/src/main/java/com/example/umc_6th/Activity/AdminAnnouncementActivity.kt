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

        binding.adminAnnouncementRemoveIv.setOnClickListener {
            val deleteDialog = DialogAdminAnnouncemenetDelete(this)
            CookieClient.service.getAnnouncementsList(page).enqueue((object : Callback<NoticeListResponse> {
                override fun onFailure(call: Call<NoticeListResponse>, t: Throwable) {
                    Log.e("retrofit", t.toString())
                }

                override fun onResponse(
                    call: Call<NoticeListResponse>,
                    response: Response<NoticeListResponse>
                ) {
                    Log.d("retrofit_response", response.toString())
                    deleteDialog.setDialogClickListener(object : DialogAdminAnnouncemenetDelete.myDialogDoneClickListener{
                        override fun done() {
                            CallDeleteRootNotice(notice_id)
                            finish()
                        }
                    })
                    deleteDialog.show()
                }

            }))
        }
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

    private fun CallDeleteRootNotice(notice_id : Int){
        CookieClient.service.deleteRootNotice(MainActivity.accessToken, notice_id).enqueue(object : Callback<RootNoticeResponse> {
            override fun onFailure(call: Call<RootNoticeResponse>, t: Throwable) {
                Log.e("AnnouncementDelete", t.toString())
            }

            override fun onResponse(
                call: Call<RootNoticeResponse>,
                response: Response<RootNoticeResponse>
            ) {
                Log.d("AnnouncementDelete",response.toString())
                if (response.isSuccessful) {
                    if (response.body()?.isSuccess == true) {
                        Log.d("AnnouncementDelete", "Board deleted successfully")
                    } else {
                        Log.e("AnnouncementDelete", "Failed to delete board: ${response.body()?.message}")
                    }
                } else {
                    Log.e("AnnouncementDelete", "Response error: ${response.errorBody()?.string()}")
                }
            }
        })
    }
    fun initannouncementRecyclerView(){
        adapter = AdminAnnouncementRVAdapter(announcementDatas)
        adapter.adminAnnouncementlist = announcementDatas
        binding.adminAnnouncemnetRv.adapter=adapter
        binding.adminAnnouncemnetRv.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }
}