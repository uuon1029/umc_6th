package com.example.umc_6th

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_6th.Adapter.ConfigNoticeRVAdapter
import com.example.umc_6th.databinding.FragmentConfigNoticeBinding

class ConfigNoticeFragment : Fragment() {
    lateinit var binding: FragmentConfigNoticeBinding
    private var  noticeDatas = ArrayList<Config>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConfigNoticeBinding.inflate(inflater,container,false)

        binding.configNoticePreviousBtnIv.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,ConfigFragment()).commitAllowingStateLoss()
        }

        val configNoticeRVAdapter = ConfigNoticeRVAdapter(noticeDatas)
        binding.configNoticeNoticeRv.adapter = configNoticeRVAdapter
        binding.configNoticeNoticeRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)

        configNoticeRVAdapter.setNoticeClickListener(object : ConfigNoticeRVAdapter.NoticeSetOnClickeListener{
            override fun itemClick(boardId: Int) {
                val i = Intent(activity, QuestActivity::class.java)
                startActivity(i)
            }
        })

        setupDropdown()

        return binding.root
    }

    private fun setupDropdown() {
        binding.configNoticeSelectOptionCl.setOnClickListener {
            binding.configNoticeSelectOptionCl.visibility = View.GONE
            binding.configNoticeDropdownCl.visibility = View.VISIBLE
        }
        binding.configNoticeSelectedCl.setOnClickListener {
            binding.configNoticeSelectOptionCl.visibility = View.VISIBLE
            binding.configNoticeDropdownCl.visibility = View.GONE
        }

        binding.configNoticeDropdownWrittingCl.setOnClickListener {
            binding.configNoticeDropdownSelectedTv.text = "내가 쓴 글"
            binding.configNoticeSelectOptionSelectedTv.text = "내가 쓴 글"
            binding.configNoticeSelectOptionCl.visibility = View.VISIBLE
            binding.configNoticeDropdownCl.visibility = View.GONE
        }

        binding.configNoticeDropdownCommentCl.setOnClickListener {
            binding.configNoticeDropdownSelectedTv.text = "댓글 단 글"
            binding.configNoticeSelectOptionSelectedTv.text = "댓글 단 글"
            binding.configNoticeSelectOptionCl.visibility = View.VISIBLE
            binding.configNoticeDropdownCl.visibility = View.GONE
        }

        binding.configNoticeDropdownLikeCl.setOnClickListener {
            binding.configNoticeDropdownSelectedTv.text = "즐겨찾기"
            binding.configNoticeSelectOptionSelectedTv.text = "즐겨찾기"
            binding.configNoticeSelectOptionCl.visibility = View.VISIBLE
            binding.configNoticeDropdownCl.visibility = View.GONE
        }

        binding.configNoticeDropdownAllCl.setOnClickListener {
            binding.configNoticeDropdownSelectedTv.text = "전체"
            binding.configNoticeSelectOptionSelectedTv.text = "전체"
            binding.configNoticeSelectOptionCl.visibility = View.VISIBLE
            binding.configNoticeDropdownCl.visibility = View.GONE
        }
    }
}