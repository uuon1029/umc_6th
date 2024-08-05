package com.example.umc_6th.Activity

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_6th.Adapter.AdminQuestRVAdapter
import com.example.umc_6th.Data.AdminQuest
import com.example.umc_6th.databinding.ActivityAdminQuestBinding

class AdminQuestActivity : AppCompatActivity(){
    lateinit var binding: ActivityAdminQuestBinding
    private lateinit var adminquestAdapter: AdminQuestRVAdapter

    private val adminquestList = ArrayList<AdminQuest>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminQuestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerlist()
        initRecyclerView()

        setSelectedTab(binding.adminQuestTabTotalTv)

        binding.adminQuestTabTotalTv.setOnClickListener {
            setSelectedTab(binding.adminQuestTabTotalTv)
        }

        binding.adminQuestTabSearchTv.setOnClickListener {
            setSelectedTab(binding.adminQuestTabSearchTv)
        }

        binding.adminQuestTabCommunityTv.setOnClickListener {
            setSelectedTab(binding.adminQuestTabCommunityTv)
        }

        binding.adminQuestTabMatterTv.setOnClickListener {
            setSelectedTab(binding.adminQuestTabMatterTv)
        }

        binding.adminQuestBackIv.setOnClickListener{
            finish()
        }
    }

    private fun setSelectedTab(selectedTextView: TextView) {
        clearTabSelections()
        selectedTextView.isSelected = true
    }
    private fun initRecyclerView() {
        adminquestAdapter = AdminQuestRVAdapter(adminquestList)
        binding.adminQuestBodyRv.adapter = adminquestAdapter
        binding.adminQuestBodyRv.layoutManager = LinearLayoutManager(this)
    }

    private fun clearTabSelections() {
        binding.adminQuestTabTotalTv.isSelected = false
        binding.adminQuestTabSearchTv.isSelected = false
        binding.adminQuestTabCommunityTv.isSelected = false
        binding.adminQuestTabMatterTv.isSelected = false
    }
    private fun initRecyclerlist(){
        adminquestList.add(AdminQuest("커뮤니티", "외부로 유출된 글이 있는데 어떻게 하나요?", "24.07.14"))
        adminquestList.add(AdminQuest("검색어", "외부로 유출된 글이 있는데 어떻게 하나요?", "24.07.14"))
        adminquestList.add(AdminQuest("문제", "외부로 유출된 글이 있는데 어떻게 하나요?", "24.07.14"))
        adminquestList.add(AdminQuest("검색어", "외부로 유출된 글이 있는데 어떻게 하나요?", "24.07.14"))
    }
}