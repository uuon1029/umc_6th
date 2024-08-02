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

        setSelectedTab(binding.adminQuestTabTotalIb, binding.adminQuestTabTextTotalTv)

        binding.adminQuestTabTotalIb.setOnClickListener {
            setSelectedTab(binding.adminQuestTabTotalIb, binding.adminQuestTabTextTotalTv)
        }

        binding.adminQuestTabSearchIb.setOnClickListener {
            setSelectedTab(binding.adminQuestTabSearchIb, binding.adminQuestTabTextSearchTv)
        }

        binding.adminQuestTabCommunityIb.setOnClickListener {
            setSelectedTab(binding.adminQuestTabCommunityIb, binding.adminQuestTabTextCommunityTv)
        }

        binding.adminQuestTabMatterIb.setOnClickListener {
            setSelectedTab(binding.adminQuestTabMatterIb, binding.adminQuestTabTextMatterTv)
        }


        binding.adminQuestBackIv.setOnClickListener{
            finish()
        }
    }

    private fun setSelectedTab(selectedButton: ImageButton, selectedText: TextView){
        adminQuestTabSelections()

        selectedButton.isSelected = true
        selectedText.isSelected = true
    }
    private fun initRecyclerView() {
        adminquestAdapter = AdminQuestRVAdapter(adminquestList)
        binding.adminQuestBodyRv.adapter = adminquestAdapter
        binding.adminQuestBodyRv.layoutManager = LinearLayoutManager(this)
    }

    private fun adminQuestTabSelections(){
        binding.adminQuestTabTotalIb.isSelected = false
        binding.adminQuestTabTextTotalTv.isSelected =false
        binding.adminQuestTabSearchIb.isSelected = false
        binding.adminQuestTabTextSearchTv.isSelected = false
        binding.adminQuestTabCommunityIb.isSelected = false
        binding.adminQuestTabTextCommunityTv.isSelected = false
        binding.adminQuestTabMatterIb.isSelected = false
        binding.adminQuestTabTextMatterTv.isSelected = false
    }

    private fun initRecyclerlist(){
        adminquestList.add(AdminQuest("커뮤니티", "외부로 유출된 글이 있는데 어떻게 하나요?", "24.07.14"))
        adminquestList.add(AdminQuest("검색어", "외부로 유출된 글이 있는데 어떻게 하나요?", "24.07.14"))
        adminquestList.add(AdminQuest("문제", "외부로 유출된 글이 있는데 어떻게 하나요?", "24.07.14"))
        adminquestList.add(AdminQuest("검색어", "외부로 유출된 글이 있는데 어떻게 하나요?", "24.07.14"))
    }
}