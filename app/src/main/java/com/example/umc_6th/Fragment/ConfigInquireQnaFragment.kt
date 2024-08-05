package com.example.umc_6th.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_6th.Adapter.AdminQuestRVAdapter
import com.example.umc_6th.Adapter.ConfigInquireQnaRVAdapter
import com.example.umc_6th.ConfigInquireFragment
import com.example.umc_6th.Data.AdminQuest
import com.example.umc_6th.MainActivity
import com.example.umc_6th.R
import com.example.umc_6th.databinding.FragmentConfigInquireQnaBinding

class ConfigInquireQnaFragment : Fragment() {
    private lateinit var binding: FragmentConfigInquireQnaBinding
    private lateinit var adminquestAdapter: ConfigInquireQnaRVAdapter

    private val adminquestList = ArrayList<AdminQuest>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConfigInquireQnaBinding.inflate(inflater, container, false)

        initRecyclerList()
        initRecyclerView()

        setSelectedTab(binding.inquireQnaTabTotalTv)

        binding.inquireQnaTabTotalTv.setOnClickListener {
            setSelectedTab(binding.inquireQnaTabTotalTv)
        }

        binding.inquireQnaTabSearchTv.setOnClickListener {
            setSelectedTab(binding.inquireQnaTabSearchTv)
        }

        binding.inquireQnaTabCommunityTv.setOnClickListener {
            setSelectedTab(binding.inquireQnaTabCommunityTv)
        }

        binding.inquireQnaTabMatterTv.setOnClickListener {
            setSelectedTab(binding.inquireQnaTabMatterTv)
        }

        binding.inquireQnaBackIv.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, ConfigInquireFragment()).commitAllowingStateLoss()
        }

        return binding.root
    }

    private fun setSelectedTab(selectedTextView: TextView) {
        clearTabSelections()
        selectedTextView.isSelected = true
    }

    private fun clearTabSelections() {
        binding.inquireQnaTabTotalTv.isSelected = false
        binding.inquireQnaTabSearchTv.isSelected = false
        binding.inquireQnaTabCommunityTv.isSelected = false
        binding.inquireQnaTabMatterTv.isSelected = false
    }

    private fun initRecyclerView() {
        adminquestAdapter = ConfigInquireQnaRVAdapter(adminquestList)
        binding.inquireQnaBodyRv.adapter = adminquestAdapter
        binding.inquireQnaBodyRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    private fun initRecyclerList() {
        adminquestList.add(AdminQuest("커뮤니티", "외부로 유출된 글이 있는데 어떻게 하나요?", "24.07.14"))
        adminquestList.add(AdminQuest("검색어", "외부로 유출된 글이 있는데 어떻게 하나요?", "24.07.14"))
        adminquestList.add(AdminQuest("문제", "외부로 유출된 글이 있는데 어떻게 하나요?", "24.07.14"))
        adminquestList.add(AdminQuest("검색어", "외부로 유출된 글이 있는데 어떻게 하나요?", "24.07.14"))
    }
}
