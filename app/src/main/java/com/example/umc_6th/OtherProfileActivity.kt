package com.example.umc_6th

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_6th.databinding.ActivityOtherProfileBinding

class OtherProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtherProfileBinding
    private lateinit var postAdapter: OtherProfileRVAdapter
    private lateinit var commentAdapter: OtherProfileRVAdapter

    private val postList = ArrayList<OtherProfile>()
    private val commentList = ArrayList<OtherProfile>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtherProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initPostRecyclerView()
        initCommentRecyclerView()
        loadSampleData()
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

    private fun loadSampleData() {
        // 샘플 데이터 로드 (임시 데이터)
        postList.add(OtherProfile("안녕하세요 여러분", 3, 5))
        postList.add(OtherProfile("두 번째 글입니다.", 1, 2))
        postList.add(OtherProfile("세 번째 글입니다.", 0, 0))

        commentList.add(OtherProfile("첫 번째 댓글입니다.", 4, 7))
        commentList.add(OtherProfile("두 번째 댓글입니다.", 2, 1))
        commentList.add(OtherProfile("세 번째 댓글입니다.", 5, 3))

        postAdapter.notifyDataSetChanged()
        commentAdapter.notifyDataSetChanged()
    }
}