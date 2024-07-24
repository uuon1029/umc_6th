package com.example.umc_6th

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_6th.databinding.ActivityMainBinding
import com.example.umc_6th.databinding.ActivityQuestBinding

class QuestActivity : AppCompatActivity() {

    lateinit var binding : ActivityQuestBinding
    private lateinit var mainAnswerAdapter: MainAnswerRVAdapter
    private val MainAnswerList = ArrayList<MainAnswer>()
    var like : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSetOnClickListener()

        mainAnswerAdapter = MainAnswerRVAdapter(this)
        mainAnswerAdapter.itemList = MainAnswerList

        binding.questBoardMainAnswerRv.apply {
            layoutManager = LinearLayoutManager(this@QuestActivity)
            adapter = mainAnswerAdapter
        }
        MainAnswerList.add(MainAnswer(R.drawable.ic_circle_main_40,"Name1", 11, "댓글 요정입니다. 요정계에서 요정 한 명이 없어졌다고 난리가 났습니다. 잠시 인간계에 오는건 좋지만 얼른 집으로 돌아오세요"
            ,arrayListOf(R.drawable.ic_circle_main_40),arrayListOf(SubAnswer(R.drawable.ic_circle_main_40, "SubBody1", 11,"dd"),SubAnswer(R.drawable.ic_circle_main_40, "SubBody1", 11,"dd"))))
        MainAnswerList.add(MainAnswer(R.drawable.ic_circle_main_40,"Name2", 12, "Date2",arrayListOf(R.drawable.ic_circle_main_40),arrayListOf(SubAnswer(R.drawable.ic_circle_main_40, "SubBody2", 11,"dd"))))


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
}