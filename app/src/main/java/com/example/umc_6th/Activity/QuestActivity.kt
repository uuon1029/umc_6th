package com.example.umc_6th

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_6th.Retrofit.BoardMajorListResponse
import com.example.umc_6th.Retrofit.BoardViewResponse
import com.example.umc_6th.Retrofit.CookieClient
import com.example.umc_6th.Retrofit.DataClass.Pin
import com.example.umc_6th.Retrofit.RetrofitClient
import com.example.umc_6th.databinding.ActivityMainBinding
import com.example.umc_6th.databinding.ActivityQuestBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuestActivity : AppCompatActivity(), MainAnswerRVAdapter.OnItemClickListener {

    lateinit var binding : ActivityQuestBinding
    private lateinit var mainAnswerAdapter: MainAnswerRVAdapter
    private var MainAnswerList = ArrayList<Pin>()
    var like : Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var board_id : Int = 0

        if(intent.hasExtra("id")) {
            board_id = intent.getStringExtra("id")!!.toInt()
        }
        Log.d("retrofit_check_id", board_id.toString())

        callGetBoardView(board_id)

        initSetOnClickListener()

        mainAnswerAdapter = MainAnswerRVAdapter(this, this)
        mainAnswerAdapter.itemList = MainAnswerList

        binding.questBoardMainAnswerRv.apply {
            layoutManager = LinearLayoutManager(this@QuestActivity)
            adapter = mainAnswerAdapter
        }
//        MainAnswerList.add(MainAnswer(R.drawable.ic_circle_main_40,"Name1", 11, "댓글 요정입니다. 요정계에서 요정 한 명이 없어졌다고 난리가 났습니다. 잠시 인간계에 오는건 좋지만 얼른 집으로 돌아오세요"
//            ,arrayListOf(R.drawable.ic_circle_main_40),arrayListOf(SubAnswer(R.drawable.ic_circle_main_40, "SubBody1", 11,"dd"),SubAnswer(R.drawable.ic_circle_main_40, "SubBody1", 11,"dd"))))
//        MainAnswerList.add(MainAnswer(R.drawable.ic_circle_main_40,"Name2", 12, "Date2",arrayListOf(R.drawable.ic_circle_main_40),arrayListOf(SubAnswer(R.drawable.ic_circle_main_40, "SubBody2", 11,"dd"))))

        mainAnswerAdapter.notifyDataSetChanged()

        //좋아요
        when (like) {
            true -> {
                binding.questBoardUnlikeIv.visibility = View.GONE
                binding.questBoardLikeIv.visibility = View.VISIBLE
            }

            false -> {
                binding.questBoardUnlikeIv.visibility = View.VISIBLE
                binding.questBoardLikeIv.visibility = View.GONE
            }
        }
    }
    private fun callGetBoardView(board_id:Int) {

        CookieClient.service.getBoard(board_id,0).enqueue(object :
            Callback<BoardViewResponse> {
            override fun onFailure(call: Call<BoardViewResponse>?, t: Throwable?) {
                Log.e("retrofit", t.toString())
            }

            override fun onResponse(
                call: Call<BoardViewResponse>?,
                response: Response<BoardViewResponse>?
            ) {
                Log.d("retrofit", response.toString())
                Log.d("retrofit", response?.code().toString())
                Log.d("retrofit", response?.message().toString())
                Log.d("retrofit", response?.body()?.code.toString())

                if (response != null ) {
                    MainAnswerList = response.body()?.result?.pinList!!
                    val board = response.body()?.result!!
                    binding.questBoardNameTv.text = board.userNickname
                    binding.questBoardTimeTv.text = board.boardDate
                    binding.questBoardTitleTv.text = board.title
                    binding.questBoardBodyTv.text = board.content

                    if (board.pinList.size < 3) {
                        binding.questBoardImg3Iv.visibility = View.GONE
                    }
                    if (board.pinList.size < 2) {
                        binding.questBoardImg2Iv.visibility = View.GONE
                    }
                    if (board.pinList.size < 1) {
                        binding.questBoardImg1Iv.visibility = View.GONE
                    }

                    binding.questBoardChatnumTv.text = board.pinCount.toString()
                    binding.questBoardHeartnumTv.text = board.likeCount.toString()

                }
            }
        })
    }

    private fun initSetOnClickListener() {
        binding.questBoardProfileIv.setOnClickListener{
            startActivity(Intent(this, OtherProfileActivity::class.java))
        }

        binding.questBackIv.setOnClickListener {
            finish()
        }

        binding.questBoardUnlikeIv.setOnClickListener {
            binding.questBoardUnlikeIv.visibility = View.GONE
            binding.questBoardLikeIv.visibility = View.VISIBLE
            like = true

        }

        binding.questBoardLikeIv.setOnClickListener {
            binding.questBoardUnlikeIv.visibility = View.VISIBLE
            binding.questBoardLikeIv.visibility = View.GONE
            like = false

        }
    }

    override fun onProfileImageClick(position: Int) {
        val intent = Intent(this, OtherProfileActivity::class.java)
        startActivity(intent)
    }
    override fun onSubProfileImageClick(position: Int) {
        val intent = Intent(this, OtherProfileActivity::class.java)
        startActivity(intent)
    }
}