package com.example.umc_6th

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.DataClass.ProfileBoard
import com.example.umc_6th.Retrofit.FindProfileResponse
import com.example.umc_6th.databinding.ActivityOtherProfileBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OtherProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtherProfileBinding
    private lateinit var postAdapter: OtherProfileRVAdapter
    private lateinit var commentAdapter: OtherProfileRVAdapter

    private var postList = ArrayList<ProfileBoard>()
    private var commentList = ArrayList<ProfileBoard>()

    companion object {
        var profile: Int? = null
        var otherUser_id = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtherProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        window.statusBarColor = ContextCompat.getColor(this,R.color.main_color)

        otherUser_id = intent.getIntExtra("id",0)

        initUser(otherUser_id)

        binding.otehrProfileBg1Iv.setOnClickListener{
            profile = 1
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        }

        binding.otehrProfileBg2Iv.setOnClickListener{
            profile = 2
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        }

        binding.otherProfileBackIv.setOnClickListener{
            finish()
        }
    }

    private fun initUser(userId: Int) {
        if(userId!=0) {
            CookieClient.service.getUserProfile(userId).enqueue(object : Callback<FindProfileResponse>{
                override fun onResponse(
                    call: Call<FindProfileResponse>,
                    response: Response<FindProfileResponse>
                ) {
                    if(response.body()?.result!=null){
                        val result = response.body()!!.result
                        Glide.with(this@OtherProfileActivity).load(result.pic)
                            .into(binding.otherProfileImg)

                        binding.otherProfileNameTv.text = result.nickName
                        binding.otherProfileIntroTv.text = result.description

                        if(result.board!=null&&result.pinBoard!=null){
                            binding.otherProfileBg1Rv.visibility = View.VISIBLE
                            binding.otherProfileBg2Rv.visibility = View.VISIBLE
                            binding.otehrProfileBg1Iv.visibility = View.VISIBLE
                            binding.otherProfileBg2Rv.visibility = View.VISIBLE
                            binding.otherProfileBg1TitleTv.visibility = View.VISIBLE
                            binding.otherProfileBg2TitleTv.visibility = View.VISIBLE

                            binding.closedProfileBgIv.visibility = View.GONE
                            binding.closedProfileIv.visibility = View.GONE
                            
                            postList = result.board
                            commentList = result.pinBoard

                            initPostRecyclerView()
                            initCommentRecyclerView()
                        }else {
                            binding.otherProfileBg1Rv.visibility = View.GONE
                            binding.otherProfileBg2Rv.visibility = View.GONE
                            binding.otehrProfileBg1Iv.visibility = View.GONE
                            binding.otherProfileBg2Rv.visibility = View.GONE
                            binding.otherProfileBg1TitleTv.visibility = View.GONE
                            binding.otherProfileBg2TitleTv.visibility = View.GONE
                            
                            binding.closedProfileBgIv.visibility = View.VISIBLE
                            binding.closedProfileIv.visibility = View.VISIBLE
                        }
                    }
                }

                override fun onFailure(call: Call<FindProfileResponse>, t: Throwable) {
                }
            })
        }
    }


    private fun initPostRecyclerView() {
        postAdapter = OtherProfileRVAdapter(postList)
        binding.otherProfileBg1Rv.adapter = postAdapter
        binding.otherProfileBg1Rv.layoutManager = LinearLayoutManager(this)
    }

    private fun initCommentRecyclerView() {
        commentAdapter = OtherProfileRVAdapter(commentList)
        binding.otherProfileBg2Rv.adapter = commentAdapter
        binding.otherProfileBg2Rv.layoutManager = LinearLayoutManager(this)
    }
}